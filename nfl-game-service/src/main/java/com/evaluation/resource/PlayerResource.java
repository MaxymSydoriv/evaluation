package com.evaluation.resource;

import com.evaluation.entity.Player;
import com.evaluation.model.PlayerDTO;
import com.evaluation.service.DtoCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nfl/game-service/player")
public class PlayerResource {

    @Autowired
    private DtoCrudService<Player, PlayerDTO, Integer> playerService;

    @GetMapping("/{id}")
    public PlayerDTO getById(@PathVariable Integer id) {
        return playerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDTO save(@RequestBody PlayerDTO playerDTO) {
        return playerService.save(playerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        playerService.delete(id);
    }
}
