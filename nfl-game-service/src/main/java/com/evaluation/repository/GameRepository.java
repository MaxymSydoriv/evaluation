package com.evaluation.repository;

import com.evaluation.entity.Game;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @EntityGraph("game-with-teams")
    @Query("select g from Game g where g.id = :id")
    Optional<Game> getByIdWithPlayingTeams(Integer id);
}
