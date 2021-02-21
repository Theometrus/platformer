package stickman.model;

public class Platform extends EntityImpl {

  public Platform(String imagePath, double width, double height, double XPos, double YPos,
    Layer layer, double spriteOffset) {
    super(imagePath, width, height, XPos, YPos, layer, spriteOffset);
  }
}
