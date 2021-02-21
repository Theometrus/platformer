package stickman.view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import stickman.model.GameEngine;

public class Overlay {

  private Pane pane;
  private GameEngine model;
  private Label livesLbl;
  private Label timer;
  private double counter = 0;

  /**
   * Displays the hero's current lives and timer on the screen.
   */
  public Overlay(GameEngine model, Pane pane) {
    this.pane = pane;
    this.model = model;
    livesLbl = new Label(String.format("Current lives: %d",
      model.getCurrentLevel().getHero().getLives()));
    livesLbl.setFont(Font.font("Herculanum", FontWeight.BOLD, 20));
    livesLbl.setTextFill(Color.RED);
    timer = new Label(String.format("Time elapsed: %.1f seconds", counter));
    timer.relocate(430, 0);
    timer.setFont(Font.font("Herculanum", FontWeight.BOLD, 15));
    pane.getChildren().addAll(livesLbl, timer);
  }

  public void update() {
    livesLbl.setText(String.format("Current lives: %d",
      model.getCurrentLevel().getHero().getLives()));
    counter += 0.0085;
    timer.setText(String.format("Time elapsed: %.0f second(s)", counter));
  }
}
