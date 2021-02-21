package stickman.model;

import stickman.model.CollisionDetector.Side;

/**
 * Responsible for delegating what happens to two entities that have collided, depending on their
 * respective types as well as the side of the collision. Currently only collisions involving
 * the hero are handled.
 */
public class CollisionHandlerImpl implements CollisionHandler {

  @Override
  public void handle(Entity entity1, Entity entity2, Side collisionSide, Level context) {

    if (entity1 == null || entity2 == null) {
      return;
    }

    try {
      if (entity1 instanceof Hero && entity2 instanceof Damageable) {
        switch (collisionSide) {
          // The hero jumps on top of the enemy, destroying the enemy and jumping up like in Mario
          case TOP:
            ((Hero) entity1).forceJump();
            ((Damageable) entity2).getDamaged();
            break;
            // All other collision sides result in a loss of life and position reset
          case RIGHT:
          case BOTTOM:
          case LEFT:
            ((Hero) entity1).resetPos();
            ((Hero) entity1).getDamaged();
            break;
        }
      } else if (entity1 instanceof Hero && entity2 instanceof Platform) {
        switch (collisionSide) {
          case TOP:
            ((Hero) entity1).setPlatform(entity2);
            ((Hero) entity1).setGroundLevel(entity2.getYPos() - entity2.getHeight());
            ((Hero) entity1).setYPos(entity2.getYPos() - entity2.getHeight());
            break;
          case LEFT:
            ((Hero) entity1).setXPos(entity2.getXPos() - entity1.getWidth());
            break;
          case RIGHT:
            ((Hero) entity1).setXPos(entity2.getXPos() + entity2.getWidth());
            break;
          case BOTTOM:
            ((Hero) entity1).setYPos(entity2.getYPos() + entity1.getHeight());
        }
      } else if (entity1 instanceof Hero && entity2 instanceof FinishFlag) {
        context.win();
      }
    } catch (NullPointerException e) {

      return;
    }
  }
}
