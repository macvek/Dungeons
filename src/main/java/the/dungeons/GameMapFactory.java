package the.dungeons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class GameMapFactory {
    public static GameMap fromString(String mapView) {
        final List<String> rows = Arrays.asList(mapView.split("\n"));
        int width = rows.get(0).length();
        int height = rows.size();

        final GameRoom[][] allRooms = new GameRoom[height][width];

        GameRoom starter = null;

        for (int y=0;y<rows.size();y++) {
            String row = rows.get(y);
            var tiles = row.toCharArray();
            for (int x=0;x<tiles.length;x++) {
                var tile = tiles[x];

                GameRoomFlags flags = new GameRoomFlags();
                boolean wall = false;
                boolean starterFlag = false;
                switch(tile) {
                    case 'W': flags.wizard = true; break;
                    case 'S': starterFlag = true; break;
                    case 'C': flags.chest = true; break;
                    case 'D': flags.door = true; break;
                    case 'M': flags.monster = true; break;
                    case 'E': flags.end = true; break;
                    case ' ': break;
                    default:
                        wall = true;
                }

                if (!wall) {
                    var gameRoom = new GameRoom(pickIntro(), movesFrom(x, y, allRooms), flags);
                    allRooms[y][x] = gameRoom;
                    if (starterFlag) {
                        starter = gameRoom;
                    }
                }
            }
        }

        GameMap gameMap = new GameMap(starter);
        return gameMap;
    }

    private static EnumMap<GameMove, Supplier<GameRoom>> movesFrom(int x, int y, GameRoom[][] allRooms) {
        var map = new EnumMap<GameMove, Supplier<GameRoom>>(GameMove.class);
        map.put(GameMove.North, lookup(x, y-1, allRooms));
        map.put(GameMove.South, lookup(x, y+1, allRooms));
        map.put(GameMove.East, lookup(x+1, y, allRooms));
        map.put(GameMove.West, lookup(x-1, y, allRooms));

        return map;
    }

    private static Supplier<GameRoom> lookup(int x, int y, GameRoom[][] allRooms) {
        return () -> {
            if (y < 0 || y >= allRooms.length) {
                return null;
            }
            if (x < 0 || x >= allRooms[y].length) {
                return null;
            }

            return allRooms[y][x];
        };
    }

    private static String pickIntro() {
        String[] intros = new String[]{
                "You see a dark room, it is scary here",
                "Wind is blowing through hallway.. smell of magic is in the air",
                "Water is dropping somewhere in the shadow, a small rat is eating his dinner",
                "A little candle is hanging on the wall. What a beautiful room it is",
                "A table and a chair is in the middle of the room",
                "You see a tunnel",
                "This room reminds you of your own room"
        };

        return intros[(int)(Math.random()*intros.length)];
    }
}
