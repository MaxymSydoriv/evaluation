package com.evaluation.service;

import com.evaluation.entity.Game;
import com.evaluation.entity.GameStatEvent;
import com.evaluation.entity.Player;
import com.evaluation.entity.Team;
import com.evaluation.exception.RestException;
import com.evaluation.game.BaseGameEvent;
import com.evaluation.model.*;
import com.evaluation.repository.GameRepository;
import com.evaluation.repository.GameStatEventRepository;
import com.evaluation.repository.PlayerRepository;
import com.evaluation.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameStatEventService implements DtoCrudService<GameStatEvent, BaseGameEvent, Integer> {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final GameStatEventRepository gameStatEventRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public BaseGameEvent save(BaseGameEvent baseGameEvent) {
        Player player = playerRepository.findById(baseGameEvent.getPlayerId())
                .orElseThrow(() -> new RestException("Player not found", HttpStatus.NOT_FOUND));
        Game game = gameRepository.findById(baseGameEvent.getGameId())
                .orElseThrow(() -> new RestException("Game not found", HttpStatus.NOT_FOUND));

        GameStatEvent gameStatEvent = new GameStatEvent();
        gameStatEvent.setEventType(baseGameEvent.getEventType());
        gameStatEvent.setGame(game);
        gameStatEvent.setPlayer(player);
        gameStatEvent.setValue(String.valueOf(baseGameEvent.getValue()));

        gameStatEventRepository.save(gameStatEvent);

        return baseGameEvent;
    }

    public GameStatByPlayer getGameStatByPlayer(Integer seasonId, Integer playerId) {
        List<GameStatEntry> statistics = gameStatEventRepository.getStatsEventsByPlayer(seasonId, playerId);
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RestException("Player not found", HttpStatus.NOT_FOUND));

        return new GameStatByPlayer(toPlayerDTO(player), statistics);
    }

    public GameStatsByTeam getGameStatsByTeam(Integer seasonId, Integer teamId) {
        List<GameStatEntry> statistics = gameStatEventRepository.getStatsEventsByTeam(seasonId, teamId);
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RestException("Team not found", HttpStatus.NOT_FOUND));
        return new GameStatsByTeam(toTeamDTO(team), statistics);
    }

    private PlayerDTO toPlayerDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        playerDTO.setBirthDate(player.getBirthDate());
        return playerDTO;
    }

    private TeamDTO toTeamDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        return teamDTO;
    }

}
