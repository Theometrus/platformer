
package stickman.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import stickman.model.Entity.Layer;

public class LevelImplTest extends ApplicationTest {

  String imgPath = "tree.png";
  Level level;
  Hero hero;

  @Before
  public void setUp() throws Exception {
    hero = new Hero(imgPath, 20, 20, 0, 20, Layer.FOREGROUND,
      1, 0, 0);

    level = new LevelImpl(hero, 10, 1000, 1000, 0);
  }

  @Test
  public void addEntitySimple() {
    Entity entity = new EntityImpl(imgPath, 20, 20, 4, 4,
      Layer.BACKGROUND, 0);

    level.addEntity(entity);

    assertEquals(entity, level.getEntities().get(1));
  }

  @Test
  public void isWonSimple() {
    assertFalse(level.isWon());
    level.win();
    assertTrue(level.isWon());
  }

  @Test
  public void jumpSimple() {
    level.jump();
    assertTrue(hero.isJumping());
  }

  @Test
  public void moveLeftOneStep() {
    assertEquals(10, hero.getXPos(), 0.1);
    level.moveLeft();
    level.tick();
    assertEquals(9, hero.getXPos(), 0.1);
  }

  @Test
  public void moveRightOneStep() {
    assertEquals(10, hero.getXPos(), 0.1);
    level.moveRight();
    level.tick();
    assertEquals(11, hero.getXPos(), 0.1);
  }

  @Test
  public void cleanupDeadEntities() {
    Entity entity1 = new EntityImpl(imgPath, 20, 20, 4, 4,
      Layer.BACKGROUND, 0);

    Entity entity2 = new EntityImpl(imgPath, 210, 210, 14, 14,
      Layer.BACKGROUND, 0);

    level.addEntity(entity1);
    level.addEntity(entity2);

    assertEquals(3, level.getEntities().size());
    entity1.mark();
    assertEquals(3, level.getEntities().size());
    level.cleanupDeadEntities();
    assertEquals(2, level.getEntities().size());
    entity2.mark();
    hero.mark();
    assertEquals(2, level.getEntities().size());
    level.cleanupDeadEntities();
    assertEquals(0, level.getEntities().size());
  }

  @Test
  public void tickGetHit() {
    Enemy enemy = new Enemy(imgPath, 20, 20, 100, 0, Layer.FOREGROUND,
      1, 0, 0);
    enemy.setStrategy(new NormalSlimeStrategy(enemy));

    level.addEntity(enemy);
    level.addEnemy(enemy);
    level.move(enemy);

    hero.setXPos(101);
    hero.setYPos(0);
    hero.setGroundLevel(0);

    level.tick();

    assertEquals(2, hero.getLives());
    assertEquals(10, hero.getXPos(), 0.1);
  }

  @Test
  public void tickDestroyEnemyComplex() {
    Enemy enemy = new Enemy(imgPath, 20, 20, 100, 21, Layer.FOREGROUND,
      0, 0, 0);

    level.addEntity(enemy);
    level.addEnemy(enemy);
    level.move(enemy);

    hero.setYPos(0);
    hero.setGroundLevel(10);
    hero.setXPos(100);

    assertEquals(2, level.getEntities().size());

    // Allow the hero to fall towards the ground over several ticks
    level.tick();
    level.tick();
    level.tick();
    level.tick();
    level.tick();
    level.tick();

    assertEquals(1, level.getEntities().size());
  }
}
