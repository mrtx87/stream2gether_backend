package com.section9.stream2gether.models;

import java.util.UUID;

public class Action {

    UUID origin;
    private String playbackRate;
    private String playerState;
    private String timestamp;

    public UUID getOrigin() {
        return origin;
    }

    public void setOrigin(UUID origin) {
        this.origin = origin;
    }

    public String getPlaybackRate() {
        return playbackRate;
    }

    public void setPlaybackRate(String playbackRate) {
        this.playbackRate = playbackRate;
    }

    public String getPlayerState() {
        return playerState;
    }

    public void setPlayerState(String playerState) {
        this.playerState = playerState;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
