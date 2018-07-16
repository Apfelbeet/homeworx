package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import data.DataManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Schedule;
import logic.Subject;

import java.io.IOException;
import java.util.Objects;

public class CreateSubject extends Pane {

    @FXML
    JFXTextField nameTextField;

    @FXML
    JFXTextField shortNameTextField;

    @FXML
    JFXButton saveButton;

    @FXML JFXButton backButton;


    public CreateSubject() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("CreateSubject.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        saveButton.setOnAction(event -> {
            Subject subject = new Subject(nameTextField.getText(), shortNameTextField.getText());
            Schedule.schedule.getSubjects().add(subject);
            DataManager.saveSubjects(subject);
            back();
        });

        backButton.setOnAction(event -> back());

    }

    private void back() {
        BaseWindow.Stage.setScene(new Scene(new MainWindow()));
    }
    /*@Override
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
    }*/


}
