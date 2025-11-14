package rv.killers.killerapp.model;

public class Player {
    private final String id;
    private final String name;
    private int lives;

    public Player(String id, String name, int lives) {
        this.id = id;
        this.name = name;
        this.lives = lives;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
    public void loseLife() { if (lives>0) lives--; }
}

