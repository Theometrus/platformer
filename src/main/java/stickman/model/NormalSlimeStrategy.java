package stickman.model;

public class NormalSlimeStrategy implements Strategy {

  AbsMovingEntity context;
  int direction = 1;
  double distanceCovered = 0;
  double patrolRange = 500;

  public NormalSlimeStrategy(AbsMovingEntity context) {
    this.context = context;
  }

  /**
   * Normal slimes will simply patrol the area around their spawn at a constant speed
   */
  @Override
  public void move() {
    if (distanceCovered <= patrolRange) {
      context.setXPos(context.getXPos() + (context.getSpeed() * direction));
      distanceCovered += context.getSpeed();
    } else {
      direction *= -1;
      distanceCovered = 0;
    }
  }
}
