package com.section9.stream2gether.services;

import com.section9.stream2gether.models.DataTransferContainer;
import com.section9.stream2gether.models.Room;
import com.section9.stream2gether.models.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppService {

    private Map<UUID, Room> rooms;

    public AppService(){
        rooms = new HashMap<>();
    }

    public Optional<DataTransferContainer> createRoom() {
        User user = createUser();
        Room room = new Room();
        room.addUser(user);
        rooms.put(room.getId(), room);
        DataTransferContainer container = new DataTransferContainer();
        return Optional.of();
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
