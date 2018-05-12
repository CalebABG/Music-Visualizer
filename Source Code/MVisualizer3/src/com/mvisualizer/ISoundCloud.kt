package com.mvisualizer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mvisualizer.Json2JavaClasses.Playlist
import com.mvisualizer.Json2JavaClasses.Track
import com.mvisualizer.MSong.FType
import com.soundcloud.api.ApiWrapper
import com.soundcloud.api.CloudAPI
import com.soundcloud.api.Http
import com.soundcloud.api.Request
import org.apache.http.HttpResponse
import org.apache.http.HttpStatus
import org.json.JSONObject
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


/*fun main(args: Array<String>) {
    val sc = ISoundCloud("h4xVW8Xx30tXHqgTtfUxiXFk2XpTWI8I", "8tzbr1Q0fFPOre68l9NhAwAXHqTrJO1M")

    val id = sc.getSongs("https://soundcloud.com/crimekitchen/sets/dec3mber-incandescent-ep-crime-kitchen")
//    val id = sc.getSongs("https://soundcloud.com/revokesounds/blue-skies-revoke-remix")
    println("$id ${id.second!!.size}")
}*/

open class ISoundCloud constructor(_client_id: String, _client_secret: String) {
    companion object {
        private lateinit var cid: String
        private lateinit var apiw: ApiWrapper
        private lateinit var gson: Gson

        fun getFullStreamUrl(url: String): String {
            val streamUrl = "$url/?consumer_key=$cid".replace("https://", "http://")
            val data: CompletableFuture<HttpResponse?> = CompletableFuture.supplyAsync { apiw.get(Request.to(streamUrl)) }
            val resp = data.get(15, TimeUnit.SECONDS)
            return resp!!.getFirstHeader("location").value.replace("https://", "http://")
        }

        fun getErrorCodeString(errorCode: Int): String {
            return when (errorCode) {
                HttpStatus.SC_FORBIDDEN -> "This Sound is Not Streamable :(, Try Another"
                HttpStatus.SC_NOT_FOUND -> "The Sound Requested Was Not Found, Check the URL or Try Another"
                HttpStatus.SC_NOT_ACCEPTABLE -> "This Sound is Not Accessible, Please Try Another"
                HttpStatus.SC_UNPROCESSABLE_ENTITY -> "The Request Could Not Be Processed, Please Check the Format"

            /*HttpStatus.class doesn't have a constant for this*/
                429 -> "Too Many Requests, The Limit for Streaming (24 hours) is 15K Songs"

                HttpStatus.SC_INTERNAL_SERVER_ERROR -> "Something went wrong on SoundCloud's Part, Try Again Later"
                HttpStatus.SC_SERVICE_UNAVAILABLE -> "SoundCloud's Servers are Too Busy to Handle the Request, Please Try Again Later"
                HttpStatus.SC_GATEWAY_TIMEOUT -> "Gateway Timeout, The Request Took Too Long, Try Again Later"
                else -> "Http Status Error: $errorCode"
            }
        }

        //reference from SoundCloud API
        private fun replaceJsonBlank(string: String): String {
            val pattern = Pattern.compile(":(?: +)?\"\"")
            val matcher = pattern.matcher(string)
            return matcher.replaceAll(":null")
        }
    }

    init {
        ISoundCloud.cid = _client_id
        ISoundCloud.apiw = ApiWrapper(_client_id, _client_secret, null, null)
        ISoundCloud.gson = GsonBuilder().setPrettyPrinting().create()
    }

    fun getSongs(url: String): Pair<Int, ArrayList<MSong>?> {
        val trackList: ArrayList<MSong> = ArrayList()
        var mstatusCode = -1

        try {
            var httpResponse = apiw.get(Request.to("https://api.soundcloud.com/resolve.json?url=$url&client_id=$cid"))
            mstatusCode = httpResponse.statusLine.statusCode

            //first check is to see if the resolver was able to find whatever was provided
            if (httpResponse.statusLine.statusCode == 302) {
                val oUrl = httpResponse.getFirstHeader("location").value

                httpResponse = apiw.get(Request.to(oUrl))
                mstatusCode = httpResponse.statusLine.statusCode

                //second response is to check if the link is OK
                if (httpResponse.statusLine.statusCode == HttpStatus.SC_OK){
                    val jsonString = Http.formatJSON(Http.getString(httpResponse)).trim()
                    val jsonObject = JSONObject(jsonString)
                    val type = jsonObject.getString("kind")

                    if (type == "track") {
                        val jtrack: Track = gson.fromJson(replaceJsonBlank(jsonString), Track::class.java)

                        if (jtrack.streamable && !jtrack.stream_url.isNullOrEmpty()) {
                            trackList.add(MSong(FType.SCTrack, jtrack.stream_url, jtrack.title))
                        }
                    }
                    else if (type == "playlist") {
                        val jplaylist: Playlist = gson.fromJson(replaceJsonBlank(jsonString), Playlist::class.java)

                        for (track: Track in jplaylist.tracks) {
                            if (track.streamable && !track.stream_url.isNullOrEmpty()){
                                trackList.add(MSong(FType.SCTrack, track.stream_url, track.title))
                            }
                        }
                    }
                }
            }
        }
        catch (e: CloudAPI.ApiResponseException){ mstatusCode = e.statusCode; println(e.localizedMessage); EException.append(e) }
        catch (e: Exception){ println(e.localizedMessage); EException.append(e)}

        return Pair(mstatusCode, if (trackList.isEmpty()) null else trackList)
    }
}