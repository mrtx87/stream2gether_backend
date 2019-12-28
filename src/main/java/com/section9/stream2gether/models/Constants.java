package com.section9.stream2gether.models;

public class Constants {

    public static String[] joinMessages = {
            "Aufgepasst: %s betritt das Pakett.",
            "Mit Pauken und Trompeten: Es beehrt %s die anwesende Gesellschaft!",
            "In bescheidener Unbescheidenheit betritt %s den Raum."
    };


    public static String CHAT_MESSAGE_INIT_TEXT = "Raum angelegt. Es kann los gehen.";


    public static String getRandomJoinMessage(String userName){
        String message = joinMessages[(int)(Math.random()*joinMessages.length)];
        return String.format(message, userName);
    }

}
