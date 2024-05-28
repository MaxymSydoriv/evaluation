package com.evaluation.resource;

import com.evaluation.entity.Game;
import com.evaluation.model.GameDTO;
import com.evaluation.service.DtoCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nfl/game-service/game")
public class GameResource {

    @Autowired
    private DtoCrudService<Game, GameDTO, Integer> gameService;

    @GetMapping("/{id}")
    public GameDTO getById(@PathVariable Integer id) {
        return gameService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO save(@RequestBody GameDTO gameDTO) {
        return gameService.save(gameDTO);
    }
}
