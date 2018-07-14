package ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import data.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import logic.*;

import java.io.IOException;
import java.util.ArrayList;

public class CreateGrade extends Pane {
    private Subject subject;

    @FXML private Label subject_name;
    @FXML private ImageView back_button;
    @FXML private ImageView add_button;
    @FXML private JFXComboBox<GradeType> grade_type;
    @FXML private JFXComboBox<Integer> grade_value;


    public CreateGrade(Subject subject) {

        //Initialise FXML-File
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("CreateGrade.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        //Set static Subject
        this.subject = subject;

        //Set simple Text
        subject_name.setText(subject.getName());

        back_button.setOnMouseClicked(event -> {
            back();
        });

        add_button.setOnMouseClicked(event -> {
            if(grade_type.getValue() != null && grade_value.getValue() != null) {
                Grade grade = new Grade(grade_value.getValue(), grade_type.getValue());
                subject.addNewGrade(grade);
                DataManager.saveGrade(subject, grade);
                back();
            }
        });

        grade_type.setItems(FXCollections.observableArrayList(GradeType.values()));
        grade_value.setItems(FXCollections.observableArrayList(Grade.VALUES));


    }

    private void back() {
        BaseWindow.Stage.setScene(new Scene(new ViewGrade(subject)));
    }



}
