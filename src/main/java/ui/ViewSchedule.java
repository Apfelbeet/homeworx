package ui;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import logic.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewSchedule extends Pane {
    private boolean onEdit;
    private double gabH;
    private double gabV;

    @FXML
    private GridPane basic_pane;
    @FXML
    private GridPane top_pane;
    @FXML
    private ImageView delete_button;
    @FXML private ImageView add_button;
    @FXML
    private FlowPane pane;

    private ArrayList<JFXButton> subjects;

    private HashMap<JFXButton, Subject> dayMap;

    public ViewSchedule() {

        //Initialise FXML-File
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("ViewSchedule.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        subjects = new ArrayList<>();
        dayMap = new HashMap<>();


        /*for (Day day : Day.values()) {
            JFXButton button = new JFXButton(day.name());
            dayButtons.add(button);
        }*/

        Schedule.schedule.getSubjects().forEach(e -> {
            JFXButton button = new JFXButton(e.getName());
            button.setOnAction(event -> {
                BaseWindow.Stage.setScene(new Scene(new ViewSubject(dayMap.get(event.getSource()))));
            });
            subjects.add(button);
            dayMap.put(button, e);
        });

        pane.setAlignment(Pos.CENTER);
        pane.setVgap(5);
        pane.setHgap(5);
        pane.getChildren().addAll(FXCollections.observableArrayList(subjects));


        delete_button.setOnMouseClicked(event -> {

        });

        add_button.setOnMouseClicked(event -> {
            BaseWindow.Stage.setScene(new Scene(new CreateSubject()));
        });


    }


}
