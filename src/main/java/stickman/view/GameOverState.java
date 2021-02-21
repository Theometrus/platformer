package stickman.view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import stickman.model.GameEngine;

public class GameOverState implements State {

  Pane pane;

  /**
   * Both the victory and defeat screen
   */
  public GameOverState(Pane pane, boolean won) {
    this.pane = pane;
    TextField text;
    Button button = new Button("Quit");
    button.setOnAction(actionEvent -> {
      Platform.exit();
    });

    button.relocate(290, 300);

    if (won) {
      text = new TextField("Victorious!");
    } else {
      text = new TextField("Defeated!");
    }

    text.setFont(Font.font("Tahoma", FontWeight.BOLD, 60));
    text.setAlignment(Pos.CENTER);
    text.relocate(-90, 0);
    text.setDisable(true);
    text.setStyle("-fx-opacity: 1;");
    pane.getChildren().addAll(button, text);
  }

  @Override
  public void draw() {

  }
}
