package com.evaluation.service;

import com.evaluation.game.BaseGameEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameEventService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${nfl.kafka.topics.event}")
    private String topic;

    public void acceptGameEvent(BaseGameEvent baseGameEvent) {
        log.info("Sending message {} to {}", baseGameEvent, topic);
        kafkaTemplate.send(topic, serializeValue(baseGameEvent));
    }

    @SneakyThrows //there is a better way, I avoided for simplicity
    private String serializeValue(BaseGameEvent baseGameEvent) {
        return objectMapper.writeValueAsString(baseGameEvent);
    }
}
