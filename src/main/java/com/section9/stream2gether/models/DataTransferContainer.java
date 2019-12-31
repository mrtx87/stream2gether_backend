package com.section9.stream2gether.models;

import java.util.List;
import java.util.UUID;

public class DataTransferContainer {

    UUID from;
    User user;
    List<User> users;
    UUID roomId;
    ChatMessage chatMessage;
    DTC_PURPOSE purpose;

    public DTC_PURPOSE getPurpose() {
        return purpose;
    }

    public void setPurpose(DTC_PURPOSE purpose) {
        this.purpose = purpose;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    public UUID getFrom() {
        return from;
    }

    public void setFrom(UUID from) {
        this.from = from;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }
}
