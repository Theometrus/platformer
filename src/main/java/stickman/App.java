package stickman;

import javafx.application.Application;
import javafx.stage.Stage;
import stickman.model.GameEngine;
import stickman.model.GameEngineImpl;
import stickman.view.GameWindow;

import java.net.URISyntaxException;
import java.util.Map;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        Map<String, String> params = getParameters().getNamed();

        GameEngine model = new GameEngineImpl("/sampleLevel.json");
        GameWindow window = new GameWindow(model, 640, 400);
        window.run();

        primaryStage.setTitle("Stickman");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();

        //System.out.println("test");
    }
}
