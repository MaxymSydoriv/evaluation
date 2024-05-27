package com.evaluation.game;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FoulGameEvent extends BaseGameEvent {

    @Min(value = 0, message = "Minimal value is 0")
    @Max(value = 6, message = "Maximal value is 6")
    private Integer value;

    @Override
    public Number getValue() {
        return this.value;
    }
}
