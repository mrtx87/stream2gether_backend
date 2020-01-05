package com.section9.stream2gether.models;

import java.time.Instant;
import java.util.UUID;

public class Video {

    String api;
    UUID id;
    String videoId;
    String title;
    String iconUrl;
    Instant createdAt;
    String channelTitle;

    public String getApi() { return api; }

    public void setApi(String api) { this.api = api; }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() { return iconUrl; }

    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getChannelTitle() { return channelTitle; }

    public void setChannelTitle(String channelTitle) { this.channelTitle = channelTitle; }
}
