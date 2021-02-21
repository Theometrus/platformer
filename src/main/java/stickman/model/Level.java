package stickman.model;

import java.util.List;

public interface Level {

  void addEntity(Entity entity);

  void addMovingEntity(AbsMovingEntity entity);

  List<Entity> getEntities();

  List<AbsMovingEntity> getEnemies();

  boolean isWon();

  void win();

  Hero getHero();

  void addPlatform(Entity platform);

  void addEnemy(AbsMovingEntity enemy);

  void addFinishFlag(Entity finishFlag);

  void cleanupDeadEntities();

  List<AbsMovingEntity> getCurrentlyMovingEntities();

  double getHeight();

  double getWidth();

  void tick();

  double getFloorHeight();

  double getHeroX();

  boolean jump();

  boolean moveLeft();

  boolean moveRight();

  boolean stopMoving();

  void move(AbsMovingEntity entity);
}
