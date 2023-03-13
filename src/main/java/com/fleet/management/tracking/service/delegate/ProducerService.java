package com.fleet.management.tracking.service.delegate;

import com.fleet.management.tracking.model.TrackingDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

    @Value(value = "${fleet.topic.tracking-topic}")
    private String topic;

    @NotNull
    private final ReactiveKafkaProducerTemplate<String, TrackingDetails> kafkaProducerTemplate;

    public void send(TrackingDetails trackingDetails) {
        log.info("send to topic={}, {}={},", topic, TrackingDetails.class.getSimpleName(), trackingDetails);
        kafkaProducerTemplate.send(topic, trackingDetails)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", trackingDetails, senderResult.recordMetadata().offset()))
                .subscribe();
    }
}
