package com.section9.stream2gether.services;

import com.section9.stream2gether.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppService {

     // Spring provided template for data exchange via websocket
    @Autowired
    SimpMessagingTemplate messagingService;

    private Map<UUID, Room> rooms;

    public AppService() {
        rooms = new HashMap<>();
    }

    public DataTransferContainer createRoom() {
        DataTransferContainer container = new DataTransferContainer();
        container.setPurpose(DTC_PURPOSE.ROOMCREATED);
        User user = createUser();
        container.setUser(user);
        container.setFrom(user.getId());

        Room room = new Room();
        rooms.put(room.getId(), room);
        room.addUser(user);
        container.setUsers(room.getUsersAsListOfUUID());
        container.setRoomId(room.getId());

        ChatMessage initialMessage = Util.createChatMessage(null, Constants.CHAT_MESSAGE_INIT_TEXT);
        container.setChatMessage(initialMessage);

        return container;
    }

    public DataTransferContainer joinRoom(UUID roomId) {
        if(!rooms.containsKey(roomId)){
            return null;
        }
        DataTransferContainer container = new DataTransferContainer();
        container.setPurpose(DTC_PURPOSE.ROOMJOINED);
        User user = createUser();
        container.setUser(user);
        container.setFrom(user.getId());

        Room room = rooms.get(roomId);
        room.addUser(user);
        container.setRoomId(room.getId());
        container.setUsers(room.getUsersAsListOfUUID());

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

    public void processJoinRoomNotification(DataTransferContainer dtc) {
        List<User> allButJoined = dtc.getUsers().stream()
                .filter(users -> !users.getId().equals(dtc.getFrom()))
                .collect(Collectors.toList());
        notifyClients(allButJoined,dtc);
    }

    private void notifyClients(List<User> users, DataTransferContainer dtc) {
        users.forEach(user -> notifyClient(user.getId(), dtc));
    }

    private void notifyClient(UUID userId, DataTransferContainer dtc) {
        this.messagingService.convertAndSend("/client/" + userId, dtc);
    }
}
