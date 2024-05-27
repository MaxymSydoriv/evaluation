package com.evaluation.resource;

import com.evaluation.model.GameStatByPlayer;
import com.evaluation.model.GameStatsByTeam;
import com.evaluation.service.GameStatEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nfl/game-service/stat")
public class NFLStatResource {

    @Autowired
    private GameStatEventService gameStatEventService;

    @GetMapping("/season/{seasonId}/player/{playerId}")
    public GameStatByPlayer getStatsByPlayer(@PathVariable("seasonId") Integer seasonId, @PathVariable("playerId") Integer playerId) {
        return gameStatEventService.getGameStatByPlayer(seasonId, playerId);
    }

    @GetMapping("/season/{seasonId}/team/{playerId}")
    public GameStatsByTeam getStatsByTeam(@PathVariable("seasonId") Integer seasonId, @PathVariable("playerId") Integer teamId) {
        return gameStatEventService.getGameStatsByTeam(seasonId, teamId);
    }


}
