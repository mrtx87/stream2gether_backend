package com.section9.stream2gether.controllers;


import com.section9.stream2gether.models.DataTransferContainer;
import com.section9.stream2gether.models.VideoPlayerAction;
import com.section9.stream2gether.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.UUID;


@Controller
@CrossOrigin(origins = { "http://localhost:4200","https://localhost:4200" })
public class WebSocketController {


	AppService appService;

	@Autowired
	public WebSocketController(AppService appService) {
		this.appService = appService;
	}

	@MessageMapping("/ws/create-room")
	public void onReceiveCreateRoom(@Nullable final DataTransferContainer transferContainer) {

	}

	@MessageMapping("/ws/disconnect-client")
	public void onReceiveDisconnectClient(@Nullable final DataTransferContainer transferContainer) {
		appService.processDisconnectClient(transferContainer);
	}

	@MessageMapping("/ws/roomId/{roomId}/requesting-sync")
	public void onReceiveRequestingSync(@DestinationVariable UUID roomId, @Nullable final VideoPlayerAction videoPlayerAction) {
		appService.processSyncRequest(roomId, videoPlayerAction);
	}

	@MessageMapping("/ws/roomId/{roomId}/responding-to-join-sync-request")
	public void onReceiveRespondingToJoinSyncRequest(@DestinationVariable UUID roomId, @Nullable final VideoPlayerAction videoPlayerAction) {
		appService.processRespondingToJoinSyncRequest(roomId, videoPlayerAction);
	}

	@MessageMapping("/ws/roomId/{roomId}/chatmessage")
	public void onReceiveChatmessage(@DestinationVariable UUID roomId, @Nullable final DataTransferContainer dtc) {
		appService.processChatmessage(roomId, dtc);
	}


	
}
