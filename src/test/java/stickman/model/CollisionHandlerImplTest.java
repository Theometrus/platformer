package stickman.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import stickman.model.CollisionDetector.Side;
import stickman.model.Entity.Layer;
import org.testfx.framework.junit.ApplicationTest;

public class CollisionHandlerImplTest extends ApplicationTest {

  Hero hero;
  Enemy enemy;
  Platform platform;
  String imgPath = "tree.png";
  CollisionHandler handler = new CollisionHandlerImpl();
  Level level;

  @Before
  public void setUp() throws Exception {

    hero = new Hero(imgPath, 20, 20, 0, 20, Layer.FOREGROUND,
      0, 0, 0);

    level = new LevelImpl(hero, 20, 1000, 1000, 300);

    hero.setXPos(100);

    enemy = new Enemy(imgPath, 20, 20, 100, 20, Layer.FOREGROUND,
      0, 0, 0);

    level.addEnemy(enemy);

    platform = new Platform(imgPath, 20, 20, 20, 60, Layer.FOREGROUND, 0);
  }

  @Test
  public void handleEnemyAndPlatformCollisions() {
    assertEquals(100, hero.getXPos(), 0.1);
    handler.handle(hero, enemy, Side.RIGHT, null);
    assertEquals(2, hero.getLives());
    assertEquals(20, hero.getXPos(), 0.1);
    assertEquals(1, level.getEnemies().size());

    hero.setXPos(100);

    handler.handle(hero, enemy, Side.TOP, null);
    level.cleanupDeadEntities();
    assertEquals(0, level.getEnemies().size());
    assertEquals(2, hero.getLives());
    assertEquals(100, hero.getXPos(), 0.1);

    handler.handle(hero, platform, Side.TOP, null);
    assertEquals(40, hero.getYPos(), 0.1);

    handler.handle(hero, platform, Side.LEFT, null);
    assertEquals(0, hero.getXPos(), 0.1);
  }
}