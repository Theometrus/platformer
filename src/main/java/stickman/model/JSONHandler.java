package stickman.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Responsible for extracting information from the JSON config file.
 */
public class JSONHandler {

  private JSONParser parser = new JSONParser();
  private JSONObject obj;

  public void setupFile(String jsonFile) throws URISyntaxException {
    URI uri = new URI(this.getClass().getResource(jsonFile).toString());
    String path = uri.getPath();

    try {
      obj = (JSONObject) parser.parse(new FileReader(path));

    } catch (ParseException e) {
      e.printStackTrace();

    } catch (FileNotFoundException e) {
      e.printStackTrace();

    } catch (IOException e) {
      e.printStackTrace();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Parses JSON file, extracts basic information from it.
   * This includes most things that could be represented by a single double like stickman height,
   * speed, etc.
   * @return a hashmap of the extracted settings
   */
  public HashMap<String, Double> fetchBasicProperties() {
    HashMap<String, Double> result = new HashMap<>();

    // Default settings are for a stickman of normal size (in case json file contains errors)
    String stickmanSize = "normal";
    double stickmanWidth = 20;
    double stickmanHeight = 40;
    double stickmanStartingXPos = 0;
    double cloudSpeed = 1;
    double heroGroundOffset = 34;
    double floorHeight = 300;
    double heroSpeed = 1.75;

    if (obj != null) {
      stickmanSize = (String) obj.get("stickmanSize");
      cloudSpeed = (double) obj.get("cloudVelocity") / 60;
      stickmanStartingXPos = (double) ((JSONObject) obj.get("stickmanPos")).get("x");
      floorHeight = (double) obj.get("floorHeight");
      heroSpeed = (double) obj.get("heroSpeed");
    }

    // Stickman size logic
    switch (stickmanSize) {
      case "tiny":
        stickmanHeight = 20;
        stickmanWidth = 10;
        heroGroundOffset = 20;
        break;
      case "normal":
        stickmanHeight = 40;
        stickmanWidth = 20;
        heroGroundOffset = 38;
        break;
      case "large":
        stickmanHeight = 70;
        stickmanWidth = 35;
        heroGroundOffset = 70;
        break;
      case "giant":
        stickmanHeight = 100;
        stickmanWidth = 50;
        heroGroundOffset = 98;
        break;
    }
    result.put("stickmanWidth", stickmanWidth);
    result.put("stickmanHeight", stickmanHeight);
    result.put("stickmanStartingXPos", stickmanStartingXPos);
    result.put("cloudSpeed", cloudSpeed);
    result.put("heroGroundOffset", heroGroundOffset);
    result.put("floorHeight", floorHeight);
    result.put("heroSpeed", heroSpeed);
    return result;
  }

  /**
   * @return list of coordinates of every platform to be spawned in the level
   */
  public List<Double[]> fetchPlatformPositions() {
    JSONArray platformPositions;
    platformPositions = (JSONArray) obj.get("platformPositions");
    List<Double[]> result = new ArrayList<>();
    Double[] temp;

    for (int i = 0; i < platformPositions.size(); i++) {
      temp = new Double[2];
      temp[0] = (Double) ((JSONArray) platformPositions.get(i)).get(0);
      temp[1] = (Double) ((JSONArray) platformPositions.get(i)).get(1);
      result.add(temp);
    }

    return result;
  }

  public List<EntityContainer> fetchEnemies() {
    List<EntityContainer> enemies = new ArrayList<>();
    JSONObject enemyObject = (JSONObject) obj.get("enemies");
    JSONArray types = (JSONArray) enemyObject.get("types");
    JSONArray arr = (JSONArray) enemyObject.get("positions");

    // Extracts individual enemy entries and stores them in the return list
    for (int i = 0; i < types.size(); i++) {
      enemies.add(
        new EntityContainer((String) types.get(i), (double) ((JSONArray) arr.get(i)).get(0),
          (double) ((JSONArray) arr.get(i)).get(1)));
    }

    return enemies;
  }

  /**
   * @return array of the flag's X and Y coordinates, in the event that the flag was not meant to be
   * placed on ground level (allows placement of flag on platforms for example)
   */
  public double[] fetchFinishFlag() {
    double[] result = new double[2];
    result[0] = (double) ((JSONArray) obj.get("finishFlag")).get(0);
    result[1] = (double) ((JSONArray) obj.get("finishFlag")).get(1);
    return result;
  }
}
