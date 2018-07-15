package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Schedule;

public class BaseWindow extends Application {

    public static Stage Stage = null;

    @Override
    public void start(Stage stage) {
        Stage = stage;

        Pane pane = new ViewSchedule();
        Scene scene = new Scene(pane);

        stage.setTitle("HomeworX: " + System.getProperty("user.dir"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
