package com.evaluation.service;

import com.evaluation.entity.Game;
import com.evaluation.entity.GameStatEvent;
import com.evaluation.entity.Player;
import com.evaluation.entity.Team;
import com.evaluation.game.BaseGameEvent;
import com.evaluation.model.*;
import com.evaluation.repository.GameStatEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameStatEventService implements DtoCrudService<GameStatEvent, BaseGameEvent, Integer> {
    private final PlayerService playerService;
    private final GameService gameService;
    private final GameStatEventRepository gameStatEventRepository;
    private final TeamService teamService;

    @Transactional
    public GameStatEvent save(BaseGameEvent baseGameEvent) {
        Player player = playerService.getPlayerById(baseGameEvent.getPlayerId());
        Game game = gameService.getGameById(baseGameEvent.getGameId());

        GameStatEvent gameStatEvent = new GameStatEvent();
        gameStatEvent.setEventType(baseGameEvent.getEventType());
        gameStatEvent.setGame(game);
        gameStatEvent.setPlayer(player);
        gameStatEvent.setValue(String.valueOf(baseGameEvent.getValue()));

        return gameStatEventRepository.save(gameStatEvent);
    }

    public GameStatByPlayer getGameStatByPlayer(Integer seasonId, Integer playerId) {
        List<GameStatEntry> statistics = gameStatEventRepository.getStatsEventsByPlayer(seasonId, playerId);
        Player player = playerService.getPlayerById(playerId);

        return new GameStatByPlayer(toPlayerDTO(player), statistics);
    }

    public GameStatsByTeam getGameStatsByTeam(Integer seasonId, Integer teamId) {
        List<GameStatEntry> statistics = gameStatEventRepository.getStatsEventsByTeam(seasonId, teamId);
        Team team = teamService.getById(teamId);
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
