package com.section9.stream2gether.services;

import com.section9.stream2gether.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class PlaylistService {

    //Playlist Commands
    public static final String PL_CMD_APPEND = "append";
    public static final String PL_CMD_INSERT_AT = "insert-at";
    public static final String PL_CMD_DELETE = "delete";
    public static final String PL_CMD_INSERT_BELOW_CURRENT = "insert-below-current";
    public static final String PL_CMD_INSERT_AS_CURRENT = "insert-as-current";
    public static final String PL_CMD_START_NEXT = "start-next";
    public static final String PL_CMD_REPEAT = "repeat-all";
    public static final String PL_CMD_REPEAT_ONE = "repeat-one";
    public static final String PL_CMD_NO_REPEAT = "no-repeat";
    public static final String PL_CMD_SEQUENCE_ORDER = "sequence-order";
    public static final String PL_CMD_RANDOM_ORDER = "random-order";
    public static final String PL_CMD_IMPORT_PLAYLIST = "import-playlist";
    public static final String PL_CMD_CHANGE_POSITION = "change-position";

    @Autowired
    SimpMessagingTemplate messagingService;

    public PlaylistService() {
    }

    public boolean executePlaylistCommand(Room room, PlaylistCommand playlistCmd) {
        List<Video> playlist = room.getPlaylist();
        Video currentVideo = room.getCurrentVideo();
        int currentVideoIndex = playlist.indexOf(currentVideo);
        switch(playlistCmd.getAction()) {
            case PL_CMD_APPEND:
                playlistCmd.getVideo().setId(UUID.randomUUID());
                playlist.add(playlistCmd.getVideo());
                if(playlist.size() == 1) {
                    room.setCurrentVideo(getNextVideo(room));
                    intermediateNextVideoPlayerAction(room, playlistCmd.getFrom());
                }
                break;
            case PL_CMD_INSERT_AT:

                break;
            case PL_CMD_DELETE:

                break;
            case PL_CMD_INSERT_BELOW_CURRENT: {
                playlistCmd.getVideo().setId(UUID.randomUUID());
                if (currentVideoIndex > -1) {
                    int belowIndex = currentVideoIndex + 1;
                    playlistCmd.setIndex(belowIndex);
                    playlist.add(belowIndex, playlistCmd.getVideo());
                } else {
                    if (room.playlistIsEmpty()) {
                        playlist.add(playlistCmd.getVideo());
                        playlistCmd.setIndex(0);
                    }
                }
            }
            break;
            case PL_CMD_INSERT_AS_CURRENT : {
                playlistCmd.getVideo().setId(UUID.randomUUID());
                if (currentVideoIndex > -1) {
                    playlistCmd.setIndex(currentVideoIndex);
                    playlist.add(currentVideoIndex, playlistCmd.getVideo());
                } else {
                    if (room.playlistIsEmpty()) {
                        playlist.add(playlistCmd.getVideo());
                        playlistCmd.setIndex(0);
                    }
                }
                room.setCurrentVideo(playlistCmd.getVideo());
                room.getVideoPlayerSettings().setState(Constants.PLAYING);
                intermediateNextVideoPlayerAction(room, playlistCmd.getFrom());
            }
            break;
            case PL_CMD_START_NEXT : {
                Video nextVideo = getNextVideo(room);
                room.setCurrentVideo(nextVideo);
                playlistCmd.setVideo(nextVideo);
                intermediateNextVideoPlayerAction(room, playlistCmd.getFrom());
                return true;
            }
            case PL_CMD_SEQUENCE_ORDER : {
                setPlaylistOrder(room, PL_CMD_SEQUENCE_ORDER);
            }
            break;
            case PL_CMD_RANDOM_ORDER : {
                setPlaylistOrder(room, PL_CMD_RANDOM_ORDER);
            }
            break;
            case PL_CMD_NO_REPEAT : {
                setPlaylistRepetition(room, PL_CMD_NO_REPEAT);
            }
            break;
            case PL_CMD_REPEAT : {
                setPlaylistRepetition(room, PL_CMD_REPEAT);
            }
            break;
            case PL_CMD_REPEAT_ONE : {
                setPlaylistRepetition(room, PL_CMD_REPEAT_ONE);
            }
            break;
            case PL_CMD_IMPORT_PLAYLIST :
            break;
            case PL_CMD_CHANGE_POSITION :
                break;
            default: return false;
        }

        notifyUsersAboutPlaylistCommand(room.getUserIds(), playlistCmd);
        return true;
    }

    private void intermediateNextVideoPlayerAction(Room room, UUID from) {
        VideoPlayerAction videoPlayerAction = Util.createEmptyVideoPlayerAction(from);
        VideoPlayerSettings currentVideoPlayerSettings = room.getVideoPlayerSettings();
        currentVideoPlayerSettings.setTimestamp(0);
        videoPlayerAction.setVideoPlayerSettings(currentVideoPlayerSettings);
        notifyUsersAboutVideoPlayerAction(room.getUserIds(), videoPlayerAction);
    }


    public boolean executeVideoPlayerAction(Room room, VideoPlayerAction videoPlayerAction) {
        Video currentVideo = room.getCurrentVideo();
        room.setVideoPlayerSettings(videoPlayerAction.getVideoPlayerSettings());
        notifyUsersAboutVideoPlayerAction(room.getUserIds(), videoPlayerAction);
        return false;
    }

    public Video getNextVideo(Room room) {
        PlaylistState playlistState = room.getPlaylistState();
        List<Video> playlist = room.getPlaylist();
        if(playlist.size() == 0) {
            return null;
        }
        if(playlistState.getOrder().equals(PL_CMD_SEQUENCE_ORDER)) {
            if(!room.hasCurrentVideo()) {
                return playlist.get(0);
            }else{
                int currentVideoIndex = playlist.indexOf(room.getCurrentVideo());
                if(currentVideoIndex < playlist.size()-1) {
                    Video nextVideo = playlist.get(currentVideoIndex + 1);
                    return nextVideo;
                }else{
                    if(playlistState.getRepeat().equals(PL_CMD_NO_REPEAT)){
                        return null;
                    }
                    if(playlistState.getRepeat().equals(PL_CMD_REPEAT_ONE)) {
                        return room.getCurrentVideo();
                    }else{
                        return playlist.get(0);
                    }
                }
            }
        }

        if(playlistState.getOrder().equals(PL_CMD_RANDOM_ORDER)) {

        }
        return null;
    }

    private void setPlaylistOrder(Room room, String order) {
        room.getPlaylistState().setOrder(order);
    }

    private void setPlaylistRepetition(Room room, String repeat) {
        room.getPlaylistState().setRepeat(repeat);
    }


    private void notifyUserAboutPlaylistCommand(UUID userId, PlaylistCommand playlistCmd) {
        this.messagingService.convertAndSend("/client/" + userId +"/playlist", playlistCmd);
    }

    private void notifyUsersAboutPlaylistCommand(List<UUID> userIds, PlaylistCommand playlistCmd) {
        userIds.stream().forEach(userId -> notifyUserAboutPlaylistCommand(userId, playlistCmd));
    }

    private void notifyUserAboutVideoPlayerAction(UUID userId, VideoPlayerAction videoPlayerAction) {
        this.messagingService.convertAndSend("/client/" + userId +"/video-player-command", videoPlayerAction);
    }

    private void notifyUsersAboutVideoPlayerAction(List<UUID> userIds, VideoPlayerAction videoPlayerAction) {
        userIds.stream().forEach(userId -> notifyUserAboutVideoPlayerAction(userId, videoPlayerAction));
    }

}
