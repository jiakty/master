package com.bnsf.kafkatest.chatservice;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bnsf.kafkatest.chatservice.beans.Message;
import com.bnsf.kafkatest.chatservice.controllers.MessageController;
import com.bnsf.kafkatest.chatservice.service.MessageService;
@RunWith(SpringJUnit4ClassRunner.class)
public class MVCtest {
	 private MockMvc mockMvc;

	    @Mock
	    private MessageService messageService;

	    @InjectMocks
	    private MessageController messageController;

	    @Before
	    public void init(){
	        //MockitoAnnotations.initMocks(this);
	        mockMvc = MockMvcBuilders
	                .standaloneSetup(messageController)
	                .build();
	    }

	    @Test
	    public void test() throws Exception {
	        //Message m = new Message("Tom", "Cool");
	        Response r = new Response(true,"success");

	       //Mockito.when(messageService.sendMessage(m)).thenReturn(r);
	        mockMvc.perform(MockMvcRequestBuilders.post("/MC/send?username=Tom&message=Cool"))
	                .andExpect(MockMvcResultMatchers.status().isOk());
	                
	 
	    }
}
