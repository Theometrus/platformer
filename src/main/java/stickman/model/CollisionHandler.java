package stickman.model;

import stickman.model.CollisionDetector.Side;

public interface CollisionHandler {

  void handle(Entity entity1, Entity entity2, Side collisionSide, Level context);
}
