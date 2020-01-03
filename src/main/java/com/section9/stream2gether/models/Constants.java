package com.section9.stream2gether.models;

import com.section9.stream2gether.services.Util;

public class Constants {

    //TEXTS
    public static final String CHAT_MESSAGE_INIT_TEXT = "Raum angelegt. Es kann los gehen.";

    //PURPOSES
    public static final String PURPOSE_DISCONNECTED_CLIENT = "disconnected-client";
    public static final String PURPOSE_SEND_CHATMESSAGE = "send-chatmessage";




    //PlaylistStates

    //PLAYERSTATES
    public static final int NOTSTARTED = -1;
    public static final int FINISHED = 0;
    public static final int PLAYING = 1;
    public static final int PAUSED = 2;
    public static final int BUFFERING = 3;
    public static final int PLACED = 5;

    public static final float DEFAULT_PLAYBACK_RATE = 1;


}
