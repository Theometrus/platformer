package stickman.model;

abstract class AbsMovingEntity extends EntityImpl {

  protected double speed;

  public AbsMovingEntity(String imagePath, double width, double height, double XPos, double YPos,
    Layer layer, double defaultSpeed, double spriteOffset) {

    super(imagePath, width, height, XPos, YPos, layer, spriteOffset);
    speed = defaultSpeed;
  }

  public void setXPos(double newXPos) {
    XPos = newXPos;
  }

  public void setYPos(double newYPos) {
    YPos = newYPos;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public double getSpeed() {
    return speed;
  }

  abstract void move();
}
