package the.dungeons.engine;

import the.dungeons.GameMap;
import the.dungeons.GameMove;
import the.dungeons.GameRoom;

public class Engine {
    private GameMap gameMap;
    private GameRoom room;
    private boolean gameOver;

    public Engine(GameMap gameMap) {
        this.gameMap = gameMap;
        room = gameMap.startingRoom;
    }

    public String command(String cmd) {
        switch (cmd) {
            case "show": return describeRoom();
            case "w": return move(GameMove.West);
            case "s": return move(GameMove.South);
            case "e": return move(GameMove.East);
            case "n": return move(GameMove.North);

            default:
                return "I cannot understand:"+cmd;
        }
    }

    private String move(GameMove direction) {
        var nextRoom = room.moves.get(direction).get();
        if (nextRoom == null) {
            return "You cannot move there";
        }
        else {
            room = nextRoom;
            return describeRoom();
        }
    }

    private String describeRoom() {
        return room.getIntro()+"\n"+extraActions()+room.showMoves();
    }

    private String extraActions() {
        String actions = "";
        if (room.flags.chest) {
            if (gameMap.hasKey) {
                actions += "You see an opened chest\n";
            }
            else {
                actions += "(open) You see a closed chest\n";
            }
        }
        if (room.flags.wizard) {
            if (gameMap.hasDeliveredTrophy) {
                actions += "You see a wizard; he is carrying a trophy\n";
            }
            else if (gameMap.hasSlayedMonster) {
                actions += "(give trophy) You see a wizard; he is expecting a gift\n";
            }
            else {
                actions += "You see a wizard; he tells you that there is a monster nearby; he is expecting you to slay it\n";
            }
        }
        if (room.flags.monster) {
            if (gameMap.hasSlayedMonster) {
                if (gameMap.hasDeliveredTrophy) {
                    actions += "you see a slayed monster, you already delivered a trophy\n";
                }
                else {
                    actions += "you see a slayed monster, you carry a trophy from him, you should find wizard and hand it to him as a gift\n";
                }
            }
            else {
                actions += "(slay) You see a monster; you should slay it!";
            }
        }
        if (room.flags.end) {
            if (gameMap.hasDeliveredTrophy) {
                actions += "(end) here is where your journey ends; you have slayed a monster and pleased wizard; you are a true hero";
            }
            else {
                actions += "here is where your journey ends, but you still need to slay monster and hand trophy to a wizard, good luck";
            }
        }

        return actions;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
