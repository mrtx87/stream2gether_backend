package com.section9.stream2gether.services;

import com.section9.stream2gether.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppService {

    @Autowired
    PlaylistService playlistService;

    @Autowired
    SimpMessagingTemplate messagingService;

    private Map<UUID, Room> rooms;

    public AppService() {
        rooms = new HashMap<>();

    }

    public DataTransferContainer createRoom() {
        DataTransferContainer container = new DataTransferContainer();
        User user = createUser();
        container.setUser(user);
        container.setFrom(user.getId());

        Room room = new Room();
        rooms.put(room.getId(), room);
        room.addUser(user);
        container.setRoomId(room.getId());

        ChatMessage initialMessage = Util.createChatMessage(null, Constants.CHAT_MESSAGE_INIT_TEXT);
        container.setChatMessage(initialMessage);

        return container;
    }

    public DataTransferContainer joinRoom(UUID roomId, DataTransferContainer requestDTC) {
        if(!rooms.containsKey(roomId)){
            return null;
        }
        DataTransferContainer container = new DataTransferContainer();
        container.setPurpose(Constants.PURPOSE_USER_JOINED);
        User user = createUser();
        if(requestDTC.getUser().getName() != null) {
            user.setName(requestDTC.getUser().getName());
        }
        container.setUser(user);
        container.setFrom(user.getId());

        Room room = rooms.get(roomId);
        room.addUser(user);
        container.setRoomId(room.getId());

        ChatMessage joinMessage = Util.createChatMessage(null, Util.getRandomJoinMessage(user.getName()));
        container.setChatMessage(joinMessage);

        if(container != null) {
            processJoinRoomNotification(room.getUserIds(), container);
        }

        return container;
    }

    private User createUser() {
        String name = Util.getRandomName();
        String avatar = Util.getRandomAvatar();
        return new User(name, avatar);
    }

    public Map<UUID, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<UUID, Room> rooms) {
        this.rooms = rooms;
    }


    public boolean processPlaylistCommand(UUID roomId, UUID userId, PlaylistCommand playlistCmd) {
        if(authenticate(roomId, userId)) {
            Room room = getRoom(roomId);
            return playlistService.executePlaylistCommand(room, playlistCmd);
        }
        return false;
    }

    private Room getRoom(UUID roomId) {
        return this.rooms.get(roomId);
    }

    private boolean authenticate(UUID roomId, UUID userId) {
        if(hasRoom(roomId)) {
            Room room = rooms.get(roomId);
            return hasUser(room, userId);
        }
        return false;
    }

    private boolean hasRoom(UUID roomId) {
        return this.rooms.containsKey(roomId);
    }

    private boolean hasUser(Room room, UUID userId) {
        return room.hasUser(userId);
    }

    public void processJoinRoomNotification(List<UUID> userIds, DataTransferContainer dtc) {
        List<UUID> allButJoined = userIds.stream()
                .filter(userId -> !userId.equals(dtc.getFrom()))
                .collect(Collectors.toList());
        notifyUsers(allButJoined, dtc);
    }


    private void notifyUser(UUID userId, DataTransferContainer dtc) {
        this.messagingService.convertAndSend("/client/" + userId, dtc);
    }

    private void notifyUsers(List<UUID> userIds, DataTransferContainer dtc) {
        userIds.stream().forEach(userId -> notifyUser(userId, dtc));
    }

    public void processDisconnectClient(DataTransferContainer transferContainer) {
        if(authenticate(transferContainer.getRoomId(), transferContainer.getFrom())) {
            Room room = getRoom(transferContainer.getRoomId());
            User removedUser = room.removeUser(transferContainer.getFrom());
            if(Util.isNotPresent(removedUser)) {
                return;
            }

            transferContainer.setPurpose(Constants.PURPOSE_DISCONNECTED_CLIENT);
            notifyUsers(room.getUserIds(), transferContainer);
        }
    }

    public List<Video> getPlaylist(UUID roomId, UUID userId) {
        if(authenticate(roomId, userId)) {
            return getRoom(roomId).getPlaylist();
        }
        return null;
    }

    public PlaylistCommand getInitPlaylistCommand(UUID roomId, UUID userId) {
        if(authenticate(roomId, userId)) {
            //return getRoom(roomId).getPlaylist();
        }
        return null;
    }

    public List<User> getUsers(UUID roomId, UUID userId) {
        if(authenticate(roomId, userId)) {
            return getRoom(roomId).getUserList();
        }
        return null;
    }

    public VideoPlayerSettings getVideoPlayerSettings(UUID roomId, UUID userId) {
        if(authenticate(roomId, userId)) {
            Room room = getRoom(roomId);
            return room.getVideoPlayerSettings();
        }
        return null;
    }

    public PlaylistState getPlaylistState(UUID roomId, UUID userId) {
        if(authenticate(roomId, userId)) {
            Room room = getRoom(roomId);
            return room.getPlaylistState();
        }
        return null;
    }

    public void processSyncRequest(UUID roomId, VideoPlayerAction videoPlayerAction) {
        if(authenticate(roomId, videoPlayerAction.getFrom())) {
            Room room = getRoom(roomId);
            this.playlistService.executeVideoPlayerAction(room, videoPlayerAction);
        }
    }

    public void processChatmessage(UUID roomId, DataTransferContainer dtc) {
        if(authenticate(roomId , dtc.getFrom())) {
            Room room = getRoom(roomId);
            List<UUID> userIds = room.getUserIds();
            ChatMessage chatMessage = dtc.getChatMessage();
            chatMessage.setId(UUID.randomUUID());
            chatMessage.setCreatedAt(Instant.now());
            dtc.setPurpose(Constants.PURPOSE_SEND_CHATMESSAGE);
            notifyUsers(userIds,dtc);

        }
    }

    public void processRespondingToJoinSyncRequest(UUID roomId, VideoPlayerAction videoPlayerAction) {
       if(authenticate(roomId, videoPlayerAction.getFrom())) {
            Room room = getRoom(roomId);
           playlistService.processRespondingToJoinSyncRequest(room, videoPlayerAction);
       }
    }
}
