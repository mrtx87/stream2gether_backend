package com.section9.stream2gether.models;

import java.util.*;

public class Room {

    private UUID id;
    private Map<UUID, User> users;
    private List<ChatMessage> chatMessages;

    public Room() {
        this.id = UUID.randomUUID();
        users = new HashMap<>();
        chatMessages = new ArrayList<>();
    }

    public void addUser(User user){
        users.put(user.id, user);
    }
    public void saveUser(User user){
        users.put(user.id, user);
    }

    public UUID getId() {
        return id;
    }
}
