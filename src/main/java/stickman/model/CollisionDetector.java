package stickman.model;

public interface CollisionDetector {

  Side validateCollision(Entity entity1, Entity entity2);

  enum Side {
    TOP,
    LEFT,
    RIGHT,
    BOTTOM,
    NONE
  }
}
