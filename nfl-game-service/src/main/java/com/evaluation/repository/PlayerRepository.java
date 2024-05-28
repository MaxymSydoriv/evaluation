package com.evaluation.repository;

import com.evaluation.entity.Player;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @EntityGraph("player-with-team")
    @Query("select p from Player p where p.id = :id")
    Optional<Player> getByIdWithTeam(Integer id);
}
