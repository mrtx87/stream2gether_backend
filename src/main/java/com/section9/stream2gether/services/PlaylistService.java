package com.section9.stream2gether.services;

import com.section9.stream2gether.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


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


    private int indexOfVideo(Room room, Video video) {
        for(int i = 0; i < room.getPlaylist().size(); i++) {
            if(video.getId().equals(room.getPlaylist().get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

    public boolean executePlaylistCommand(Room room, PlaylistCommand playlistCmd) {
        List<Video> playlist = room.getPlaylist();
        Video currentVideo = room.getCurrentVideo();
        int currentVideoIndex = indexOfVideo(room, currentVideo);

        switch(playlistCmd.getAction()) {
            case PL_CMD_APPEND:
                playlistCmd.getVideo().setId(UUID.randomUUID());
                playlist.add(playlistCmd.getVideo());
                if(playlist.size() == 1) {
                    room.setCurrentVideo(getNextVideo(room));
                    executeNotifyAboutNextVideo(room, playlistCmd.getFrom());
                }
                break;
            case PL_CMD_INSERT_AT:{
                playlistCmd.getVideo().setId(UUID.randomUUID());
                playlist.add(playlistCmd.getIndex(), playlistCmd.getVideo());
            }
                break;
            case PL_CMD_CHANGE_POSITION : {
                Video video = playlist.get(playlistCmd.getPreviousIndex());
                playlist.remove(playlistCmd.getPreviousIndex());
                playlist.add(playlistCmd.getIndex(), video);
            }
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
                executeNotifyAboutNextVideo(room, playlistCmd.getFrom());
            }
            break;
            case PL_CMD_START_NEXT : {
                Video nextVideo = getNextVideo(room);
                room.setCurrentVideo(nextVideo);
                playlistCmd.setVideo(nextVideo);
                executeNotifyAboutNextVideo(room, playlistCmd.getFrom());
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
                playlistCmd
                        .getPlaylist()
                        .forEach( plVideo -> {
                            plVideo.setId(UUID.randomUUID()); playlist.add(plVideo);
                        });
            break;
            default: return false;
        }

        notifyUsersAboutPlaylistCommand(room.getUserIds(), playlistCmd);
        return true;
    }

    private void executeNotifyAboutNextVideo(Room room, UUID from) {
        VideoPlayerAction videoPlayerAction = Util.createEmptyVideoPlayerAction(from);
        VideoPlayerSettings currentVideoPlayerSettings = room.getVideoPlayerSettings();
        currentVideoPlayerSettings.setTimestamp(0);
        videoPlayerAction.setVideoPlayerSettings(currentVideoPlayerSettings);
        notifyUsersAboutVideoPlayerAction(room.getUserIds(), videoPlayerAction);
    }


    public boolean executeVideoPlayerAction(Room room, VideoPlayerAction videoPlayerAction) {

        if(videoPlayerAction.getVideoPlayerSettings().getState() == Constants.REQUEST_SYNC) {

            room.addUserRequestingSync(videoPlayerAction.getFrom());
            List<UUID> withoutRequestingUserId = room.getUserIds().stream().filter(u -> !u.equals(videoPlayerAction.getFrom())).collect(Collectors.toList());
            UUID requestingResponseUserId = withoutRequestingUserId.get(0);
            notifyUserAboutVideoPlayerAction(requestingResponseUserId, videoPlayerAction);

            return true;
        }

        if(videoPlayerAction.getVideoPlayerSettings().getState() == Constants.SYNC_REQUEST_RESPONSE) {

            return true;
        }

        Video currentVideo = room.getCurrentVideo();
        room.setVideoPlayerSettings(videoPlayerAction.getVideoPlayerSettings());
        notifyUsersAboutVideoPlayerAction(room.getUserIds().stream().filter(id -> !videoPlayerAction.getFrom().equals(id)).collect(Collectors.toList()), videoPlayerAction);
        return true;
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

    public void processRespondingToJoinSyncRequest(Room room, VideoPlayerAction videoPlayerAction) {
        double timestamp = videoPlayerAction.getVideoPlayerSettings().getTimestamp();
        videoPlayerAction.getVideoPlayerSettings().setTimestamp(timestamp+1);
        notifyUsersAboutVideoPlayerAction(room.getRequestingJoinSyncs(), videoPlayerAction);
        room.clearRequestedJoinSyncs();
        System.out.println("SYNC LIST CLEARED");
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
