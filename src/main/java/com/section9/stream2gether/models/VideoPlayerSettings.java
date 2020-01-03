package com.section9.stream2gether.models;

public class VideoPlayerSettings {
    String api;
    float playbackRate;
    int state;
    int timestamp;
    Video video;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
    public int getState() { return state; }

    public void setState(int state) { this.state = state; }

    public float getPlaybackRate() { return playbackRate; }

    public void setPlaybackRate(float playbackRate) { this.playbackRate = playbackRate; }

    public int getTimestamp() { return timestamp; }

    public void setTimestamp(int timestamp) { this.timestamp = timestamp; }

    public Video getVideo() { return video; }

    public void setVideo(Video video) { this.video = video; }

}
