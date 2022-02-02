package the.dungeons;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GameRoom {
    public final EnumMap<GameMove, Supplier<GameRoom>> moves;
    public final GameRoomFlags flags;
    private String intro;
    public String getIntro() {
        return intro;
    }


    public GameRoom(String intro, EnumMap<GameMove, Supplier<GameRoom>> moves, GameRoomFlags flags) {
        this.intro = intro;
        this.moves = moves;
        this.flags = flags;
    }

    public String showMoves() {
        ArrayList<String> possibleMoves = new ArrayList<>();
        if (moves.get(GameMove.North).get() != null) possibleMoves.add("n");
        if (moves.get(GameMove.South).get() != null) possibleMoves.add("s");
        if (moves.get(GameMove.East).get() != null) possibleMoves.add("e");
        if (moves.get(GameMove.West).get() != null) possibleMoves.add("w");

        return "("+possibleMoves.stream().collect(Collectors.joining(","))+") to move";
    }
}
