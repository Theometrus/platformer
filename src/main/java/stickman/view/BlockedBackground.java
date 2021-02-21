package stickman.view;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import stickman.model.GameEngine;

public class BlockedBackground implements BackgroundDrawer {

  private Rectangle sky;
  private Rectangle floor;
  private Pane pane;
  private GameEngine model;

  @Override
  public void draw(GameEngine model, Pane pane) {
    this.model = model;
    this.pane = pane;

    double width = pane.getWidth();
    double height = pane.getHeight();
    double floorHeight = model.getCurrentLevel().getFloorHeight();

    this.sky = new Rectangle(0, 0, width, 10000);
    sky.setFill(Paint.valueOf("LIGHTBLUE"));
    sky.setViewOrder(1000.0);

    this.floor = new Rectangle(0, floorHeight, width, height - floorHeight);
    floor.setFill(Paint.valueOf("GREEN"));
    floor.setViewOrder(1000.0);

    Image img = new Image("backgroundColorForest.png");

    BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    Background bg = new Background(bgImg);
    pane.setBackground(bg);

    pane.getChildren().addAll(floor);
  }

  @Override
  public void update(double xViewportOffset, double yViewportOffset) {
    floor.setY(yViewportOffset);
  }
}
