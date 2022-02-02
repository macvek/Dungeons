package the.dungeons;

public class GameMap {
    public final GameRoom startingRoom;

    public boolean hasKey;
    public boolean hasSlayedMonster;
    public boolean hasDeliveredTrophy;

    public GameMap(GameRoom startingRoom) {
        this.startingRoom = startingRoom;
    }

}
