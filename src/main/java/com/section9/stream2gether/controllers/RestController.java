package com.section9.stream2gether.controllers;

import com.section9.stream2gether.models.*;
import com.section9.stream2gether.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = {"http://localhost", "http://localhost:4200", "https://localhost:4200"})
@RequestMapping("backend/rest")
public class RestController {


    @Autowired
    AppService appService;

	/*@PostMapping(path = { "/data/register" })
	public ResponseEntity<UserDTO> registerUser(@RequestBody Credentials credentials) {
		Optional<UserDTO> user = chatService.registerUser(credentials);
		if (user.isPresent()) {
			return ResponseEntity.ok().body(user.get());
		} else {
			return ResponseEntity.badRequest().build();
		}
		return null;
	}

	@GetMapping(path = { "/data/userId/{id}/rooms" })
	public ResponseEntity<List<ChatRoomDTO>> getRoomsByUserId(@PathVariable("id") UUID id) {
		Optional<List<ChatRoomDTO>> chatRooms = chatService.getRoomsByUserId(id);
		if (chatRooms.isPresent()) {
			return ResponseEntity.ok().body(chatRooms.get());
		} else {
			return ResponseEntity.ok().body(new ArrayList<ChatRoomDTO>());
		}
		return null;

	}*/

    @GetMapping(path = {"/create-room"})
    public ResponseEntity<DataTransferContainer> requestCreateRoom() {
        DataTransferContainer responseDTC = appService.createRoom();
        if (responseDTC != null) {
            return ResponseEntity.ok().body(responseDTC);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = {"/roomId/{roomId}/userId/{userId}/userlist"})
    public ResponseEntity<List<User>> requestUserList(@PathVariable("roomId") UUID roomId, @PathVariable("userId") UUID userId) {
        List<User> users = appService.getUsers(roomId, userId);
        if (users != null) {
            return ResponseEntity.ok().body(users);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = {"/roomId/{roomId}/userId/{userId}/playlist"})
    public ResponseEntity<List<Video>> requestPlaylist(@PathVariable("roomId") UUID roomId, @PathVariable("userId") UUID userId) {
        List<Video> playlist = appService.getPlaylist(roomId, userId);
        if (playlist != null) {
            return ResponseEntity.ok().body(playlist);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = {"/roomId/{roomId}/userId/{userId}/player-settings"})
    public ResponseEntity<VideoPlayerSettings> requestVideoPlayerSettings(@PathVariable("roomId") UUID roomId, @PathVariable("userId") UUID userId) {
        VideoPlayerSettings videoPlayerSettings = appService.getVideoPlayerSettings(roomId, userId);
        if (videoPlayerSettings != null) {
            return ResponseEntity.ok().body(videoPlayerSettings);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = {"/roomId/{roomId}/userId/{userId}/playlist-state"})
    public ResponseEntity<PlaylistState> requestPlaylistState(@PathVariable("roomId") UUID roomId, @PathVariable("userId") UUID userId) {
        PlaylistState playlistState = appService.getPlaylistState(roomId, userId);
        if (playlistState != null) {
            return ResponseEntity.ok().body(playlistState);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = {"/join-room/{roomId}"})
    public ResponseEntity<DataTransferContainer> requestJoinRoom(@PathVariable("roomId") UUID roomId, @RequestBody DataTransferContainer requestDTC) {
        DataTransferContainer responseDTC = appService.joinRoom(roomId, requestDTC);
        if (responseDTC != null) {
            return ResponseEntity.ok().body(responseDTC);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = {"/roomId/{roomId}/userId/{userId}/playlist-command"})
    public ResponseEntity<UUID> requestProcessPlaylistCommand(@PathVariable("roomId") UUID roomId, @PathVariable("userId") UUID userId, @RequestBody PlaylistCommand plCommand) {

        boolean success = appService.processPlaylistCommand(roomId, userId, plCommand);
        if (success) {
            return ResponseEntity.ok().body(userId);
        }
        return ResponseEntity.badRequest().build();
    }

}
