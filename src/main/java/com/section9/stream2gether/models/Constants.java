package com.section9.stream2gether.models;

import com.section9.stream2gether.services.Util;

public class Constants {

    public static final String YOUTUBE_ID = "youtube";
    public static final String DIRECT_VIDEO_ID = "direct-video";

    //TEXTS
    public static final String CHAT_MESSAGE_INIT_TEXT = "Raum angelegt. Es kann los gehen.";

    //PURPOSES
    public static final int PURPOSE_DISCONNECTED_CLIENT = 501;
    public static final int PURPOSE_SEND_CHATMESSAGE = 502;
    public static final int PURPOSE_USER_JOINED = 503;





    //PlaylistStates

    //PLAYERSTATES
    public static final int NOTSTARTED = -1;
    public static final int FINISHED = 0;
    public static final int PLAYING = 1;
    public static final int PAUSED = 2;
    public static final int BUFFERING = 3;
    public static final int PLACED = 5;
    public static final int REQUEST_SYNC = 100;
    public static final int SYNC_REQUEST_RESPONSE = 200;

    public static final float DEFAULT_PLAYBACK_RATE = 1;


}
