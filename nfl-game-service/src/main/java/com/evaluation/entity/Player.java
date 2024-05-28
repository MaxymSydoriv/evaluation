package com.evaluation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@NamedEntityGraph(
        name = "player-with-team",
        attributeNodes = {
                @NamedAttributeNode("team")
        }
)
@Data
@Entity
@Table(name = "PLAYER")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String surname;
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
