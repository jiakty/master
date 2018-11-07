package com.bnsf.kafkatest.chatservice;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.internals.Sender;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
//import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.bnsf.kafkatest.chatservice.beans.Message;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SpringKafkaSenderTest {
	private static String SENDER_TOPIC = "all.messages";

	  @Autowired
	  private KafkaTemplate<String, Message> messageKafkaTemplate;
	  @Autowired
	  private Sender sender;

	  private KafkaMessageListenerContainer<String, String> container;

	  private BlockingQueue<ConsumerRecord<String, String>> records;

	  @ClassRule
	  public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, SENDER_TOPIC);

	  @Before
	  public void setUp() throws Exception {
	    // set up the Kafka consumer properties
	    Map<String, Object> consumerProperties =
	        KafkaTestUtils.consumerProps("sender", "false", embeddedKafka);

	    // create a Kafka consumer factory
	    DefaultKafkaConsumerFactory<String, String> consumerFactory =
	        new DefaultKafkaConsumerFactory<String, String>(consumerProperties);

	    // set the topic that needs to be consumed
	    ContainerProperties containerProperties = new ContainerProperties(SENDER_TOPIC);

	    // create a Kafka MessageListenerContainer
	    container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

	    // create a thread safe queue to store the received message
	    records = new LinkedBlockingQueue<>();

	    // setup a Kafka message listener
	    container.setupMessageListener(new MessageListener<String, String>() {
	      @Override
	      public void onMessage(ConsumerRecord<String, String> record) {
	        records.add(record);
	      }
	    });

	    // start the container and underlying message listener
	    container.start();

	    // wait until the container has the required number of assigned partitions
	    ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
	  }

	  @After
	  public void tearDown() {
	    // stop the container
	    container.stop();
	  }

	  @Test
	  public void testSend() throws InterruptedException {
	    // send the message
	    Message m = new Message("Tom", "Cool");
	    Response r = new Response(true,"Success");
	    messageKafkaTemplate.send(SENDER_TOPIC, m);

	    // check that the message was received
	    ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);
	    // Hamcrest Matchers to check the value
	  
	    // AssertJ Condition to check the key
	    assertThat(received).has(key(null));
	  }


	
}