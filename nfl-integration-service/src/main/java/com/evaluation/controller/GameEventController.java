package com.evaluation.controller;

import com.evaluation.game.BaseGameEvent;
import com.evaluation.service.GameEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/nfl/integration/game/event")
public class GameEventController {

    private final GameEventService gameEventService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void acceptGameEvent(@Valid @RequestBody BaseGameEvent gameEvent) {
        this.gameEventService.acceptGameEvent(gameEvent);
    }
}
