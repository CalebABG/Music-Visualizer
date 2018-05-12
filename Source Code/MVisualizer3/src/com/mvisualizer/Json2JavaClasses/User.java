package com.mvisualizer.Json2JavaClasses;

public class User {
    private Integer id;
    private String kind;
    private String permalink;
    private String username;
    private String last_modified;
    private String uri;
    private String permalink_url;
    private String avatar_url;
    private String country;
    private String full_name;
    private String description;
    private String city;
    private String discogs_name;
    private String myspace_name;
    private String website;
    private String website_title;
    private Boolean online;
    private Integer track_count;
    private Integer playlist_count;
    private String plan;
    private Integer public_favorites_count;
    private Integer followers_count;
    private Integer followings_count;

    public User() { }

    public Integer getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getUsername() {
        return username;
    }

    public String getLastModified() {
        return last_modified;
    }

    public String getUri() {
        return uri;
    }

    public String getPermalinkUrl() {
        return permalink_url;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public String getCountry() {
        return country;
    }

    public String getFullName() {
        return full_name;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public String getDiscogsName() {
        return discogs_name;
    }

    public String getMyspaceName() {
        return myspace_name;
    }

    public String getWebsite() {
        return website;
    }

    public String getWebsiteTitle() {
        return website_title;
    }

    public Boolean getOnline() {
        return online;
    }

    public Integer getTrackCount() {
        return track_count;
    }

    public Integer getPlaylistCount() {
        return playlist_count;
    }

    public String getPlan() {
        return plan;
    }

    public Integer getPublicFavoritesCount() {
        return public_favorites_count;
    }

    public Integer getFollowersCount() {
        return followers_count;
    }

    public Integer getFollowingsCount() {
        return followings_count;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", kind='" + kind + '\'' +
                ", permalink='" + permalink + '\'' +
                ", username='" + username + '\'' +
                ", last_modified='" + last_modified + '\'' +
                ", uri='" + uri + '\'' +
                ", permalink_url='" + permalink_url + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", country='" + country + '\'' +
                ", full_name='" + full_name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", discogs_name='" + discogs_name + '\'' +
                ", myspace_name='" + myspace_name + '\'' +
                ", website='" + website + '\'' +
                ", website_title='" + website_title + '\'' +
                ", online=" + online +
                ", track_count=" + track_count +
                ", playlist_count=" + playlist_count +
                ", plan='" + plan + '\'' +
                ", public_favorites_count=" + public_favorites_count +
                ", followers_count=" + followers_count +
                ", followings_count=" + followings_count +
                '}';
    }
}
