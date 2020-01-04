package com.section9.stream2gether.services;

import com.section9.stream2gether.models.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Util {

    public static List<String> listOfNames = List.of("Lulli", "Lalli", "HusterHihi", "Gorna", "Torsten", "Walter", "Tom", "Sven", "Markus", "Karsten", "Timmi");
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

    public static VideoPlayerSettings createVideoPlayerSettings(float playbackRate, int state, int timestamp, Video video) {
        VideoPlayerSettings videoPlayerSettings = new VideoPlayerSettings();
        videoPlayerSettings.setApi(video != null ? video.getApi() : "youtube");
        videoPlayerSettings.setPlaybackRate(playbackRate);
        videoPlayerSettings.setTimestamp(timestamp);
        videoPlayerSettings.setVideo(video);
        videoPlayerSettings.setState(state);
        return videoPlayerSettings;
    }

    public static VideoPlayerAction createVideoPlayerAction(UUID from, float playbackRate, int state, int timestamp, Video video) {
        VideoPlayerAction videoPlayerAction=  new VideoPlayerAction();
        videoPlayerAction.setFrom(from);
        videoPlayerAction.setVideoPlayerSettings(createVideoPlayerSettings(playbackRate, state, timestamp, video));
        return videoPlayerAction;
    }

    public static VideoPlayerAction createEmptyVideoPlayerAction(UUID from) {
        VideoPlayerAction videoPlayerAction =  new VideoPlayerAction();
        videoPlayerAction.setFrom(from);
        return videoPlayerAction;
    }

    //DEFAULT PLAYERSTATE
    public static VideoPlayerSettings DEFAULT_VIDEO_PLAYER_SETTINGS() {
        return createVideoPlayerSettings(1, Constants.PAUSED, 0, DEFAULT_VIDEO());
    }

    public static PlaylistState DEFAULT_PLAYLIST_STATE() {
        return new PlaylistState(PlaylistService.PL_CMD_SEQUENCE_ORDER, PlaylistService.PL_CMD_NO_REPEAT);
    }


    public static Video DEFAULT_VIDEO() {
        Video video = new Video();
        video.setId(UUID.randomUUID());
        video.setApi("youtube");
        video.setVideoId("XDkSCBdyUuI");
        return video;
    }


    public static String[] joinMessages = {
            "Aufgepasst: %s betritt das Pakett.",
            "Mit Pauken und Trompeten: Es beehrt %s die anwesende Gesellschaft!",
            "In bescheidener Unbescheidenheit betritt %s den Raum.",
            "An scheinbar unheilbarer Würde erkrankt, beschreitet %s den Raum.",
            "Aus den Schatten tritt ins Licht - %s!",
            "Wachen! Wachen! Ach ne, ist doch nur %s.",
            "%s ante portas!"
    };

    public static String getRandomJoinMessage(String userName){
        String message = joinMessages[(int)(Math.random()*joinMessages.length)];
        return String.format(message, userName);
    }

    public static boolean isNotPresent(Object obj) {
        return obj == null;
    }

    public static boolean isPresent(Object obj) {
        return obj != null;
    }
}
