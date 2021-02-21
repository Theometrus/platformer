package stickman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import stickman.model.Entity;
import stickman.model.GameEngine;

import java.util.ArrayList;
import java.util.List;

public class GameWindow {

  private final int width;
  private final int height;
  private Scene scene;
  private Pane pane;
  private GameEngine model;
  private State currentState;

  public GameWindow(GameEngine model, int width, int height) {
    this.width = width;
    this.height = height;
    this.pane = new Pane();
    this.scene = new Scene(pane, width, height);
    this.model = model;

    currentState = new GameState(this);

    KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(model);

    scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
    scene.setOnKeyReleased(keyboardInputHandler::handleReleased);
  }

  public Scene getScene() {
    return this.scene;
  }

  public void run() {
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  public void draw() {
    currentState.draw();
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Pane getPane() {
    return this.pane;
  }

  public GameEngine getModel() {
    return model;
  }

  public void setState(State state) {
    currentState = state;
  }
}
