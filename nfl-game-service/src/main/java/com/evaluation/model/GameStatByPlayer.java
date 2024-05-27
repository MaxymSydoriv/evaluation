package com.evaluation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameStatByPlayer {

    private PlayerDTO playerDTO;
    private List<GameStatEntry> statistics;
}
