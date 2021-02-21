package stickman.view;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import stickman.model.Enemy;
import stickman.model.Entity;
import stickman.model.Entity.Layer;
import stickman.model.EntityImpl;

public class EntityViewImplTest extends ApplicationTest {
  Entity entity;
  Entity entity2;
  EntityView entityView;
  String imgPath = "tree.png";

  @Before
  public void setUp() {
    entity = new EntityImpl(imgPath, 0, 0, 0, 0, Layer.FOREGROUND, 0);
    entity = new EntityImpl(imgPath, 50, 0, 0, 0, Layer.FOREGROUND, 0);

    entityView = new EntityViewImpl(entity);
  }

  @Test
  public void matchesEntitySimple() {
    assertTrue(entityView.matchesEntity(entity));
    assertFalse(entityView.matchesEntity(entity2));
  }

  @Test
  public void matchesEntityOtherTypes() {
    Enemy enemy = new Enemy(imgPath, 0, 0, 0, 0, Layer.FOREGROUND,
      0, 0, 0);
    assertFalse(entityView.matchesEntity(enemy));
  }

  @Test
  public void markEntities() {
    assertFalse(entityView.isMarkedForDelete());
    entityView.markForDelete();
    assertTrue(entityView.isMarkedForDelete());
  }
}