package stickman.model;

import static org.junit.Assert.*;

import org.junit.Test;
import stickman.model.CollisionDetector.Side;
import stickman.model.Entity.Layer;

public class CollisionDetectorImplTest {

  CollisionDetector detector = new CollisionDetectorImpl();
  String imgPath = "tree.png";

  @Test
  public void validateCollisionsSimple() {
    Entity entity1 = new EntityImpl(imgPath, 20, 20, 0, 50,
      Layer.BACKGROUND, 0);

    Entity entity2 = new EntityImpl(imgPath, 200, 200, 0, 250,
      Layer.BACKGROUND, 3);

    AbsMovingEntity entity3 = new Enemy(imgPath, 20, 30, 40, 40,
      Layer.BACKGROUND, 1, 0, 0);

    assertNotEquals(Side.NONE, detector.validateCollision(entity1, entity2));
    assertEquals(Side.NONE, detector.validateCollision(entity1, entity3));
    assertEquals(Side.NONE, detector.validateCollision(entity2, entity3));

    entity3.setXPos(0);

    assertNotEquals(Side.NONE, detector.validateCollision(entity1, entity3));
    assertEquals(Side.NONE, detector.validateCollision(entity2, entity2));
  }
}