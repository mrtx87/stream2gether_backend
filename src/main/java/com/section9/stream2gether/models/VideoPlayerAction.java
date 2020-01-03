package com.section9.stream2gether.models;

import java.util.UUID;

public class VideoPlayerAction {

    UUID from;
    VideoPlayerSettings videoPlayerSettings;

    public UUID getFrom() {
        return from;
    }

    public void setFrom(UUID from) { this.from = from; }

    public VideoPlayerSettings getVideoPlayerSettings() { return videoPlayerSettings; }

    public void setVideoPlayerSettings(VideoPlayerSettings playerState) {
        this.videoPlayerSettings = playerState;
    }
}
