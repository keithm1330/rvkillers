package rv.killers.killerapp.model;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final List<Player> players = new ArrayList<>();
    private int currentIndex = 0;
    private boolean started = false;


    public List<Player> getPlayers() { return players; }
    public int getCurrentIndex() { return currentIndex; }
    public void setCurrentIndex(int i) {
        this.currentIndex = i;
    }

    public void next() {
        if (players.isEmpty()) return;
        currentIndex = (currentIndex + 1) % players.size();
    }
    public boolean isStarted() { return started; }
    public void setStarted(boolean s) { this.started = s; }
}

