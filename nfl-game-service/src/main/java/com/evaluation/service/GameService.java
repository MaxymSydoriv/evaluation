package com.evaluation.service;

import com.evaluation.entity.Game;
import com.evaluation.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game getGameById(Integer id) {
        return gameRepository.findById(id).orElseThrow();
    }
}
