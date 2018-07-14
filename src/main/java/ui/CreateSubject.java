package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class CreateSubject extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("CreateSubject.fxml")));

        Scene scene = new Scene(root, 500, 250);

        stage.setTitle("FXML Welcome Location: " + System.getProperty("user.dir"));
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}
