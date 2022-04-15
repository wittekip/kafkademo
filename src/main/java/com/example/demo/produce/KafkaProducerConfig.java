package com.example.demo.produce;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

@Configuration
public class KafkaProducerConfig {

	@Value("${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value("${kafka.schema.registry.url}")
	private String schemaUrl;

//	@Bean
//	public ProducerFactory<String, String> producerFactory() {
//		Map<String, Object> configProps = new HashMap<>();
//		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//		return new DefaultKafkaProducerFactory<>(configProps);
//	}
//
//	@Bean
//	public KafkaTemplate<String, String> kafkaTemplate() {
//		return new KafkaTemplate<>(producerFactory());
//	}

//	@Bean
//	public ProducerFactory<String, Object> greetingProducerFactory() {
//		Map<String, Object> configProps = new HashMap<>();
//		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//		return new DefaultKafkaProducerFactory<>(configProps);
//	}
//
//	@Bean
//	public KafkaTemplate<String, Object> greetingKafkaTemplate() {
//		return new KafkaTemplate<>(greetingProducerFactory());
//	}
//	
	@Bean
	public ProducerFactory<String, Object> avroProducerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put("schema.registry.url", schemaUrl);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, Object> avroKafkaTemplate() {
		return new KafkaTemplate<>(avroProducerFactory());
	}
}
