package stickman.view;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import stickman.model.GameEngine;

import java.util.HashSet;
import java.util.Set;

class KeyboardInputHandler {

  private final GameEngine model;
  private boolean left = false;
  private boolean right = false;
  private Set<KeyCode> pressedKeys = new HashSet<>();

  KeyboardInputHandler(GameEngine model) {
    this.model = model;
  }

  void handlePressed(KeyEvent keyEvent) {
    if (pressedKeys.contains(keyEvent.getCode())) {
      return;
    }
    pressedKeys.add(keyEvent.getCode());

    if (keyEvent.getCode().equals(KeyCode.UP)) {
      model.jump();
    }

    if (keyEvent.getCode().equals(KeyCode.LEFT)) {
      left = true;
    } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
      right = true;
    } else {
      return;
    }

    if (left) {
      if (right) {
        model.stopMoving();
      } else {
        model.moveLeft();
      }
    } else {
      model.moveRight();
    }
  }

  void handleReleased(KeyEvent keyEvent) {
    pressedKeys.remove(keyEvent.getCode());

    if (keyEvent.getCode().equals(KeyCode.LEFT)) {
      left = false;
    } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
      right = false;
    } else {
      return;
    }

    if (!(right || left)) {
      model.stopMoving();
    } else if (right) {
      model.moveRight();
    } else {
      model.moveLeft();
    }
  }
}
