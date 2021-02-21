package stickman.model;

public class Cloud extends AbsMovingEntity {

  double levelWidth;

  public Cloud(String imagePath, double width, double height, double XPos, double YPos,
    Layer layer, double defaultSpeed, double spriteOffset, double levelWidth) {
    super(imagePath, width, height, XPos, YPos, layer, defaultSpeed, spriteOffset);
    this.levelWidth = levelWidth;
  }

  /**
   * Always just moves right and resets its position if it flies outside of the level boundaries
   */
  @Override
  void move() {
    XPos += speed;
    if (XPos >= levelWidth) {
      XPos = -100;
    }
  }
}
