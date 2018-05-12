package com.mvisualizer

import controlP5.ControlP5
import ddf.minim.Minim
import ddf.minim.analysis.FFT
import org.apache.commons.io.FilenameUtils
import processing.core.PApplet
import java.awt.FileDialog
import java.awt.Frame
import java.awt.event.KeyEvent
import java.lang.Math.signum
import javax.swing.JOptionPane
import javax.swing.UIManager
import java.awt.EventQueue
import kotlin.collections.ArrayList
import java.io.File
import com.mvisualizer.MSong.FType


class Visualizer : PApplet() {

    override fun setup() {
        surface.setTitle("MVisualizer :D")
        surface.setResizable(true)
        surface.setFrameRate(85f)
        surface.setIcon(loadImage(iconFile))

        strokeCap(SQUARE)
        noiseDetail(1, 0.95f)

        cp5 = ControlP5(this)
        Controls.setupControls()
    }

    override fun settings() {
        minim = Minim(this)

        if (MV_DEBUG)
            minim.debugOn()

        Controls.visualizerRef = this

        size(900, 840, JAVA2D)
    }

    override fun stop() {
        try {
            player.close()
            minim.stop()
            super.stop()
        }
        catch (e: Exception) {
            println(e.message)
            exit()
        }
        finally {
            exit()
        }
    }

    override fun draw() {
        try {
            if (initialSongLoaded)
                mainLoop()
            else
                welcome()
        }
        catch (e: Exception){EException.set(e)}
    }

    val numBars = 10
    var t = 0f
    private fun welcome() {
        val amplitude = height / 12
        val offset = height / 10

        background(0)
        stroke(255)

        textSize(26f)
        textAlign(CENTER)
        fill(255)
        text("MVisualizer Wave, Listen On!", width/2f, height-50f)

        for (i in 1 until numBars) {
            val x = i * (width / numBars)
            val y1 = height / 2 - (offset + amplitude * sin(t + i))
            val y2 = height / 2 + (offset + amplitude * cos(t + i))

            strokeWeight(9f)
            line(x.toFloat(), y1, x.toFloat(), y2)
        }

        t += 0.04f
    }
    private fun mainLoop() {
        /**
         * when these two values both equal false, then the song has finished
         * if the player was paused then player.isPlaying = false and isPaused = true
         * if the user un-pauses the song then player.isPlaying = true and isPaused = false
         * so if the song isn't paused and the position of the player's audio buffer hits its limit,
         * player.isPlaying will be set to false and then both values will be false
         */
        val songFinished: Boolean = player.isPlaying == isPaused

        if (!MusicList.visListModel.isEmpty && songFinished) {
            val nextSong: MSong = MusicList.visListModel.poll()
            loadSong(nextSong)
        }
        else if (MusicList.visListModel.isEmpty && songFinished && playerNotClosed){
            player.close()
            playerNotClosed = false
            println("Closed Player: ${!playerNotClosed}")
        }

        fft.forward(player.mix)

        background(0)
        if (drawBackground) {
            stroke(255)
            showBackGround(backgroundStep)
        }

        showSongTime()
        smoothPulse()
        visualize()
    }

    private fun visualize() {
        cAmplitude = lerp(cAmplitude, smoothing * fft.calcAvg(10f, 120f), .1f)
        ellipseR = constrain(cAmplitude * bassKick, 0.1f, circleRadius * 65.0f)
        maxDrawHeight = (height / 2) * 0.25f
        spectrumScaleFactor = maxVal - minVal + 0.00001f

        when (visualMode) {
            0 -> circularPulse()
            1 -> signal()
            2 -> inverse()
            3 -> functionMusic()
            4 -> dotWave()
            5 -> bassNationPulse()
            else -> {}
        }
    }

    private fun smoothPulse() {
//        for (i in 0 until fft.specSize()) {
        for (i in 0 until avgSize) {
            // Smooth using exponential moving average
            fftSmooth[i] = smoothing * fftSmooth[i] + (1 - smoothing) * fft.getAvg(i)

            // Find max and min values ever displayed across whole spectrum
            if (fftSmooth[i] > maxVal) maxVal = fftSmooth[i]
            if (fftSmooth[i] < minVal) minVal = fftSmooth[i]
        }
    }

    private fun circularPulse() {
        /*noFill()
        stroke(0f,0f,255f)
        strokeWeight(strokeWeight)
        strokeJoin(ROUND)

        beginShape()
        var i = 0
        while (i < avgSize) {
            val angle = i.toFloat() * angOffset * 2f * PI / avgSize
            val amp = maxDrawHeight * ((amplitude * fftSmooth[i] - minVal) / spectrumScaleFactor)
            val rad = circleRadius * 2
            val x = (rad + amp) * cos(angle) + .85f
            val y = (rad + amp) * sin(angle) + .85f

            vertex(x + width / 2, y + height / 2)
            i += barStep
        }
        endShape(CLOSE)*/

        var i = 0
        while (i < avgSize) {
            val angle = i.toFloat() * angOffset * 2f * PI / avgSize
            val fftSmoothDisplay = maxDrawHeight * ((amplitude * fftSmooth[i] - minVal) / spectrumScaleFactor)
            val rad = circleRadius * 2
            val x = rad * cos(angle)
            val y = rad * sin(angle)
            val x2 = (rad + fftSmoothDisplay) * cos(angle) + .85f
            val y2 = (rad + fftSmoothDisplay) * sin(angle) + .85f

            //int c = (int) map(i, 0, fft.specSize(), 0, 470);
            val c = map(i.toFloat(), 0f, fft.specSize().toFloat(), 0f, 275f)
            colorMode(HSB)
            stroke(c, 255f, 255f)
            strokeWeight(strokeWeight)
            line(x + width / 2, y + height / 2, x2 + width / 2, y2 + height / 2)
            i += barStep
        }

        noStroke()
        fill(lerp(0f, map(ellipseR, 0f, circleRadius * 3 - 50, 0f, 255f), .23f), 255f, 255f)
        ellipse(width / 2f, height / 2f, ellipseR, ellipseR)
    }

    private fun signal() {
        var i = 0

        while (i < avgSize - 1) {
            val x1 = map(i.toFloat(), 0f, avgSize.toFloat(), 0f, width.toFloat())
            val x2 = map((i + 1).toFloat(), 0f, avgSize.toFloat(), 0f, width.toFloat())
            val amp = maxDrawHeight * ((amplitude * fftSmooth[i] - minVal) / spectrumScaleFactor)
            val y = height / 2f - 20

            val c = map(i.toFloat(), 0f, fft.specSize().toFloat(), 0f, 285f)
            stroke(c, 255f, 255f)
            strokeWeight(strokeWeight)
            line(x1, y + amp, x2, y - amp)
            i += barStep
        }
    }

    private fun inverse() {
        var i = 0
        val size = avgSize - 1

        while (i < size) {
            val x1 = map(i.toFloat(), 0f, size.toFloat(), 0f, width.toFloat())
            val x2 = map((i + 1).toFloat(), 0f, size.toFloat(), 0f, width.toFloat())

            val x3 = map(i.toFloat(), 0f, size.toFloat(), width.toFloat(), 0f)
            val x4 = map((i + 1).toFloat(), 0f, size.toFloat(), width.toFloat(), 0f)

            val amp = maxDrawHeight * ((amplitude * fftSmooth[i] - minVal) / spectrumScaleFactor)
            val y = height / 2f - 20

            val c = map(i.toFloat(), 0f, fft.specSize().toFloat(), 0f, 275f)

            stroke(c, 255f, 255f)
            strokeWeight(strokeWeight)
            line(x1, y, x2, y + amp)
            line(x3, y, x4, y - amp)
            i += barStep
        }
    }

    fun functionMusic() {
        var i = 0

        while (i < avgSize - 1) {
            val amp = maxDrawHeight * ((amplitude * fftSmooth[i] - minVal) / spectrumScaleFactor)

            val x1 = map(i.toFloat(), 0f, avgSize.toFloat(), 0f, width.toFloat())
            val x2 = map((i + 1).toFloat(), 0f, avgSize.toFloat(), 0f, width.toFloat())
            val y1 = -musicTransform(x1) + height / 2
            val y2 = -musicTransform(x2) + height / 2

            val c1 = map(i.toFloat(), 0f, fft.specSize().toFloat(), 0f, 275f)

            stroke(c1, 255f, 255f)
            strokeWeight(strokeWeight)
            line(1.7f * x1, y1 + amp, 1.7f * x2, y2 - amp)

            i += barStep
        }

        angle += 0.015f
    }

    fun musicTransform(x: Float): Float {
        return when (transformMode) {
            0 -> 75f * sin(.015f * x + angle)
            1 -> {
                val m = 2f
                val a = 325f
                m * (x - a) * signum(a - x) + m * a - height / 2 + 60 * sin(.08f * x + angle)
            }
            2 -> 60f * sin(2 * sin(2 * sin(2 * sin(2 *
                        sin(2 * sin(2 * sin(2 * sin(.032f * x))))))))
            3 -> pow(.02f * x, 2f)
            else -> 75f * sin(.015f * x + angle)
        }
    }

    fun dotWave(){
        val space = backgroundStep
        var i = -2 * space

        var rx: Float
        var ry: Float

        val rt = 400f
        val size = 1f

        while (i < width + space) {
            var j = -2 * space
            val cx = i.toFloat()

            while (j < height + space) {

                val cy = j.toFloat()

                var t = millis() / rt

                t += cos(abs(1 - cx / (width / 2))) *  TWO_PI
                t += cos(abs(1 - cy / (height / 2))) * TWO_PI
                t += offset

                rx = sin(t) * (size + map(ellipseR, 0f, circleRadius * 1.85f - 30, 0f, 25f))
                //rx = (cos(t) + sin(4*t)) * (size + map(ellipseR, 0f, circleRadius * 1.85f - 30, 0f, 25f))
                ry = rx


                if (rx > 0 && ry > 0) {
                    noStroke()
                    fill(lerp(0f, dist(cx, cy, width / 2f, height / 2f), .25f), 255f, 255f)
                    pushStyle()
                    rectMode(CENTER)
                    rect(cx, cy, rx, ry)
                    popStyle()
                    //ellipse(cx, cy, rx, ry)
                }

                j += space
            }

            i += space
        }
    }

    fun bassNationPulse() {
        pushMatrix()
        translate(width / 2f, height / 2f)

        val bass = bassKick * floor(fftSmooth[1])
        val radius = 0.75f * -(circleRadius * 2 + bass * 0.25f)
        val scale = 0.75f * amplitude

        for (i in 0 until  avgSize){
            val amp = fftSmooth[i]
            //val a = -(angOffset / 100f)
            val a = -(60f / 100f)
            val b = PI / 180f
            val angle = a * b

            rotate(angle)
            colorMode(HSB)

            val c = map(i.toFloat(), 0f, avgSize.toFloat(), 0f, 275f)
            fill(c, 255f, 255f)
            rect(0f, radius, 2f, -amp * scale)
        }

        for (i in 0 until  avgSize){
            val amp = fftSmooth[i]
            //val a = angOffset / 100f
            val a = 60f / 100f
            val b = PI / 180f

            rotate(a * b)
            colorMode(HSB)

            val c = map(i.toFloat(), avgSize.toFloat(), 0f, 275f, 0f)
            fill(c, 255f, 255f)
            rect(0f, radius, 2f, -amp * scale)
        }

        popMatrix()
    }

    private fun showSongTime() {
        if (!isSoundCloudSource) {
            val millis = player.position()
            val second = (millis / 1000) % 60
            val minute = (millis / (1000 * 60)) % 60
            val hour = (millis / (1000 * 60 * 60)) % 24

            title = "$nameOfSong - ${String.format("%02d:%02d:%02d", hour, minute, second)}"
            surface.setTitle(title)

            val posx = map(player.position().toFloat(), 0f, player.length().toFloat(), 0f, width.toFloat())

            pushStyle()

            noStroke()
            colorMode(HSB)

            fill(map(player.position().toFloat(), 0f, player.length().toFloat(), 0f, 255f), 255f, 255f)
            rect(0f, 0f, posx, 14f)

            popStyle()
        }
    }

    override fun mouseReleased() {
        if (mouseY in 0..14 && !isSoundCloudSource)
            player.cue(map(mouseX.toFloat(), 0f, width.toFloat(), 0f, player.length().toFloat()).toInt())
    }

    private fun showBackGround(step: Int) {
        var x = step
        while (x < width) {
            var y = step
            while (y < height) {
                val n = noise(x * 0.005f, y * 0.007f, frameCount * 0.02f)
                pushMatrix()
                translate(x.toFloat(), y.toFloat())

                //rotate(TWO_PI * n)
                scale(map(ellipseR, 0f, circleRadius * 1.85f - 30, 0f, 15f) * n)
                strokeWeight(.2f)
                fill(255)

                val x1 = 0f
                val y1 = 0f
                val w = .8f
                val h = .8f

                triangle(x1, y1, x1 + w / 2, y1 - h, x1 + w, y1)
                //triangle(0f,.1f, .1f,)
                //rect(0f, 0f, .8f, .8f)
                popMatrix()
                y += step
            }
            x += step
        }
    }

    /**
     * Method to load in songs to the audio player.
     * Loads local files as well as soundcloud tracks
     */
    fun loadSong(musicObj: MSong, isSetup: Boolean = false) {
        try {
            var isSCloudSrc = false

            //make sure that boolean values don't conflict in mainLoop
            if (isPaused) togglePlay()

            if (!isSetup && playerInitialized) player.close()

            if (musicObj.type == FType.File){
                nameOfSong = musicObj.title
                player = minim.loadFile(musicObj.path, bufferSize)
            }

            else if(musicObj.type == FType.SCTrack){
                isSCloudSrc = true
                nameOfSong = musicObj.title
                surface.setTitle(nameOfSong)
                player = minim.loadFile(ISoundCloud.getFullStreamUrl(musicObj.path), bufferSize)
            }

            fft = FFT(player.bufferSize(), player.sampleRate())
            fft.logAverages(minBandwidth, bandsPerOctave)
            fft.window(FFT.GAUSS)

            avgSize = fft.avgSize()
            fftSmooth = FloatArray(avgSize)

            isSoundCloudSource = isSCloudSrc
            playerNotClosed = true

            player.gain = musicGain
            player.play()

            MusicList.setNowPlaying(nameOfSong)

            //if we've gotten here then the song has loaded successfully
            //so we should let the program know that an initial song is playing so run the awesome
            //graphics instead of a black screen
            if (!initialSongLoaded)
                initialSongLoaded = true

            //since we're playing a song now, when the song stops and it's time to play another
            //it's now ok to close the audio player because the variable has been initialized,
            //otherwise an initialization exception will be thrown
            if (!playerInitialized)
                playerInitialized = true
        }
        catch (e: Exception) {EException.append(e)}
    }

    override fun keyReleased() {
        super.keyReleased()

        if (key == 'q') EventQueue.invokeLater { InstructionsWindow.getInstance(null) }

        if (key == 'c') changeTransform()

        if (key == 'n') MusicList.nextSong()

        if (key == 'm') toggleMute()

        if (key == 't') EventQueue.invokeLater { MusicList.getInstance() }

        if (key == 's') toggleBackground()

        if (key == 'x') changeVisual()

        if (key == 'z') EventQueue.invokeLater { EException.getInstance(null) }

        if (keyCode == KeyEvent.VK_SPACE) togglePlay()

        if (key == 'v') addSongs(fileChooser())

        if ((keyEvent.isShiftDown && keyEvent.isControlDown) && keyCode == KeyEvent.VK_Q) System.exit(0)
    }

    companion object {
        fun changeVisual() {
            visualMode = ++visualMode % 6
        }

        fun changeTransform() {
            transformMode = ++transformMode % 4
        }

        fun toggleBackground(){
            drawBackground = !drawBackground
        }

        fun togglePlay() {
            isPaused = if (player.isPlaying) {
                player.pause()
                true
            }
            else {
                player.play()
                false
            }

            cp5.getController("pause-song").setCaptionLabel(if (isPaused) "play-song" else "pause-song")
        }

        /**
         * Method to either mute or un-mute the audio player.
         * Toggles mute and also updates the gui buttons caption
         */
        fun toggleMute() {
            isMute = if (player.isMuted) {
                player.unmute()
                false
            }
            else {
                player.mute()
                true
            }

            cp5.getController("mute-song").setCaptionLabel(if (isMute) "un-mute-song" else "mute-song")
        }

        fun addSongs(songs: ArrayList<MSong>?) {
            if (songs != null && songs.size > 0) {
                for (s: MSong in songs) MusicList.visListModel.addElement(s)
                if (!initialSongLoaded) MusicList.nextSong()
            }

            MusicList.jList.model = MusicList.visListModel
        }

        fun fileChooser(): ArrayList<MSong>? {
            try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())} catch (x: Exception) {EException.append(x)}

            val fileList: Array<File>

            val dialog = FileDialog(null as Frame?, "Select a Song :D", FileDialog.LOAD)
            dialog.isMultipleMode = true
            dialog.file = fileFilterStr()
            dialog.isVisible = true

            fileList = dialog.files

            /*if (Utils.isWindows()){
                val chooser = JFileChooser( )
                chooser.dialogTitle = "Select a song :D"
                chooser.dialogType = JFileChooser.OPEN_DIALOG
                chooser.isMultiSelectionEnabled = true
                chooser.fileFilter = FileNameExtensionFilter(fileFilterStr(), *audioFormats)
                chooser.showOpenDialog(null)

                fileList = chooser.selectedFiles
            }
            else {
                val dialog = FileDialog(null as Frame?, "Select a Song :D", FileDialog.LOAD)
                dialog.isMultipleMode = true
                dialog.setFilenameFilter { dir, file -> isSongCorrectFormat(file, audioFormats) }
                dialog.file = fileFilterStr()
                dialog.isVisible = true

                fileList = dialog.files
            }*/

            return if (fileList.isNotEmpty()) {
                val msongList: ArrayList<MSong> = ArrayList()

                for (file in fileList)
                    msongList.add(MSong(FType.File, file.absolutePath, FilenameUtils.getBaseName(file.name)))

                msongList
            }
            else null
        }

        fun fileFilterStr(): String {
            var s = ""
            for (format in audioFormats) s += "*.$format; "
            return s
        }
        fun isSongCorrectFormat(file: String, extensions: Array<String>): Boolean {
            return extensions.any { file.endsWith(it) }
        }

        fun showError(message: String, title: String) {
            JOptionPane.showConfirmDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE)
        }
    }
}

