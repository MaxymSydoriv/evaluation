package com.evaluation.service;

import com.evaluation.entity.Player;
import com.evaluation.entity.Team;
import com.evaluation.exception.RestException;
import com.evaluation.model.PlayerDTO;
import com.evaluation.repository.PlayerRepository;
import com.evaluation.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService implements DtoCrudService<Player, PlayerDTO, Integer> {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public PlayerDTO save(PlayerDTO playerDTO) {
        Team team = teamRepository.findById(playerDTO.getTeamId()).orElseThrow(() -> new RestException("Team not found", HttpStatus.NOT_FOUND));
        Player player = new Player();
        player.setBirthDate(playerDTO.getBirthDate());
        player.setName(playerDTO.getName());
        player.setSurname(playerDTO.getSurname());
        player.setTeam(team);
        playerRepository.save(player);

        return playerDTO;
    }

    @Override
    public PlayerDTO getById(Integer id) {
        return playerRepository.getByIdWithTeam(id)
                .map(this::toPlayerDTO).orElseThrow(() -> new RestException("Player not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Integer id) {
        playerRepository.deleteById(id);
    }

    private PlayerDTO toPlayerDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        playerDTO.setBirthDate(player.getBirthDate());
        playerDTO.setTeamId(player.getTeam().getId());
        return playerDTO;
    }

}