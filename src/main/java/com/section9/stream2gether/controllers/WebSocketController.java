package com.section9.stream2gether.controllers;


import com.section9.stream2gether.models.DataTransferContainer;
import com.section9.stream2gether.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


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

	
}
