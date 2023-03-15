package com.fleet.management.tracking.config;

import com.fleet.management.tracking.model.TrackingDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Bean
    public ReceiverOptions<String, TrackingDetails> kafkaReceiverOptions(@Value(value = "${fleet.tracker.consumer.topic.tracking-topic}") String topic, KafkaProperties kafkaProperties) {
        ReceiverOptions<String, TrackingDetails> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions
                .addAssignListener(assignments -> log.info("Assigned: " + assignments))
                .commitBatchSize(1)
                .subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, TrackingDetails> reactiveKafkaConsumerTemplate(ReceiverOptions<String, TrackingDetails> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, TrackingDetails>(kafkaReceiverOptions);
    }
}
