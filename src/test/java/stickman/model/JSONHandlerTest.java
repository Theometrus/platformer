package stickman.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class JSONHandlerTest {
  JSONHandler handler;

  @Before
  public void setUp() throws Exception {
    handler = new JSONHandler();
    handler.setupFile("/testConfig.json");
  }

  @Test(expected = NullPointerException.class)
  public void setupFile() throws URISyntaxException {
    handler.setupFile("/RealFakeFiles");
  }

  @Test
  public void fetchBasicProperties() {
    HashMap<String, Double> map = new HashMap<>();
    map = handler.fetchBasicProperties();
    assertEquals(20.0, map.get("stickmanStartingXPos"), 0.1);
    assertEquals(40.0, map.get("stickmanHeight"), 0.1);
    assertEquals(20.0, map.get("stickmanWidth"), 0.1);
    assertEquals(0.5, map.get("cloudSpeed"), 0.1);
    assertEquals(38, map.get("heroGroundOffset"), 0.1);
    assertEquals(300, map.get("floorHeight"), 0.1);
  }

  @Test
  public void fetchPlatformPositions() {
    List<Double[]> actual = new ArrayList<>();
    List<Double[]> expected = new ArrayList<>();
    expected.add(new Double[]{120.0, 250.0});
    expected.add(new Double[]{200.0, 200.0});

    actual = handler.fetchPlatformPositions();

    assertArrayEquals(expected.get(0), actual.get(0));
    assertArrayEquals(expected.get(1), actual.get(1));
  }

  @Test
  public void fetchEnemies() {
    List<EntityContainer> expected = new ArrayList<>();
    List<EntityContainer> actual = new ArrayList<>();
    expected.add(new EntityContainer("erraticEnemySlime", 100.0, 300.0));
    expected.add(new EntityContainer("erraticEnemySlime", 200.0, 300.0));
    expected.add(new EntityContainer("normalEnemySlime", 300.0, 300.0));

    actual = handler.fetchEnemies();

    assertEquals(expected.get(0).getType(), actual.get(0).getType());
    assertEquals(expected.get(1).getType(), actual.get(1).getType());
    assertEquals(expected.get(2).getType(), actual.get(2).getType());
    assertEquals(expected.get(0).getXPos(), actual.get(0).getXPos(), 0.1);
    assertEquals(expected.get(1).getXPos(), actual.get(1).getXPos(), 0.1);
    assertEquals(expected.get(2).getXPos(), actual.get(2).getXPos(), 0.1);
    assertEquals(expected.get(0).getYPos(), actual.get(0).getYPos(), 0.1);
    assertEquals(expected.get(1).getYPos(), actual.get(1).getYPos(), 0.1);
    assertEquals(expected.get(2).getYPos(), actual.get(2).getYPos(), 0.1);
  }

  @Test
  public void fetchFinishFlag() {
    double[] expected = {500.0, 300.0};
    double[] actual = handler.fetchFinishFlag();
    assertArrayEquals(expected, actual, 0.1);
  }
}