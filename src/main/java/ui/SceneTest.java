package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Schedule;

public class SceneTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("HomeworX: " + System.getProperty("user.dir"));
        ViewSubject viewSubject = new ViewSubject(Schedule.schedule.getSubjects().get(0), null);
        Scene scene = new Scene(viewSubject);
        stage.setScene(scene);
        viewSubject.setVisible(true);
        //stage.setResizable(false);
        stage.show();
    }
}
