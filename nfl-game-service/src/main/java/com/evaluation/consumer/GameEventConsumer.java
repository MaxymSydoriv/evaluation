package com.evaluation.consumer;

import com.evaluation.entity.GameStatEvent;
import com.evaluation.game.BaseGameEvent;
import com.evaluation.service.DtoCrudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameEventConsumer {

    private final DtoCrudService<GameStatEvent, BaseGameEvent, Integer> gameStatEventService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${nfl.kafka.topics.event}", groupId = "game-service")
    public void consumeGameEvent(Message<String> baseGameEventMessage) {
        try {
            gameStatEventService.save(deserialize(baseGameEventMessage.getPayload()));
        } catch (JsonProcessingException e) {
            log.error("Received error consuming message {}", baseGameEventMessage.getPayload());
        }

    }
    private BaseGameEvent deserialize(String string) throws JsonProcessingException {
        return objectMapper.readValue(string, BaseGameEvent.class);
    }

}
