package the.dungeons;

public class GameMap {
    public final GameRoom startingRoom;

    private boolean hasKey;
    private boolean hasSlayedMonster;
    private boolean hasDeliveredTrophy;

    public GameMap(GameRoom startingRoom) {
        this.startingRoom = startingRoom;
    }

}
