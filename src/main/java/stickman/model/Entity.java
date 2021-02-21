package stickman.model;

public interface Entity {

  String getImagePath();

  void setImagePath(String imagePath);

  double getXPos();

  double getYPos();

  double getHeight();

  double getWidth();

  Layer getLayer();

  boolean isMarked();

  /**
   * Mark for deletion
   */
  void mark();

  double getSpriteOffset();

  enum Layer {
    BACKGROUND, FOREGROUND, EFFECT
  }
}
