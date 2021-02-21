package stickman.model;

public class CollisionDetectorImpl implements CollisionDetector {

  /**
   * First checks whether a collision between two entities has occurred, then returns the side
   *
   * @return the side from which entity1 collided with entity2
   */
  @Override
  public Side validateCollision(Entity entity1, Entity entity2) {

    if (entity1 == null || entity2 == null) {
      return Side.NONE;
    } else if (entity1 == entity2) {
      return Side.NONE;
    }

    // General collision/intersection
    if (!(entity1.getXPos() + entity1.getWidth() < entity2.getXPos() ||
      entity2.getXPos() + entity2.getWidth() < entity1.getXPos() ||
      entity1.getYPos() < entity2.getYPos() - entity2.getHeight() ||
      entity2.getYPos() < entity1.getYPos() - entity1.getHeight())) {

      // Followed by specific cases to try and determine which side a collision came from.
      if (entity1.getYPos() - entity1.getHeight() < entity2.getYPos() - entity2.getHeight() &&
        entity1.getXPos() + entity1.getWidth() / 2 >= entity2.getXPos() &&
        entity1.getXPos() + entity1.getWidth() / 2 <= entity2.getXPos() + entity2.getWidth()) {
        return Side.TOP;

      } else if (entity1.getYPos() - entity1.getHeight() <= entity2.getYPos() &&
        entity1.getXPos() + entity1.getWidth() / 2 >= entity2.getXPos() &&
        entity1.getXPos() + entity1.getWidth() / 2 <= entity2.getXPos() + entity2.getWidth()) {
        return Side.BOTTOM;

      } else if (entity1.getXPos() >= entity2.getXPos() && entity1.getYPos() >= entity2.getYPos()) {
        return Side.RIGHT;

      } else if (entity1.getXPos() <= entity2.getXPos() && entity1.getYPos() >= entity2.getYPos()) {
        return Side.LEFT;
      }
    }
    return Side.NONE;
  }
}
