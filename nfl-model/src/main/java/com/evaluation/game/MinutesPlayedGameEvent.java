package com.evaluation.game;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MinutesPlayedGameEvent extends BaseGameEvent {

    @Min(0)
    @Max(48)
    private Float value;
    @Override
    public Number getValue() {
        return this.value;
    }
}
