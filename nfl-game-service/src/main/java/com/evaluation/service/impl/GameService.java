package com.evaluation.service.impl;

import com.evaluation.entity.Game;
import com.evaluation.entity.Team;
import com.evaluation.exception.RestException;
import com.evaluation.model.GameDTO;
import com.evaluation.model.TeamDTO;
import com.evaluation.repository.GameRepository;
import com.evaluation.repository.TeamRepository;
import com.evaluation.service.DtoCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameService implements DtoCrudService<Game, GameDTO, Integer> {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    @Override
    public GameDTO getById(Integer id) {
        return gameRepository.getByIdWithPlayingTeams(id)
                .map(this::toGameDto).orElseThrow(() -> new RestException("Game not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public GameDTO save(GameDTO gameDTO) {
        Game game = new Game();
        game.setStartDatetime(gameDTO.getStartDatetime());
        game.setTeamA(getTeam(gameDTO.getTeamA().getId()));
        game.setTeamB(getTeam(gameDTO.getTeamB().getId()));
        gameRepository.save(game);
        gameDTO.setId(game.getId());
        return gameDTO;
    }

    private Team getTeam(Integer id) {
        return teamRepository.findById(id).orElseThrow(() -> new RestException("Team not found", HttpStatus.NOT_FOUND));
    }

    private GameDTO toGameDto(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setStartDatetime(game.getStartDatetime());
        gameDTO.setTeamA(toTeamDTO(game.getTeamA()));
        gameDTO.setTeamB(toTeamDTO(game.getTeamB()));
        return gameDTO;
    }


    private TeamDTO toTeamDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName(team.getName());
        teamDTO.setId(team.getId());
        return teamDTO;
    }
}
