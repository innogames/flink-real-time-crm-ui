package com.innogames.analytics.rtcrmui.kafka;

import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.Future;

@Component
public class StringProducer {

	private final Properties properties;
	private Producer<String, String> producer;

	public StringProducer(@Value("${spring.kafka.bootstrap-servers}") final String kafkaBootstrapServers) {
		this.properties = new Properties();

		this.properties.put("bootstrap.servers", kafkaBootstrapServers);
		this.properties.put("acks", "all");
		this.properties.put("buffer.memory", 33554432);
		this.properties.put("compression.type", "snappy");
		this.properties.put("retries", 3);
		this.properties.put("batch.size", 16384);
		this.properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		this.properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		this.producer = new KafkaProducer<>(this.properties);
	}

	@PreDestroy
	public void destroy() {
		this.flush();
		this.producer.close();
	}

	public void flush() {
		this.producer.flush();
	}

	public Future<RecordMetadata> send(ProducerRecord<String, String> record) {
		return this.producer.send(record);
	}

	public Future<RecordMetadata> send(String topic, String message) {
		return this.send(new ProducerRecord<>(topic, message));
	}

}
