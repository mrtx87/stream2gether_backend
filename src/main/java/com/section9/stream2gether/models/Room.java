package com.section9.stream2gether.models;

import com.section9.stream2gether.services.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Room {

    private VideoPlayerSettings videoPlayerSettings;
    private PlaylistState playlistState;
    private UUID id;
    private Map<UUID, User> users;
    private List<ChatMessage> chatMessages;
    private List<Video> playlist;

    public Room() {
        this.id = UUID.randomUUID();
        users = new HashMap<>();
        chatMessages = new ArrayList<>();
        playlist = new ArrayList<>();
        videoPlayerSettings = Util.DEFAULT_VIDEO_PLAYER_SETTINGS();
        playlistState = Util.DEFAULT_PLAYLIST_STATE();
    }

    public void addUser(User user){
        users.put(user.id, user);
    }
    public void saveUser(User user){
        users.put(user.id, user);
    }
    public boolean hasUser(UUID userId) {
        return users.containsKey(userId);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Map<UUID, User> getUsers() {
        return users;
    }

    public List<UUID> getUserIds() {
        return users.keySet().stream().collect(Collectors.toList());
    }

    public List<User> getUserList() {
        return users.values().stream().collect(Collectors.toList());
    }

    public void setUsers(Map<UUID, User> users) {
        this.users = users;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public List<Video> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Video> playlist) {
        this.playlist = playlist;
    }

    public User removeUser(UUID id) {
        return users.remove(id);
    }

    public VideoPlayerSettings getVideoPlayerSettings() {
        return videoPlayerSettings;
    }

    public void setVideoPlayerSettings(VideoPlayerSettings videoPlayerSettings) {
        this.videoPlayerSettings = videoPlayerSettings;
    }

    public boolean hasCurrentVideo() {
        return this.videoPlayerSettings.getVideo() != null;
    }

    public Video getCurrentVideo() {
        return this.videoPlayerSettings.getVideo();
    }

    public void setCurrentVideo(Video video) {
        this.videoPlayerSettings.setVideo(video);
    }

    public PlaylistState getPlaylistState() { return playlistState; }

    public boolean playlistIsEmpty() { return playlist.size() == 0; }

    public void setPlaylistState(PlaylistState playlistState) { this.playlistState = playlistState; }
}
