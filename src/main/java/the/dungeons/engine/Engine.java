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
            case "open": return open();
            case "give trophy": return giveTrophy();
            case "slay": return slay();
            case "end": return end();

            default:
                return "I cannot understand:"+cmd;
        }
    }

    private String open() {
        if (room.flags.chest) {
            if (gameMap.hasKey) {
                return "you already have opened a chest";
            }
            else {
                gameMap.hasKey = true;
                return "you open a chest and pick up a key";
            }
        }
        else {
            return "there is nothing to open here";
        }
    }

    private String giveTrophy() {
        if (room.flags.wizard) {
            if (gameMap.hasDeliveredTrophy) {
                return "you already delivered trophy to wizard";
            }
            else if (gameMap.hasSlayedMonster) {
                gameMap.hasDeliveredTrophy = true;
                return "You deliver trophy to wizard; he tells you that you can now seek for an exit from dungeon";
            }
            else {
                return "You have no trophy to give; seek for a monster and slay him";
            }
        }
        else {
            return "there is no wizard here";
        }
    }

    private String slay() {
        if (room.flags.monster) {
            if (gameMap.hasSlayedMonster) {
                return "You already slayed monster";
            }
            else {
                gameMap.hasSlayedMonster = true;
                return "You slayed monster and obtain a trophy from it; seek wizard to give him a trophy";
            }
        }
        else {
            return "There is no monster to slay here";
        }
    }

    private String end() {
        if (room.flags.end) {
            if (gameMap.hasDeliveredTrophy) {
                gameOver = true;
                return "Your journey ends here. Dungeons are free from monster now; you feel good to see sun again";
            }
            else {
                return "The door is shut; you must please Wizard by handing him a trophy, to have it opened";
            }
        }
        else {
            return "There is no exit here";
        }
    }

    private String move(GameMove direction) {
        var nextRoom = room.moves.get(direction).get();
        if (nextRoom == null) {
            return "You cannot move there";
        }
        else {
            if (nextRoom.flags.door && !gameMap.hasKey) {
                return "You face a closed door, you cannot get there; seek for a key in the chest";
            }
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
                    actions += "You see a slayed monster, you already delivered a trophy\n";
                }
                else {
                    actions += "You see a slayed monster, you carry a trophy from him, you should find wizard and hand it to him as a gift\n";
                }
            }
            else {
                actions += "(slay) You see a monster; you should slay it!\n";
            }
        }
        if (room.flags.end) {
            if (gameMap.hasDeliveredTrophy) {
                actions += "(end) Here is where your journey ends; you have slayed a monster and pleased wizard; you are a true hero";
            }
            else {
                actions += "Here is where your journey ends, but you still need to slay monster and hand trophy to a wizard, good luck";
            }
        }

        return actions;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
