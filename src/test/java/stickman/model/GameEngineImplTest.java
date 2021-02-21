package stickman.model;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class GameEngineImplTest extends ApplicationTest {

  GameEngine engine;
  Level level;

  @Before
  @Test
  public void startLevelSimple() throws URISyntaxException {
    engine = new GameEngineImpl("/testConfig.json");
    engine.startLevel(20, 1, 300);
    level = engine.getCurrentLevel();

    assertTrue(level.getEntities().get(0) instanceof Hero);
    assertTrue(level.getEntities().get(1) instanceof FinishFlag);
    assertTrue(level.getEntities().get(2) instanceof Platform);
    assertTrue(level.getEntities().get(3) instanceof Platform);
    assertTrue(level.getEntities().get(4) instanceof Enemy);
    assertTrue(level.getEntities().get(5) instanceof Enemy);
    assertTrue(level.getEntities().get(6) instanceof Enemy);
    assertTrue(level.getEntities().get(7) instanceof Cloud);
    assertTrue(level.getEntities().get(8) instanceof Cloud);
    assertTrue(level.getEntities().get(9) instanceof Cloud);

    assertEquals(20, level.getEntities().get(0).getXPos(), 0.1);
    assertEquals(300, level.getEntities().get(0).getYPos(), 0.1);
    assertEquals(38, level.getEntities().get(0).getSpriteOffset(), 0.1);
    assertEquals(40, level.getEntities().get(0).getHeight(), 0.1);
    assertEquals(20, level.getEntities().get(0).getWidth(), 0.1);
  }

  @Test
  public void jumpSimple() {
    assertEquals(300, level.getHero().getYPos(), 0.1);
    level.jump();
    level.tick();
    assertEquals(297, level.getHero().getYPos(), 0.1);
  }

  @Test
  public void jumpFall() {
    assertEquals(300, level.getHero().getYPos(), 0.1);
    level.jump();
    level.tick();
    level.tick();
    assertEquals(294, level.getHero().getYPos(), 0.1);
    for (int i = 0; i < 100; i++) {
      level.tick();
    }
    assertEquals(300, level.getHero().getYPos(), 0.1);
  }

  @Test
  public void moveBackForth() {
    assertEquals(20, level.getHero().getXPos(), 0.1);
    level.moveLeft();
    for (int i = 0; i < 6; i++) {
      level.tick();
    }

    level.stopMoving();
    assertEquals(10, level.getHero().getXPos(), 0.5);
    level.moveRight();

    for (int i = 0; i < 7; i++) {
      level.tick();
    }
    level.stopMoving();
    assertEquals(22, level.getHero().getXPos(), 0.5);
  }

  @Test
  public void noRandomWin() {
    assertFalse(level.isWon());
    assertFalse(engine.isWon());

    for (int i = 0; i < 50; i++) {
      engine.tick();
    }

    level.jump();
    level.moveRight();

    for (int i = 0; i < 50; i++) {
      level.tick();
    }

    level.stopMoving();

    assertFalse(level.isWon());
    assertFalse(engine.isWon());
  }
}