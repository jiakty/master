package com.bnsf.kafkatest.chatservice.beans;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	private String username;
	private String message;
	private String time;
	
	@JsonCreator
    public Message(@JsonProperty("username") String username,
                    @JsonProperty("message") String message) {
        this.username = username;
        this.message = message;
    }
	public String getUser() {
		return username;
	}
	public void setUser(String user) {
		this.username = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(yourmilliseconds);
		
		return sdf.format(resultdate);
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Message [username=" + username + ", message=" + message + ", time=" + time + "]";
	}
	
}
