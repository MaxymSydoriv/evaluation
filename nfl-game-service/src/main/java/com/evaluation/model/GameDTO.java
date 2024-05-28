package com.evaluation.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class GameDTO {

    private Integer id;
    private LocalDateTime startDatetime;
    private TeamDTO teamA;
    private TeamDTO teamB;
}
