package com.bnsf.kafkatest.chatservice.service;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.bnsf.kafkatest.chatservice.beans.Message;
import com.bnsf.kafkatest.chatservice.beans.User;

@Service
public class MessageService {

    @Autowired
    private KafkaTemplate<String, Message> messageKafkaTemplate;

    //@Autowired
   // private KafkaProducerProperties kafkaProducerProperties;

    public Message sendMessage(String  message) throws InterruptedException, ExecutionException{
    	Message m = new Message();
    	m.setMessage(message);	
    	User u = new User();
    	u.setUsername("Tom");
    	m.setUser(u);
    	SendResult<String, Message> sendResult = messageKafkaTemplate.sendDefault(m).get();
        //RecordMetadata recordMetadata = sendResult.getRecordMetadata();
		return m;
    }
}
