package com.mvisualizer.Json2JavaClasses;

public class Track {
    private String kind;
    private Integer id;
    private String created_at;
    private Integer user_id;
    private Integer duration;
    private Boolean commentable;
    private String state;
    private Integer original_content_size;
    private String last_modified;
    private String sharing;
    private String tag_list;
    private String permalink;
    private Boolean streamable;
    private String embeddable_by;
    private String purchase_url;
    private String purchase_title;
    private Integer label_id;
    private String genre;
    private String title;
    private String description;
    private String label_name;
    private Integer release;
    private String track_type;
    private String key_signature;
    private String isrc;
    private String video_url;
    private Integer bpm;
    private Integer release_year;
    private Integer release_month;
    private Integer release_day;
    private String original_format;
    private String license;
    private String uri;
    private User user;
    private String permalink_url;
    private String artwork_url;
    private String stream_url;
    private String download_url;
    private Integer playback_count;
    private Integer download_count;
    private Integer favoritings_count;
    private Integer reposts_count;
    private Integer comment_count;
    private Boolean downloadable;
    private String waveform_url;
    private String attachments_uri;

    public Track() { }

    public String getKind() {
        return kind;
    }

    public Integer getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Integer getDuration() {
        return duration;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public String getState() {
        return state;
    }

    public Integer getOriginal_content_size() {
        return original_content_size;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public String getSharing() {
        return sharing;
    }

    public String getTag_list() {
        return tag_list;
    }

    public String getPermalink() {
        return permalink;
    }

    public Boolean getStreamable() {
        return streamable;
    }

    public String getEmbeddable_by() {
        return embeddable_by;
    }

    public String getPurchase_url() {
        return purchase_url;
    }

    public String getPurchase_title() {
        return purchase_title;
    }

    public Integer getLabel_id() {
        return label_id;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLabel_name() {
        return label_name;
    }

    public Integer getRelease() {
        return release;
    }

    public String getTrack_type() {
        return track_type;
    }

    public String getKey_signature() {
        return key_signature;
    }

    public String getIsrc() {
        return isrc;
    }

    public String getVideo_url() {
        return video_url;
    }

    public Integer getBpm() {
        return bpm;
    }

    public Integer getRelease_year() {
        return release_year;
    }

    public Integer getRelease_month() {
        return release_month;
    }

    public Integer getRelease_day() {
        return release_day;
    }

    public String getOriginal_format() {
        return original_format;
    }

    public String getLicense() {
        return license;
    }

    public String getUri() {
        return uri;
    }

    public User getUser() {
        return user;
    }

    public String getPermalink_url() {
        return permalink_url;
    }

    public String getArtwork_url() {
        return artwork_url;
    }

    public String getStream_url() {
        return stream_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public Integer getPlayback_count() {
        return playback_count;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public Integer getFavoritings_count() {
        return favoritings_count;
    }

    public Integer getReposts_count() {
        return reposts_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public String getWaveform_url() {
        return waveform_url;
    }

    public String getAttachments_uri() {
        return attachments_uri;
    }

    @Override
    public String toString() {
        return "Track{" +
                "kind='" + kind + '\'' +
                ", id=" + id +
                ", created_at='" + created_at + '\'' +
                ", user_id=" + user_id +
                ", duration=" + duration +
                ", commentable=" + commentable +
                ", state='" + state + '\'' +
                ", original_content_size=" + original_content_size +
                ", last_modified='" + last_modified + '\'' +
                ", sharing='" + sharing + '\'' +
                ", tag_list='" + tag_list + '\'' +
                ", permalink='" + permalink + '\'' +
                ", streamable=" + streamable +
                ", embeddable_by='" + embeddable_by + '\'' +
                ", purchase_url='" + purchase_url + '\'' +
                ", purchase_title='" + purchase_title + '\'' +
                ", label_id=" + label_id +
                ", genre='" + genre + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", label_name='" + label_name + '\'' +
                ", release=" + release +
                ", track_type='" + track_type + '\'' +
                ", key_signature='" + key_signature + '\'' +
                ", isrc='" + isrc + '\'' +
                ", video_url='" + video_url + '\'' +
                ", bpm=" + bpm +
                ", release_year=" + release_year +
                ", release_month=" + release_month +
                ", release_day=" + release_day +
                ", original_format='" + original_format + '\'' +
                ", license='" + license + '\'' +
                ", uri='" + uri + '\'' +
                ", user=" + user +
                ", permalink_url='" + permalink_url + '\'' +
                ", artwork_url='" + artwork_url + '\'' +
                ", stream_url='" + stream_url + '\'' +
                ", download_url='" + download_url + '\'' +
                ", playback_count=" + playback_count +
                ", download_count=" + download_count +
                ", favoritings_count=" + favoritings_count +
                ", reposts_count=" + reposts_count +
                ", comment_count=" + comment_count +
                ", downloadable=" + downloadable +
                ", waveform_url='" + waveform_url + '\'' +
                ", attachments_uri='" + attachments_uri + '\'' +
                '}';
    }
}
