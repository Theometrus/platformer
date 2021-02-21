package stickman.model;

/**
 * This container is used for easily transferring entity information between classes. This information
 * is first read from the JSON file, then passed down the line.
 */
public class EntityContainer {

  private String type;
  private double XPos;
  private double YPos;

  public EntityContainer(String type, double XPos, double YPos) {
    this.type = type;
    this.XPos = XPos;
    this.YPos = YPos;
  }

  public String getType() {
    return type;
  }

  public double getXPos() {
    return XPos;
  }

  public double getYPos() {
    return YPos;
  }
}
