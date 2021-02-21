package stickman.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import stickman.model.Entity.Layer;

public class LevelMakerTest extends ApplicationTest {

  LevelMaker maker = new LevelMaker();
  Hero hero;
  String imgPath = "tree.png";

  @Test
  public void makeSimple() {
    hero = new Hero(imgPath, 20, 20, 0, 20, Layer.FOREGROUND,
      1, 0, 0);

    List<Double[]> platformPositions = new ArrayList<>();
    platformPositions.add(new Double[] {100.0, 200.0});
    platformPositions.add(new Double[] {200.0, 250.0});
    platformPositions.add(new Double[] {210.0, 100.0});

    List<EntityContainer> enemies = new ArrayList<>();
    enemies.add(new EntityContainer("erraticEnemySlime", 100.0, 100.0));
    enemies.add(new EntityContainer("erraticEnemySlime", 500.0, 0.0));
    enemies.add(new EntityContainer("erraticEnemySlime", 205.0, 10.0));

    double[] finishFlagPos = {20, 30};

    Level actual = maker.make(hero, 20, 1000, 1000, 1,
    platformPositions, enemies, finishFlagPos, 200);

    assertEquals(100.0, actual.getEntities().get(5).getXPos(), 0.1);
    assertEquals(100.0, actual.getEntities().get(5).getYPos(), 0.1);
    assertEquals(205.0, actual.getEntities().get(7).getXPos(), 0.1);
    assertEquals(10.0, actual.getEntities().get(7).getYPos(), 0.1);

    assertTrue(actual.getEntities().get(1) instanceof FinishFlag);
    assertTrue(actual.getEntities().get(2) instanceof Platform);
    assertTrue(actual.getEntities().get(3) instanceof Platform);
    assertTrue(actual.getEntities().get(4) instanceof Platform);
    assertTrue(actual.getEntities().get(5) instanceof Enemy);
    assertTrue(actual.getEntities().get(6) instanceof Enemy);
    assertTrue(actual.getEntities().get(7) instanceof Enemy);
    assertTrue(actual.getEntities().get(8) instanceof Cloud);
    assertTrue(actual.getEntities().get(9) instanceof Cloud);
    assertTrue(actual.getEntities().get(10) instanceof Cloud);

    assertEquals(20, actual.getEntities().get(1).getXPos(), 0.1);
    assertEquals(30, actual.getEntities().get(1).getYPos(), 0.1);

    assertEquals(100.0, actual.getEntities().get(2).getXPos(), 0.1);
    assertEquals(200.0, actual.getEntities().get(2).getYPos(), 0.1);
    assertEquals(200.0, actual.getEntities().get(3).getXPos(), 0.1);
    assertEquals(250.0, actual.getEntities().get(3).getYPos(), 0.1);
    assertEquals(210.0, actual.getEntities().get(4).getXPos(), 0.1);
    assertEquals(100.0, actual.getEntities().get(4).getYPos(), 0.1);
  }
}