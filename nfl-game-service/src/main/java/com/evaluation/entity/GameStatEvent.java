package com.evaluation.entity;

import com.evaluation.constant.EventType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "game_event")
public class GameStatEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String value;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @PrePersist
    void prePersist() {
        if (Objects.isNull(createdAt)) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
