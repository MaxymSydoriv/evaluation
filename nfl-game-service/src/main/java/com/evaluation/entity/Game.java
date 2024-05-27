package com.evaluation.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "GAME")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime startDatetime;
    @ManyToOne
    @JoinColumn(name = "team_a_id")
    private Team teamA;
    @ManyToOne
    @JoinColumn(name = "team_b_id")
    private Team teamB;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;

}
