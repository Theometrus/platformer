package stickman.model;

public class EntityImpl implements Entity {

  protected String imagePath;
  protected double XPos;
  protected double YPos;
  protected double width;
  protected double height;
  protected Layer layer;
  protected double spriteOffset;
  protected boolean marked = false;

  public EntityImpl(String imagePath, double width, double height,
    double XPos, double YPos, Layer layer, double spriteOffset) {
    this.imagePath = imagePath;
    this.width = width;
    this.height = height;
    this.layer = layer;
    this.XPos = XPos;
    this.YPos = YPos;
    this.spriteOffset = spriteOffset;
  }

  @Override
  public String getImagePath() {
    return imagePath;
  }

  @Override
  public boolean isMarked() {
    return marked;
  }

  @Override
  public void mark() {
    marked = true;
  }

  @Override
  public double getXPos() {
    return XPos;
  }

  @Override
  public double getYPos() {
    return YPos;
  }

  @Override
  public double getHeight() {
    return height;
  }

  @Override
  public double getWidth() {
    return width;
  }

  @Override
  public Layer getLayer() {
    return layer;
  }

  @Override
  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  @Override
  public double getSpriteOffset() {
    return spriteOffset;
  }
}
