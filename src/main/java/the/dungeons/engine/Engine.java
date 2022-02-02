package the.dungeons.engine;

import the.dungeons.GameMap;

public class Engine {
    private GameMap gameMap;
    private boolean gameOver;

    public Engine(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public String command(String cmd) {
        return "Good morning";

    }

    public boolean isGameOver() {
        return gameOver;
    }
}
