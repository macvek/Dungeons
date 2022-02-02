package the.dungeons;

import the.dungeons.engine.Engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game {
    public static void main(String[] args) throws Exception {
        String mapView =
                "================\n" +
                "==S===C=========\n" +
                "==           ===\n" +
                "============= ==\n" +
                "==    D       ==\n" +
                "== ========== ==\n" +
                "==W =========M==\n" +
                "===E============\n";

        final GameMap mapFromString = GameMapFactory.fromString(mapView);

        var engine = new Engine(mapFromString);
        String response = engine.command("show");
        var reader = new BufferedReader(new InputStreamReader(System.in));
        for(;;) {
            System.out.println(response);
            if (engine.isGameOver()) {
                break;
            }
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            response = engine.command(line);
        }

    }
}
