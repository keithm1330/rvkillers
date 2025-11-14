package rv.killers.killerapp.service;

import org.springframework.stereotype.Service;
import rv.killers.killerapp.model.GameState;
import rv.killers.killerapp.model.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameService {
    private final GameState state = new GameState();
    private final AtomicInteger idCounter = new AtomicInteger();

    public synchronized GameState getState() { return state; }

    public synchronized Player addPlayer(String name, int lives) {
        String id = String.valueOf(idCounter.incrementAndGet());
        Player p = new Player(id, name, lives); // use custom lives
        state.getPlayers().add(p);
        return p;
    }


    public synchronized void clearPlayers() {
        state.getPlayers().clear();
        state.setStarted(false);
        state.setCurrentIndex(0);
    }

    public synchronized void startGame() {
        List<Player> list = new ArrayList<>(state.getPlayers());
        Collections.shuffle(list);
        state.getPlayers().clear();
        state.getPlayers().addAll(list);
        state.setStarted(true);
        state.setCurrentIndex(0);
    }


    public synchronized void loseLife(String playerId) {
        List<Player> players = state.getPlayers();

        // Find the player
        Player p = players.stream()
                .filter(pl -> pl.getId().equals(playerId))
                .findFirst()
                .orElse(null);

        if (p == null) return;

        // Lose one life
        p.loseLife();

        // Remove if lives <= 0
        if (p.getLives() <= 0) {
            int removeIndex = players.indexOf(p);
            players.remove(p);

            // Adjust currentIndex if needed
            if (removeIndex < state.getCurrentIndex()) {
                state.setCurrentIndex(state.getCurrentIndex() - 1);
            }
        }

        // Reset game if no players left
        if (players.isEmpty()) {
            state.setCurrentIndex(0);
            state.setStarted(false);
        }
    }


    public synchronized void nextTurn() {
        List<Player> players = state.getPlayers();
        if (!state.isStarted() || players.isEmpty()) return;

        int index = state.getCurrentIndex();
        index++;

        if (index >= players.size()) index = 0;

        state.setCurrentIndex(index);
    }


    public synchronized void previousTurn() {
        List<Player> players = state.getPlayers();
        if (!state.isStarted() || players.isEmpty()) return;

        int index = state.getCurrentIndex();
        index--;

        if (index < 0) index = players.size() - 1;

        state.setCurrentIndex(index);
    }



}

