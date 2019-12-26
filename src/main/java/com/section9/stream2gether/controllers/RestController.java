package com.section9.stream2gether.controllers;

import com.section9.stream2gether.models.DataTransferContainer;
import com.section9.stream2gether.models.User;
import com.section9.stream2gether.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        DataTransferContainer dtc = appService.createRoom();
        if (dtc != null) {
            return ResponseEntity.ok().body(dtc);
        }
        return ResponseEntity.badRequest().build();
    }
}
