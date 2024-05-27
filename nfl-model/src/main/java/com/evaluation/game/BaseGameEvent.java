package com.evaluation.game;

import com.evaluation.constant.EventType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "eventType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FoulGameEvent.class, name = "FOUL"),
        @JsonSubTypes.Type(value = MinutesPlayedGameEvent.class, name = "MINUTES_PLAYED"),
        @JsonSubTypes.Type(value = GameEvent.class, names = {"POINT", "REBOUND", "ASSIST", "STEAL", "BLOCK", "TURNOVER"})
})
public abstract class BaseGameEvent {

    @NonNull
    private Integer gameId;
    @NonNull
    private EventType eventType;
    @NonNull
    private Integer playerId;

    public abstract Number getValue();

}
