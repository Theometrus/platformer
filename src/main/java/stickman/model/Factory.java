package stickman.model;

public interface Factory {

  Entity make(String entityType, double XPos, double YPos);

  /**
   * This was mostly necessary for delegating level borders so that clouds would come out
   * from the level's beginning upon reaching the boundary.
   */
  void setLevelWidth(double width);
}
