package com.example.demo.consume;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.demo.domain.BookEntity;
import com.example.demo.domain.BookEntityRepository;
import com.example.demo.domain.BookId;

import example.avro.Book;
import example.avro.Envelope;
import example.avro.Movie;
import example.avro.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "avroTopic2", groupId = "bas", containerFactory = "avroKafkaListenerContainerFactory")
public class AvroListener {

	private final BookEntityRepository bookRepo;

	private Random random = new Random();

	private List<BookEntity> bookList = new ArrayList<>();

	@KafkaHandler
	public void receiveGreeting(@Payload User record) throws IOException {
		log.info("Received a user: " + record.getName() + ", " + record.getFavoriteNumber());
	}

	@KafkaHandler
	public void receiveGreeting(@Payload Movie record) throws IOException {
		log.info("Received a movie: " + record.getName() + ", " + record.getDirector());
	}

	@KafkaHandler
	public void receiveEnvelope(@Payload Envelope record, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition)
			throws IOException {
		// log.info("Received a batch message: " + list.size());
		//log.info("Received a payload: " + record.getPayload() + " on partition " + partition);
		if (record.getPayload() instanceof Book) {
			Book book = (Book) record.getPayload();

			BookId id = new BookId();
			id.setOriginalId(book.getSomeId());
			id.setReceived(ZonedDateTime.now());

			BookEntity be = new BookEntity();
			// be.setOriginalId(book.getSomeId());
			be.setAuthor(book.getAuthor().toString());
			be.setName(book.getName().toString());
			if (book.getIsbn() != null) {
				be.setIsbn(book.getIsbn().toString());
			}
			be.setId(id);

//				if (random.nextInt(1000) == 123) {
//					log.info("Warning");
//					throw new RuntimeException();
//				}

//				bookList.add(be);
//				if (bookList.size() % 1000 == 0) {
//					bookRepo.saveAll(bookList);
//					bookList.clear();
//				}
			bookRepo.save(be);

//			ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//			JsonEncoder encoder = EncoderFactory.get().jsonEncoder(Book.getClassSchema(), stream);
//			DatumWriter<Book> writer = new SpecificDatumWriter<>(Book.class);
//			writer.write(book, encoder);
//			encoder.flush();
//
//			FileUtils.writeByteArrayToFile(new File("c:\\data\\avro\\book_" + book.getSomeId() + ".txt"),
//					stream.toByteArray());
		}
	}
}
