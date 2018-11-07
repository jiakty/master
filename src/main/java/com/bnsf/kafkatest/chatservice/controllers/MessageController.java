package com.bnsf.kafkatest.chatservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnsf.kafkatest.chatservice.Response;
import com.bnsf.kafkatest.chatservice.beans.Message;
import com.bnsf.kafkatest.chatservice.service.MessageService;
@RestController
@RequestMapping("/MC")
public class MessageController {
	@Autowired
	private MessageService ms;
	
	 @PostMapping("/send")
	public Response sendToAll(Message message) {
		System.out.println(message);
		return ms.sendMessage(message);
	}
}
