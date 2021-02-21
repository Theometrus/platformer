package stickman.view;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.security.Key;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jdk.jfr.EventType;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import stickman.App;
import stickman.model.GameEngine;
import stickman.model.GameEngineImpl;

public class KeyboardInputHandlerTest extends ApplicationTest {
  KeyboardInputHandler handler;
  GameEngine engine;

  @Before
  public void setUp() throws URISyntaxException {
    engine = new GameEngineImpl("/testConfig.json");
    handler = new KeyboardInputHandler(engine);
  }

  @Test
  public void handlePressed() {
    assertEquals(20, engine.getCurrentLevel().getHeroX(), 0.1);
    KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.RIGHT,
      false, false, false, false);
    handler.handlePressed(event);
    engine.tick();
    assertEquals(22, engine.getCurrentLevel().getHeroX(), 0.5);


    assertEquals(300, engine.getCurrentLevel().getHero().getYPos(), 0.5);
    event = new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.UP,
      false, false, false, false);
    handler.handlePressed(event);
    engine.tick();
    assertEquals(297, engine.getCurrentLevel().getHero().getYPos(), 0.1);

    event = new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.LEFT,
      false, false, false, false);
    handler.handlePressed(event);
    engine.tick();
    engine.tick();
    engine.tick();
    assertEquals(23, engine.getCurrentLevel().getHeroX(), 0.5);
  }

  @Test
  public void handleReleased() {
    assertEquals(20, engine.getCurrentLevel().getHeroX(), 0.1);
    KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.RIGHT,
      false, false, false, false);
    handler.handlePressed(event);
    engine.tick();
    assertEquals(22, engine.getCurrentLevel().getHeroX(), 0.5);

    handler.handleReleased(event);
    engine.tick();
    engine.tick();
    assertEquals(22, engine.getCurrentLevel().getHeroX(), 0.5);
  }
}