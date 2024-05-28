package com.evaluation.resource;

import com.evaluation.entity.Team;
import com.evaluation.model.TeamDTO;
import com.evaluation.service.DtoCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nfl/game-service/team")
public class TeamResource {

    @Autowired
    private DtoCrudService<Team, TeamDTO, Integer> teamService;

    @GetMapping("/{id}")
    public TeamDTO getById(@PathVariable Integer id) {
        return teamService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO save(@RequestBody TeamDTO teamDTO) {
        return teamService.save(teamDTO);
    }
}
