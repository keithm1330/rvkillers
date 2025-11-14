package rv.killers.killerapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rv.killers.killerapp.model.GameState;
import rv.killers.killerapp.model.Player;
import rv.killers.killerapp.service.GameService;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public GameState state() { return service.getState(); }

    @PostMapping("/add")
    public Player add(@RequestParam String name,
                      @RequestParam(required = false, defaultValue = "3") int lives) {
        return service.addPlayer(name, lives);
    }
    @PostMapping("/clear")
    public ResponseEntity<?> clear() {
        service.clearPlayers();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/start")
    public ResponseEntity<?> start() {
        service.startGame();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/player/{id}/lose")
    public ResponseEntity<?> loseLife(@PathVariable String id) {
        service.loseLife(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/next")
    public ResponseEntity<?> next() {
        service.nextTurn();
        return ResponseEntity.ok().build();
    }

    // 🔥 NEW ENDPOINT: Go to previous player
    @PostMapping("/prev")
    public ResponseEntity<?> prev() {
        service.previousTurn();
        return ResponseEntity.ok().build();
    }
}
