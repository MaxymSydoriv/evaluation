package com.evaluation.service;

import com.evaluation.entity.Player;
import com.evaluation.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Player getPlayerById(Integer id) {
        return playerRepository.findById(id).orElseThrow();
    }

    //more methods here
}
