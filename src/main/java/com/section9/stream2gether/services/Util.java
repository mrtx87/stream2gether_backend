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


    public static String[] joinMessages = {
            "Aufgepasst: %s betritt das Pakett.",
            "Mit Pauken und Trompeten: Es beehrt %s die anwesende Gesellschaft!",
            "In bescheidener Unbescheidenheit betritt %s den Raum."
            "An scheibar unheilbarer Würde erkrankt, beschreitet %s den Raum.",
            "Aus den Schatten tritt ins Licht - %s!",
            "Wachen! Wachen! Ach ne, ist doch nur %s.",
            "%s ante portas!"
    };

    public static String getRandomJoinMessage(String userName){
        String message = joinMessages[(int)(Math.random()*joinMessages.length)];
        return String.format(message, userName);
    }
}
