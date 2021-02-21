package stickman.model;

/**
 * Interface implemented by the hero and which marks a character as being player-controlled
 */
public interface Controllable {

  void startMovingRight();

  void startMovingLeft();

  void stopMoving();

  void setGroundLevel(double groundLevel);

  double getGroundLevel();

  void resetGroundLevel();
}
