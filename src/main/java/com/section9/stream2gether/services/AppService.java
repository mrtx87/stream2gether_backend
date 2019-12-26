package com.section9.stream2gether.services;

import com.section9.stream2gether.models.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AppService {

    private Map<UUID, Room> rooms;

    public AppService(){
        rooms = new HashMap<>();
    }

    public DataTransferContainer createRoom() {
        DataTransferContainer container = new DataTransferContainer();
        User user = createUser();
        container.setUser(user);
        container.setFrom(user.getId());
        Room room = new Room();
        room.addUser(user);
        rooms.put(room.getId(), room);
        container.setRoomId(room.getId());
        ChatMessage initialMessage = Util.createChatMessage(null, Constants.CHAT_MESSAGE_INIT_TEXT);
        container.setChatMessage(initialMessage);
        return container;
    }

    private User createUser(){
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
}
