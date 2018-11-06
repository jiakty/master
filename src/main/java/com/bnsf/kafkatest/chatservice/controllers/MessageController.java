package com.bnsf.kafkatest.chatservice.controllers;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnsf.kafkatest.chatservice.beans.Message;
import com.bnsf.kafkatest.chatservice.service.MessageService;
@RestController
@RequestMapping("/MC")
public class MessageController {
	@Autowired
	private MessageService ms;
	
	@PostMapping("/send")
	public Message sendToAll( String message) throws InterruptedException, ExecutionException{
		message = "hello";
		return this.ms.sendMessage(message);
	}
}
