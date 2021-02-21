package stickman.model;

import java.util.HashMap;
import java.net.URISyntaxException;
import java.util.List;

public class GameEngineImpl implements GameEngine {

  private Level level;
  private Hero hero;
  private JSONHandler handler = new JSONHandler();
  private boolean won = false;
  private boolean lost = false;

  public GameEngineImpl(String jsonFile) throws URISyntaxException {
    HashMap<String, Double> settings = new HashMap<>();

    handler.setupFile(jsonFile);
    settings = handler.fetchBasicProperties();
    handler.fetchEnemies();

    hero = new Hero("ch_stand1.png", settings.get("stickmanWidth"),
      settings.get("stickmanHeight"), 0, 0, Entity.Layer.FOREGROUND,
      settings.get("heroSpeed"), 0, settings.get("heroGroundOffset"));

    startLevel(settings.get("stickmanStartingXPos"), settings.get("cloudSpeed"),
      settings.get("floorHeight"));
  }

  @Override
  public Level getCurrentLevel() {
    return level;
  }

  @Override
  public void startLevel(double heroStartPos, double cloudSpeed, double floorHeight) {
    LevelMaker levelMaker = new LevelMaker();
    List<Double[]> platformPositions = handler.fetchPlatformPositions();
    List<EntityContainer> enemies = handler.fetchEnemies();
    double[] finishFlagPos = handler.fetchFinishFlag();

    // Level maker is used to encapsulate level entity spawning
    level = levelMaker.make(hero, heroStartPos, 1000, 2000, cloudSpeed,
      platformPositions, enemies, finishFlagPos, floorHeight);
  }

  @Override
  public boolean jump() {
    return level.jump();
  }

  @Override
  public boolean moveLeft() {
    level.moveLeft();
    return true;
  }

  @Override
  public boolean moveRight() {
    level.moveRight();
    return true;
  }

  @Override
  public boolean stopMoving() {
    level.stopMoving();
    return true;
  }

  public boolean conclude() {
    return won || lost;
  }

  @Override
  public boolean isWon() {
    return won;
  }

  public boolean isLost() {
    return lost;
  }

  @Override
  public void tick() {
    level.tick();
    won = level.isWon();
    lost = (hero.getLives() <= 0);
  }
}
