package com.section9.stream2gether.models;

import java.util.*;
import java.util.stream.Collectors;

public class Room {

    private UUID id;
    private Map<UUID, User> users;
    private List<ChatMessage> chatMessages;

    public Room() {
        this.id = UUID.randomUUID();
        users = new HashMap<>();
        chatMessages = new ArrayList<>();
    }

    public Map<UUID, User> getUsers(){
        return users;
    }

    public List<User> getUsersAsListOfUUID(){
        return users.values().stream().collect(Collectors.toList());
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
