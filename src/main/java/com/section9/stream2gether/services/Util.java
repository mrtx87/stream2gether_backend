package com.section9.stream2gether.services;

import com.section9.stream2gether.models.ChatMessage;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Util {

    public static List<String> listOfNames = List.of("Lulli", "Lalli", "HusterHihi", "Gorna");
    public static List<String> listOfAvatars = List.of("avatar_1", "avatar_2", "avatar_3", "avatar_4");

    public static String getRandomName(){
        return listOfNames.get((int) (Math.random() * listOfNames.size()));
    }

    public static String getRandomAvatar(){
        return listOfAvatars.get((int) (Math.random() * listOfAvatars.size()));
    }

    public static ChatMessage createChatMessage(UUID from, String body){
        ChatMessage message =  new ChatMessage();
        message.setId(UUID.randomUUID());
        message.setFrom(from);
        message.setBody(body);
        message.setCreatedAt(Instant.now());
        return message;
    }
}
