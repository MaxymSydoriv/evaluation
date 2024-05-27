package com.evaluation.game;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GameEvent extends BaseGameEvent {

    @Min(value = 0, message = "Value must be positive")
    private Integer value;
    @Override
    public Number getValue() {
        return this.value;
    }
}
