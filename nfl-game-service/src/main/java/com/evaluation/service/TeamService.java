package com.evaluation.service;

import com.evaluation.entity.Team;
import com.evaluation.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public Team getById(Integer id) {
        return teamRepository.findById(id).orElseThrow();
    }
}
