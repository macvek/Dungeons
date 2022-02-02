package the.dungeons;

import java.util.EnumMap;
import java.util.function.Supplier;

public class GameRoom {
    private final EnumMap<GameMove, Supplier<GameRoom>> moves;

    public GameRoom(EnumMap<GameMove, Supplier<GameRoom>> moves, GameRoomFlags flags) {
        this.moves = moves;
    }
}
