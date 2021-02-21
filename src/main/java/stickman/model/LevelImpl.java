package stickman.model;

import java.util.ArrayList;
import java.util.List;
import stickman.model.CollisionDetector.Side;

public class LevelImpl implements Level {

  private List<Entity> entities = new ArrayList<>();
  private List<AbsMovingEntity> enemies = new ArrayList<>();
  private List<Entity> platforms = new ArrayList<>();
  private List<AbsMovingEntity> currentlyMovingEntities;
  private double height;
  private double width;
  private Hero hero;
  private double floorHeight = 300;
  private CollisionHandler handler;
  private CollisionDetector detector;
  private boolean won = false;
  private Entity finishFlag;

  public LevelImpl(Hero hero, double heroStartingXPos, double height, double width, double floorHeight) {

    this.floorHeight = floorHeight;
    this.height = height;
    this.width = width;
    currentlyMovingEntities = new ArrayList<>();
    handler = new CollisionHandlerImpl();
    detector = new CollisionDetectorImpl();

    // Setup everything related to the hero
    this.hero = hero;
    hero.setXPos(heroStartingXPos);
    hero.setStartingXPos(heroStartingXPos);
    hero.setGroundLevel(floorHeight);
    hero.setFloorHeight(floorHeight);
    hero.setYPos(floorHeight);
    entities.add(hero);
  }

  @Override
  public void addEntity(Entity entity) {
    entities.add(entity);
  }

  @Override
  public void addMovingEntity(AbsMovingEntity entity) {
    entities.add(entity);
    move(entity);
  }

  @Override
  public void addFinishFlag(Entity finishFlag) {
    entities.add(finishFlag);
    this.finishFlag = finishFlag;
  }

  @Override
  public boolean isWon() {
    return won;
  }

  @Override
  public void win() {
    won = true;
  }

  @Override
  public List<Entity> getEntities() {
    return entities;
  }

  @Override
  public List<AbsMovingEntity> getCurrentlyMovingEntities() {
    return currentlyMovingEntities;
  }

  @Override
  public List<AbsMovingEntity> getEnemies() {
    return enemies;
  }

  @Override
  public double getHeight() {
    return height;
  }

  @Override
  public double getWidth() {
    return width;
  }

  @Override
  public void tick() {
    hero.move();

    // Both jump and fall include checks and only work when conditions are met, so there shouldn't
    // be a scenario in which the hero is glitching out falling and jumping at the same time.
    hero.jump();
    hero.fall();

    // Collision checking and handling. Platforms and enemies split into different lists to
    // minimize looping and sniffing.
    for (Entity platform : platforms) {
      Side side = detector.validateCollision(hero, platform);

      if (side != Side.NONE) {
        handler.handle(hero, platform, side, this);
      }
    }

    for (Entity enemy : enemies) {
      Side side = detector.validateCollision(hero, enemy);

      if (side != Side.NONE) {
        handler.handle(hero, enemy, side, this);
      }
    }

    if (finishFlag != null) {
      Side side = detector.validateCollision(hero, finishFlag);
      if (side != Side.NONE) {
        handler.handle(hero, finishFlag, side, this);
      }
    }

    for (AbsMovingEntity entity : currentlyMovingEntities) {
      entity.move();
    }

    // Dead entity removal is left until the end to prevent modifying a list while traversing it
    // above. Necessary for safety reasons and to avoid a ConcurrentModificationException.
    cleanupDeadEntities();
  }

  @Override
  public double getFloorHeight() {
    return floorHeight;
  }

  @Override
  public double getHeroX() {
    return hero.getXPos();
  }

  @Override
  public boolean jump() {
    return hero.startJumping();
  }

  @Override
  public void move(AbsMovingEntity entity) {
    currentlyMovingEntities.add(entity);
  }

  @Override
  public boolean moveLeft() {
    hero.startMovingLeft();
    return true;
  }

  @Override
  public boolean moveRight() {
    hero.startMovingRight();
    return true;
  }

  @Override
  public boolean stopMoving() {
    hero.stopMoving();
    return true;
  }

  @Override
  public Hero getHero() {
    return hero;
  }

  @Override
  public void addPlatform(Entity platform) {
    entities.add(platform);
    platforms.add(platform);
  }

  /*
   * Have to also manually add the enemy to entities and currently moving entities lists afterwards.
   * This is to add support for entities who are already in the entity list when they suddenly turn
   * hostile (such as a friendly entity becoming aggroed after damaging it).
   */
  @Override
  public void addEnemy(AbsMovingEntity enemy) {
    enemies.add(enemy);
  }

  @Override
  public void cleanupDeadEntities() {
    enemies.removeIf(AbsMovingEntity::isMarked);
    entities.removeIf(Entity::isMarked);
    platforms.removeIf(Entity::isMarked);
    currentlyMovingEntities.removeIf(AbsMovingEntity::isMarked);
  }
}
