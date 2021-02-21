package stickman.model;

import java.util.List;
import java.util.Random;
import stickman.model.Entity.Layer;

/**
 * Responsible for unpacking platform and enemy lists, and spawning the appropriate entities
 * in the level dynamically.
 */
public class LevelMaker {

  Level level;
  Factory factory;
  int numClouds = 10;

  public Level make(Hero hero, double heroStartPos, int height, int width, double cloudSpeed,
    List<Double[]> platformPositions, List<EntityContainer> enemies, double[] finishFlagPos,
    double floorHeight) {

    AbsMovingEntity enemy;

    level = new LevelImpl(hero, heroStartPos, height, width, floorHeight);
    factory = new EntityFactory(floorHeight, cloudSpeed);
    factory.setLevelWidth(width);

    level.addFinishFlag(new FinishFlag("medievalTile_148.png", 40, 40,
      finishFlagPos[0], finishFlagPos[1], Layer.FOREGROUND, 40));

    // Spawn platforms
    for (int i = 0; i < platformPositions.size(); i++) {
      level.addPlatform(factory.make("platform",
        platformPositions.get(i)[0], platformPositions.get(i)[1]));
    }

    // Spawn enemies
    for (int i = 0; i < enemies.size(); i++) {
      enemy = (AbsMovingEntity) factory.make(enemies.get(i).getType(),
        enemies.get(i).getXPos(), enemies.get(i).getYPos());

      level.addMovingEntity(enemy);
      level.addEnemy(enemy);
    }

    makeClouds(numClouds);
    return level;
  }

  /**
   * Creates the specified number of clouds at random locations within set boundaries
   */
  private void makeClouds(int numClouds) {
    Random rand = new Random();

    for (int i = 0; i < numClouds; i++) {
      level.addMovingEntity((AbsMovingEntity) factory.make("cloud",
        rand.nextInt(1700), rand.nextInt(100)));
    }
  }
}
