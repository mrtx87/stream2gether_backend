package com.section9.stream2gether.models;

public class VideoPlayerSetting {
    String api;
    float playbackRate;
    VideoPlayerStateType playerState;
    int timestamp;
    Video video;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public float getPlaybackRate() {
        return playbackRate;
    }

    public void setPlaybackRate(float playbackRate) {
        this.playbackRate = playbackRate;
    }

    public VideoPlayerStateType getPlayerState() {
        return playerState;
    }

    public void setPlayerState(VideoPlayerStateType playerState) {
        this.playerState = playerState;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

}
