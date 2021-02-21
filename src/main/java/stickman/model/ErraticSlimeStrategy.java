
package stickman.model;

import java.util.Random;

public class ErraticSlimeStrategy implements Strategy {

  private Random rand;
  private AbsMovingEntity context;
  private int count;
  private double move;
  private int[] arr = {-1, 1};

  public ErraticSlimeStrategy(AbsMovingEntity context) {
    this.context = context;
    rand = new Random();
    count = 0;
  }

  /**
   * The entity picks a random direction to move in once every 118 ticks and goes that way with a
   * random speed. Can also stop and stand in place for a while before resuming movement.
   */
  @Override
  public void move() {
    // Direction is -1 or 1
    int direction = arr[rand.nextInt(2)];

    if (count >= 118) {
      count = 0;
      move = rand.nextDouble() * direction * rand.nextInt(2);
      return;
    }

    if (context.getXPos() <= 0) {
      context.setXPos(0);
      move = rand.nextDouble();
    }
    context.setXPos(context.getXPos() + move);
    count++;
  }
}

