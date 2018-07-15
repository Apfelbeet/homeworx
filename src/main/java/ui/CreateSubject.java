package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import data.DataManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Schedule;
import logic.Subject;

import java.util.Objects;

public class CreateSubject extends Application{

    @FXML
    JFXTextField nameTextField;

    @FXML
    JFXTextField shortNameTextField;

    @FXML
    JFXButton saveButton;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("CreateSubject.fxml")));

        Scene scene = new Scene(root);

        stage.setTitle("Create Subject");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @FXML
    public void initialize(){

    }

    @FXML
    public void saveButton_Action(){
        Subject subject = new Subject(nameTextField.getText(), shortNameTextField.getText());
        Schedule.schedule.getSubjects().add(subject);
        DataManager.saveSubjects(subject);
    }


}
