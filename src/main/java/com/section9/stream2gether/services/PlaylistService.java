package com.section9.stream2gether.services;

import com.section9.stream2gether.models.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AppService {

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


    public boolean addVideoToPlaylist(UUID roomId, UUID userId, PlaylistCommand plCommand) {
        if(authenticate(roomId, userId)) {


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
}
