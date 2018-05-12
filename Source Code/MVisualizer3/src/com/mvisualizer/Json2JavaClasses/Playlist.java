package com.mvisualizer.Json2JavaClasses;

import java.util.ArrayList;

public class Playlist {
    private Integer duration;
    private Integer release_day;
    private String permalink_url;
    private String genre;
    private String permalink;
    private String purchase_url;
    private Integer release_month;
    private String description;
    private String uri;
    private String label_name;
    private String tag_list;
    private Integer release_year;
    private Integer track_count;
    private Integer user_id;
    private String last_modified;
    private String license;
    private ArrayList<Track> tracks;
    private String playlist_type;
    private Integer id;
    private Boolean downloadable;
    private String sharing;
    private String created_at;
    private Integer release;
    private String kind;
    private String title;
    private String type;
    private String purchase_title;
    private String artwork_url;
    private String ean;
    private Boolean streamable;
    private User user;
    private String embeddable_By;
    private Integer label_id;

    public Playlist() { }

    public Integer getDuration() {
        return duration;
    }

    public Integer getRelease_day() {
        return release_day;
    }

    public String getPermalink_url() {
        return permalink_url;
    }

    public String getGenre() {
        return genre;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getPurchase_url() {
        return purchase_url;
    }

    public Integer getRelease_month() {
        return release_month;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public String getLabel_name() {
        return label_name;
    }

    public String getTag_list() {
        return tag_list;
    }

    public Integer getRelease_year() {
        return release_year;
    }

    public Integer getTrack_count() {
        return track_count;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public String getLicense() {
        return license;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public String getPlaylist_type() {
        return playlist_type;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public String getSharing() {
        return sharing;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Integer getRelease() {
        return release;
    }

    public String getKind() {
        return kind;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getPurchase_title() {
        return purchase_title;
    }

    public String getArtwork_url() {
        return artwork_url;
    }

    public String getEan() {
        return ean;
    }

    public Boolean getStreamable() {
        return streamable;
    }

    public User getUser() {
        return user;
    }

    public String getEmbeddable_By() {
        return embeddable_By;
    }

    public Integer getLabel_id() {
        return label_id;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "duration=" + duration +
                ", release_day=" + release_day +
                ", permalink_url='" + permalink_url + '\'' +
                ", genre='" + genre + '\'' +
                ", permalink='" + permalink + '\'' +
                ", purchase_url='" + purchase_url + '\'' +
                ", release_month=" + release_month +
                ", description='" + description + '\'' +
                ", uri='" + uri + '\'' +
                ", label_name='" + label_name + '\'' +
                ", tag_list='" + tag_list + '\'' +
                ", release_year=" + release_year +
                ", track_count=" + track_count +
                ", user_id=" + user_id +
                ", last_modified='" + last_modified + '\'' +
                ", license='" + license + '\'' +
                ", tracks=" + tracks +
                ", playlist_type='" + playlist_type + '\'' +
                ", id=" + id +
                ", downloadable=" + downloadable +
                ", sharing='" + sharing + '\'' +
                ", created_at='" + created_at + '\'' +
                ", release=" + release +
                ", kind='" + kind + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", purchase_title='" + purchase_title + '\'' +
                ", artwork_url='" + artwork_url + '\'' +
                ", ean='" + ean + '\'' +
                ", streamable=" + streamable +
                ", user=" + user +
                ", embeddable_By='" + embeddable_By + '\'' +
                ", label_id=" + label_id +
                '}';
    }
}
