package com.section9.stream2gether.models;

import java.util.List;
import java.util.UUID;

public class PlaylistCommand {

    UUID from;
    String action;
    int index;
    int previousIndex;
    Video video;
    List<Video> playlist;

    public UUID getFrom() {
        return from;
    }
    public void setFrom(UUID from) {
        this.from = from;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getPreviousIndex() {
        return previousIndex;
    }
    public void setPreviousIndex(int previousIndex) {
        this.previousIndex = previousIndex;
    }
    public Video getVideo() {
        return video;
    }
    public void setVideo(Video video) {
        this.video = video;
    }
    public List<Video> getPlaylist() { return playlist; }
    public void setPlaylist(List<Video> playlist) { this.playlist = playlist; }
}
