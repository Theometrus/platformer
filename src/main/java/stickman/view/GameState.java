package stickman.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import stickman.model.Entity;
import stickman.model.GameEngine;

public class GameState implements State {

  private GameEngine model;
  private List<EntityView> entityViews;
  private BackgroundDrawer backgroundDrawer;
  private double xViewportOffset = 0.0;
  private double yViewportOffset = 0.0;
  private double floorHeight;
  private static final double VIEWPORT_MARGIN = 280.0;
  private static final double YVIEWPORT_MARGIN = 150.0;
  private GameWindow context;
  private Overlay overlay;
  private int width;
  private int height;
  private Pane pane;

  /**
   * The state of a live game
   */
  public GameState(GameWindow context) {
    this.context = context;
    model = context.getModel();
    width = context.getWidth();
    height = context.getHeight();
    pane = context.getPane();

    overlay = new Overlay(model, pane);

    this.floorHeight = model.getCurrentLevel().getFloorHeight();
    this.entityViews = new ArrayList<>();
    this.backgroundDrawer = new BlockedBackground();
    backgroundDrawer.draw(model, pane);
  }

  public void removeMarkedEntities() {
    for (EntityView entityView : entityViews) {
      if (entityView.isMarkedForDelete()) {
        pane.getChildren().remove(entityView.getNode());
      }
    }
    entityViews.removeIf(EntityView::isMarkedForDelete);
  }

  @Override
  public void draw() {
    model.tick();

    for (EntityView entityView : entityViews) {
      entityView.markForDelete();
    }

    // If the player lost or won, change the state of the game
    if (model.conclude()) {
      removeMarkedEntities();
      context.setState(new GameOverState(pane, model.isWon()));
      return;
    }

    List<Entity> entities = model.getCurrentLevel().getEntities();

    double heroXPos = model.getCurrentLevel().getHeroX();
    double heroYPos = model.getCurrentLevel().getHero().getYPos();
    heroXPos -= xViewportOffset;
    heroYPos += yViewportOffset;

    if (heroXPos < VIEWPORT_MARGIN) {
      if (xViewportOffset >= 0) { // Don't go further left than the start of the level
        xViewportOffset -= VIEWPORT_MARGIN - heroXPos;
        if (xViewportOffset < 0) {
          xViewportOffset = 0;
        }
      }
    } else if (heroXPos > width - VIEWPORT_MARGIN) {
      xViewportOffset += heroXPos - (width - VIEWPORT_MARGIN);
    }

    // Makes the camera follow the hero when he goes up or down
    if (heroYPos > height - YVIEWPORT_MARGIN) {
      if (yViewportOffset > 0) {
        yViewportOffset -= heroYPos - (height - YVIEWPORT_MARGIN);
        floorHeight -= heroYPos - (height - YVIEWPORT_MARGIN);

        if (yViewportOffset < 0) {
          yViewportOffset = 0;
          floorHeight = model.getCurrentLevel().getFloorHeight();
        }
      }

    } else if (heroYPos < YVIEWPORT_MARGIN) {
      yViewportOffset += YVIEWPORT_MARGIN - heroYPos;
      floorHeight += YVIEWPORT_MARGIN - heroYPos;
    }

    backgroundDrawer.update(xViewportOffset, floorHeight);
    overlay.update();

    for (Entity entity : entities) {
      boolean notFound = true;
      for (EntityView view : entityViews) {
        if (view.matchesEntity(entity)) {
          notFound = false;
          view.update(xViewportOffset, yViewportOffset);
          break;
        }
      }
      if (notFound) {
        EntityView entityView = new EntityViewImpl(entity);
        entityViews.add(entityView);
        pane.getChildren().add(entityView.getNode());
      }
    }
    removeMarkedEntities();
  }
}
