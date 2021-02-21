package stickman.model;

public class Enemy extends AbsGroundedMovingEntity implements Damageable {

  private Strategy strategy;

  public Enemy(String imagePath, double width, double height, double XPos,
    double YPos, Layer layer, double defaultSpeed, double floorHeight, double spriteOffset) {

    super(imagePath, width, height, XPos, YPos, layer, defaultSpeed, floorHeight, spriteOffset);
  }

  public void setStrategy(Strategy strategy) {
    this.strategy = strategy;
  }

  /**
   * Usage of the strategy pattern allows for great flexibility in enemy movement patterns
   */
  @Override
  public void move() {
    if (strategy != null) {
      strategy.move();
    }
  }

  /**
   * In the case of the basic enemies, they just get killed when the hero jumps on top of them once.
   * Other enemy types in the future could incorporate a lives counter into this.
   */
  @Override
  public void getDamaged() {
    mark();
  }

  @Override
  public void jump() {

  }
}
