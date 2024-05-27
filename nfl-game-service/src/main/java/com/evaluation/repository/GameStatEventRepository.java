package com.evaluation.repository;

import com.evaluation.entity.GameStatEvent;
import com.evaluation.model.GameStatEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStatEventRepository extends JpaRepository<GameStatEvent, Integer> {

    @Query(nativeQuery = true, value = "select event_type as eventType, sum(CAST(value AS integer)) as value from game_event " +
            "left join game on game.id = game_event.game_id" +
            " WHERE season_id = :seasonId AND (game.team_a_id = :teamId OR game.team_b_id = :teamId) AND event_type != 'MINUTES_PLAYED' " +
            "GROUP BY event_type;")
    List<GameStatEntry> getStatsEventsByTeam(Integer seasonId, Integer teamId);

    @Query(nativeQuery = true, value = "select event_type as eventType, sum(CAST(value AS integer)) as value from game_event " +
            "join game on game.id = game_event.game_id " +
            "WHERE season_id = :seasonId AND player_id = :playerId AND event_type != 'MINUTES_PLAYED' " +
            "GROUP BY event_type")
    List<GameStatEntry> getStatsEventsByPlayer(Integer seasonId, Integer playerId);
}
