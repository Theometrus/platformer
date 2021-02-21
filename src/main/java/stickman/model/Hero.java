package stickman.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Hero extends AbsGroundedMovingEntity implements Controllable, Damageable {

  private boolean movingLeft = false;
  private boolean movingRight = false;
  private int animCounter = 0;
  private int animCounterL = 4;
  private int tickCounter = 20;
  private int lives = 3;
  private double startingXPos;
  private List<String> images;
  private Map<String, MediaPlayer> sounds = new HashMap<>();

  public Hero(String imagePath, double width, double height, double XPos,
    double YPos, Layer layer, double defaultSpeed, double floorHeight, double spriteOffset) {

    super(imagePath, width, height, XPos, YPos, layer, defaultSpeed, floorHeight, spriteOffset);

    URL mediaUrl = getClass().getResource("/jump.wav");
    String jumpURL = mediaUrl.toExternalForm();

    Media sound = new Media(jumpURL);
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    sounds.put("jump", mediaPlayer);

    // These are used for the running animation
    images = new ArrayList<>();
    images.add("ch_walk1.png");
    images.add("ch_walk2.png");
    images.add("ch_walk3.png");
    images.add("ch_walk4.png");
    images.add("ch_walk5.png");
    images.add("ch_walk6.png");
    images.add("ch_walk7.png");
    images.add("ch_walk8.png");
  }

  @Override
  public void jump() {
    if (jumping) {
      YPos -= YVelocity;
      YVelocity = YVelocity - gravity;

      if (YVelocity <= 0) {
        jumping = false;
        falling = true;
      }
    }
  }

  /**
   * Used for sending the hero back to spawn after they take a hit from an enemy
   */
  public void resetPos() {
    XPos = startingXPos;
  }

  public void setStartingXPos(double startingXPos) {
    this.startingXPos = startingXPos;
  }

  /**
   * Jump sound playback moved here from keyboard input handler, due to it being unique to the hero
   * of the current game.
   */
  private void playJumpSound() {
    MediaPlayer jumpPlayer = sounds.get("jump");
    jumpPlayer.stop();
    jumpPlayer.play();
  }

  @Override
  public boolean startJumping() {
    boolean result = super.startJumping();
    if (result) {
      playJumpSound();
      return true;
    }
    return false;
  }

  /**
   * Isn't called under normal circumstances. Only called when the player falls on top of an enemy.
   * Allows the player to bounce off the enemy's head, Mario style.
   */
  public void forceJump() {
    playJumpSound();
    jumping = true;
    falling = false;
    YVelocity = defaultYVelocity;
    jump();
  }

  @Override
  public void move() {
    // Allows the hero to seamlessly transition between platforms and the ground.
    if (platform != null) {
      if (XPos > platform.getXPos() + platform.getWidth() || XPos + width < platform.getXPos()) {
        groundLevel = floorHeight;
        platform = null;
      }
    }

    // Prevent hero going off screen to the left
    if (XPos < 0) {
      setXPos(0);
    }

    tickCounter++;

    // Most of what is below is concerned with the hero's animation
    if (movingRight) {
      XPos += speed;

      if (tickCounter > 20) {
        setImagePath(images.get(animCounter));
        animCounter++;
        if (animCounter >= 4) {
          animCounter = 0;
        }
        tickCounter = 0;
      }

    } else if (movingLeft) {
      XPos -= speed;

      if (tickCounter > 20) {
        setImagePath(images.get(animCounterL));
        animCounterL++;
        if (animCounterL >= 8) {
          animCounterL = 4;
        }
        tickCounter = 0;
      }
    }
  }

  @Override
  public void startMovingRight() {
    movingRight = true;
    movingLeft = false;
  }

  @Override
  public void startMovingLeft() {
    movingLeft = true;
    movingRight = false;
  }

  @Override
  public void stopMoving() {
    movingLeft = false;
    movingRight = false;
  }

  @Override
  public void setGroundLevel(double groundLevel) {
    this.groundLevel = groundLevel;
  }

  @Override
  public double getGroundLevel() {
    return groundLevel;
  }

  @Override
  public void resetGroundLevel() {
    groundLevel = floorHeight;
  }


  /**
   * The second half of the jumping process, and also useful for when the hero walks off the
   * edge of a platform. Brings the hero back down to the ground (or another platform, should they
   * collide with one (handled in CollisionHandler))
   */
  public void fall() {
    if (YPos < groundLevel && !jumping) {
      YPos += YVelocity;
      YVelocity += gravity;

      if (YPos >= groundLevel) {
        YVelocity = 0;
        YPos = groundLevel;
        jumping = false;
        falling = false;
      }
    }
  }

  @Override
  public void getDamaged() {
    lives--;
  }

  public int getLives() {
    return lives;
  }
}
