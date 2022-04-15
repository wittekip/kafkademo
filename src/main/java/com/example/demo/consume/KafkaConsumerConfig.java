package com.example.demo.consume;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.RecoveringBatchErrorHandler;

import com.example.demo.Greeting;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.extern.slf4j.Slf4j;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value("${kafka.schema.registry.url}")
	private String schemaUrl;

	@Autowired
	private KafkaTemplate<String, Object> template;

//
//	@Bean
//	public ConsumerFactory<String, String> consumerFactory() {
//		Map<String, Object> props = props();
//		// props.put(ConsumerConfig.RE, props)
//		// props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//		return new DefaultKafkaConsumerFactory<>(props);
//	}
//
	private Map<String, Object> props() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put("schema.registry.url", schemaUrl);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "basgroup");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
		return props;
	}

//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, String> filterKafkaListenerContainerFactory() {
//
//		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(consumerFactory());
//		// factory.setErrorHandler(new DlqErrorHandler());
//		// factory.setRecordFilterStrategy(record -> record.value().contains("1"));
//		// DeadLetterPublishingRecoverer recover = new
//		// DeadLetterPublishingRecoverer(template);
//		// factory.setCommonErrorHandler(new DefaultErrorHandler(recover, new
//		// ExponentialBackOffWithMaxRetries(3)));
//		// factory.setErrorHandler(new SeekToCurrentErrorHandler());
//		return factory;
//	}
//
//	class DlqErrorHandler implements ContainerAwareErrorHandler {
//
//		@Override
//		public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer,
//				MessageListenerContainer container) {
//			@SuppressWarnings("unchecked")
//			ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) records.get(0);
//			try {
//				template.send("dlqTopic", record.key(), record.value());
//				consumer.seek(new TopicPartition(record.topic(), record.partition()), record.offset() + 1);
//				// Other records may be from other partitions, so seek to current offset for
//				// other partitions too
//				// ...
//			} catch (Exception e) {
//				consumer.seek(new TopicPartition(record.topic(), record.partition()), record.offset());
//				// Other records may be from other partitions, so seek to current offset for
//				// other partitions too
//				// ...
//				throw new KafkaException("Seek to current after exception", thrownException);
//			}
//		}
//	}

	@Bean
	public ConsumerFactory<String, Object> avroConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(props(), new StringDeserializer(), new KafkaAvroDeserializer());
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Greeting> avroKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, Greeting> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(avroConsumerFactory());
		//factory.setBatchListener(true);
		//factory.setBatchErrorHandler(new RecoveringBatchErrorHandler());
		return factory;
	}

}
