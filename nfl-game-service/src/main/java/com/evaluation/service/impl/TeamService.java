package com.evaluation.service.impl;

import com.evaluation.entity.Team;
import com.evaluation.exception.RestException;
import com.evaluation.model.TeamDTO;
import com.evaluation.repository.TeamRepository;
import com.evaluation.service.DtoCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService implements DtoCrudService<Team, TeamDTO, Integer> {
    private final TeamRepository teamRepository;

    @Override
    public TeamDTO getById(Integer id) {
        return teamRepository.findById(id).map(this::toTeamDTO)
                .orElseThrow(() -> new RestException("Team not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public TeamDTO save(TeamDTO teamDTO) {
        Team team = new Team();
        team.setName(team.getName());
        teamRepository.save(team);
        teamDTO.setId(team.getId());
        return teamDTO;
    }

    private TeamDTO toTeamDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName(team.getName());
        teamDTO.setId(team.getId());
        return teamDTO;
    }
}
