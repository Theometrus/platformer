package stickman.model;

public interface GameEngine {

  Level getCurrentLevel();

  void startLevel(double heroStartPos, double cloudSpeed, double floorHeight);

  boolean jump();

  boolean moveLeft();

  boolean moveRight();

  boolean stopMoving();

  /**
   * @return whether the game is lost or won (in other words, finished)
   */
  boolean conclude();

  void tick();

  /**
   * Required for deciding which screen (victory vs defeat) to send the player to after concluding.
   */
  boolean isWon();
}
