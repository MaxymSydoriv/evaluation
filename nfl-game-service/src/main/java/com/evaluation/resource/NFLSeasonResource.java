package com.evaluation.resource;

import com.evaluation.model.SeasonDTO;
import com.evaluation.service.NFLSeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nfl/game-service/season")
public class NFLSeasonResource {

    @Autowired
    private NFLSeasonService seasonService;

    @GetMapping("/{id}")
    public SeasonDTO getById(@PathVariable Integer id) {
        return seasonService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeasonDTO save(@RequestBody SeasonDTO seasonDTO) {
        return seasonService.save(seasonDTO);
    }

}
