package ui;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Schedule;

public class SceneTest extends Application {

    public static Stage Stage = null;

    @Override
    public void start(Stage stage) throws Exception {
        Stage = stage;
        stage.setTitle("HomeworX: " + System.getProperty("user.dir"));
        //ViewSubject viewSubject = new ViewSubject(Schedule.schedule.getSubjects().get(0), null);
        ViewHomework viewSubject = new ViewHomework(Schedule.schedule.getSubjects().get(0).getHomework().get(1), Schedule.schedule.getSubjects().get(0));
        Scene scene = new Scene(viewSubject);
        stage.setScene(scene);
        viewSubject.setVisible(true);
        //stage.setResizable(false);
        stage.show();
    }
}
