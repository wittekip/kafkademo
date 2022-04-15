package com.example.demo.consume;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.demo.Address;
import com.example.demo.Greeting;

import lombok.extern.slf4j.Slf4j;

//@KafkaListener(topics = "greetingTopic", groupId = "bas", containerFactory = "greetingKafkaListenerContainerFactory")
//@Slf4j
//@Component
//public class JsonListener {
//
//	@KafkaHandler
//	public void receiveGreeting(@Payload Greeting greeting) {
//		log.info("Received a greeting: " + greeting);
//	}
//
//	@KafkaHandler
//	public void receiveAddress(@Payload Address address) {
//		log.info("Received an address: " + address);
//	}
//}
