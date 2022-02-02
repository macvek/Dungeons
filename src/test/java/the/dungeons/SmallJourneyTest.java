package the.dungeons;

import org.junit.Assert;
import org.junit.Test;
import the.dungeons.engine.Engine;


public class SmallJourneyTest {
    @Test
    public void greenPathOfTheMonsterSlayer() {
        String shortestPath = "SCDMWE";

        final GameMap mapFromString = GameMapFactory.fromString(shortestPath);
        var engine = new Engine(mapFromString);
        var first = engine.command("show");
        Assert.assertTrue(first.contains("(e)"));
        var snd = engine.command("e");
        Assert.assertTrue(snd.contains("(open)"));
        engine.command("open");
        engine.command("e");
        var shouldSeeSlay = engine.command("e");
        Assert.assertTrue(shouldSeeSlay.contains("(slay)"));
        engine.command("slay");

        var shouldSeeGiveTrophy = engine.command("e");
        Assert.assertTrue(shouldSeeGiveTrophy.contains("(give trophy)"));
        engine.command("give trophy");
        var shouldSeeEnd = engine.command("e");

        Assert.assertTrue(shouldSeeEnd.contains("(end)"));
        engine.command("end");
        Assert.assertTrue(engine.isGameOver());
    }
}
