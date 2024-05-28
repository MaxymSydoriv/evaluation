package com.evaluation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@NamedEntityGraph(
        name = "game-with-teams",
        attributeNodes = {
                @NamedAttributeNode("teamA"),
                @NamedAttributeNode("teamB")
        }
)
@Data
@Entity
@Table(name = "GAME")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime startDatetime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_a_id")
    private Team teamA;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_b_id")
    private Team teamB;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;

}
