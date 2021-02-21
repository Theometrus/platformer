package stickman.model;

abstract class AbsGroundedMovingEntity extends AbsMovingEntity {

  protected double floorHeight;
  protected boolean jumping = false;
  protected boolean falling = false;
  protected double YVelocity;
  protected double groundLevel;
  protected Entity platform;

  // Affect jump speed/height
  protected double defaultYVelocity = 3f;
  protected double gravity = 0.07f;

  public AbsGroundedMovingEntity(String imagePath, double width, double height, double XPos,
    double YPos, Layer layer, double defaultSpeed, double floorHeight, double spriteOffset) {

    super(imagePath, width, height, XPos, YPos, layer, defaultSpeed, spriteOffset);
    this.floorHeight = floorHeight;
    groundLevel = floorHeight;
  }

  public void setYVelocity(double YVelocity) {
    this.YVelocity = YVelocity;
  }

  public double getYVelocity() {
    return YVelocity;
  }

  /**
   * This supports the concept of entities being able to stand on platforms. Currently, however,
   * only the hero is able to interact with platforms. This can easily be changed in the future.
   */
  public void setPlatform(Entity platform) {
    this.platform = platform;
  }

  public boolean startJumping() {
    if (YPos != groundLevel) {
      return false;
    }

    // This has to do with the primitive deceleration that results in the entity having smoother jumps
    YVelocity = defaultYVelocity;
    jumping = true;
    return true;
  }

  public boolean isJumping() {
    return jumping;
  }

  /**
   * Currently unused but could be altered to make entities heavier or lighter during runtime.
   */
  public void setGravity(double gravity) {
    this.gravity = gravity;
  }

  public void setFloorHeight(double floorHeight) {
    this.floorHeight = floorHeight;
  }

  abstract void jump();

  abstract void move();
}
