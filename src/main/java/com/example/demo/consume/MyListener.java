//package com.example.demo.consume;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//import com.example.demo.Greeting;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class MyListener {
//
////	@KafkaListener(topics = "topic1", groupId = "basgroup")
////	public void listenGroupFoo(String message) {
////		log.info("Received Message in group basgroup: {}", message);
////	}
////
////	@KafkaListener(topics = "topic1", groupId = "basgroup2")
////	public void listenGroupFoo2(String message) {
////		log.info("Received Message in group basgroup2: {}", message);
////	}
//
//	@KafkaListener(topics = "topic2", groupId = "basgroup3", containerFactory = "filterKafkaListenerContainerFactory")
//	public void listenGroupFoo3(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//		log.info("Received Message in group basgroup3: {} on partition {}", message, partition);
//		if (message.contains("1")) {
//			log.info("Exception!");
//			throw new RuntimeException("Well that is nice but it's wrong: " + message);
//		}
//
//	}
//
//	@KafkaListener(topics = "dlqTopic", groupId = "basgroup2")
//	public void dlqListener(String message) {
//		log.info("Received Message on DLQ topic: {}", message);
//	}
//
////	@KafkaListener(topics = "greetingTopic", groupId = "bas", containerFactory = "greetingKafkaListenerContainerFactory")
////	public void listenGroupFoo2(Object message) {
////		log.info("Received Message in group basgroup2: {}", message);
////	}
//
//}
