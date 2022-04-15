package com.example.demo.produce;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import example.avro.Book;
import example.avro.Envelope;
import example.avro.Movie;
import example.avro.User;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduleProducer {

//	@Autowired
//	private KafkaTemplate<String, String> template;
//
//	@Autowired
//	private KafkaTemplate<String, Object> greetingTemplate;
//	
	@Autowired
	private KafkaTemplate<String, Object> avroKafkaTemplate;

	int x = 1;

	private Random random = new Random();

	@Scheduled(fixedDelay = 10000L)
	public void sendMessage() {

//		String msg = "This is a test " + (++x);
//		log.info("Sending message {}", msg);
//		// template.send("topic2", msg);
//
//		if (x % 2 == 0) {
//			greetingTemplate.send("greetingTopic", new Greeting(msg, "Pete"));
//		}
//		else {
//			greetingTemplate.send("greetingTopic", new Address("Street Road", "Amsterdam"));
//		}
		long start = System.currentTimeMillis();
		for (int i = 0; i < 500000; i++) {

			Envelope envelope;

			if (x % 3 == 0) {
				User user = User.newBuilder().setName("User " + x).setFavoriteColor("Green").setFavoriteNumber(x)
						.build();
				envelope = Envelope.newBuilder().setPayload(user).build();
			} else if (x % 3 == 1) {
				Movie movie = Movie.newBuilder().setName("Movie " + x).setDirector("Quentin Tarantino").build();
				envelope = Envelope.newBuilder().setPayload(movie).build();

			} else {
				Book book = Book.newBuilder().setName("Book " + x).setAuthor("JRR Tolkien")
						.setIsbn(UUID.randomUUID().toString()).setSomeId(x).build();
				envelope = Envelope.newBuilder().setPayload(book).build();
			}
			avroKafkaTemplate.send("avroTopic2", UUID.randomUUID().toString(), envelope);
			x++;
		}
		long end = System.currentTimeMillis();
		log.info("Posted messages in: " + (end - start) + " ms");

	}
}
