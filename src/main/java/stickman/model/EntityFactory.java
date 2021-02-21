package stickman.model;

import stickman.model.Entity.Layer;

/**
 * Concrete implementation of the factory, stores information about various entities such as their
 * sprite path and dimensions. The client needs only fill out basic details such as entity type and
 * coordinates, and the factory can extrapolate the rest (such as ground offset) from the requested
 * entity type.
 */
public class EntityFactory implements Factory {

  // Params stored here for ease of modification
  private double[] cloudDimensions = {60, 20};
  private double[] enemySlimeDimensions = {30, 25};
  private double[] platformDimensions = {70, 15};
  private double cloudSpeed = 1;
  private double enemySlimeSpeed = 0.7;
  private double floorHeight = 0;
  private double levelWidth = 1000;

  private String cloudImg = "cloud_2.png";
  private String erraticSlimeImg = "slimeRa.png";
  private String normalSlimeImg = "slimeBa.png";
  private String platformImg = "medievalTile_162.png";

  public EntityFactory(double floorHeight, double cloudSpeed) {
    this.cloudSpeed = cloudSpeed;
    this.floorHeight = floorHeight;
  }

  public void setLevelWidth(double levelWidth) {
    this.levelWidth = levelWidth;
  }

  @Override
  public Entity make(String entityType, double XPos, double YPos) {
    Strategy strategy;

    /*
     * Wanted to use enums for the entity types but that unnecessarily complicated entity type
     * reading from the JSON file. Strings proved easier to pass around and resulted in slightly
     * lower coupling.
     */
    switch (entityType) {
      case "cloud":
        Cloud cloud = new Cloud(cloudImg, cloudDimensions[0], cloudDimensions[1],
          XPos, YPos, Layer.BACKGROUND, cloudSpeed, 0, levelWidth);

        return cloud;

      case "erraticEnemySlime":
        Enemy erraticSlime = new Enemy(erraticSlimeImg, enemySlimeDimensions[0],
          enemySlimeDimensions[1], XPos, YPos, Layer.FOREGROUND, enemySlimeSpeed,
          floorHeight, 22);

        strategy = new ErraticSlimeStrategy(erraticSlime);
        erraticSlime.setStrategy(strategy);

        return erraticSlime;

      case "normalEnemySlime":
        Enemy normalSlime = new Enemy(normalSlimeImg, enemySlimeDimensions[0],
          enemySlimeDimensions[1],
          XPos, YPos, Layer.FOREGROUND, enemySlimeSpeed, floorHeight, 22);

        strategy = new NormalSlimeStrategy(normalSlime);
        normalSlime.setStrategy(strategy);

        return normalSlime;

      case "platform":
        return new Platform(platformImg, platformDimensions[0], platformDimensions[1],
          XPos, YPos, Layer.FOREGROUND, 15);
    }
    return null;
  }
}
