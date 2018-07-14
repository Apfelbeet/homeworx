package ui;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.util.Callback;
import logic.*;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainWindow extends Application {
    @FXML
    private ProgressBar hallo;

    @FXML
    private TableView subjectGrid;
    private Scene scene;
    private Stage stage;

    @FXML
    private GridPane gridPane;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("MainWindow.fxml")));

        scene = new Scene(root, 500, 250);

        stage.setTitle("HomeworX: " + System.getProperty("user.dir"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void initialize() {

        TableView<Lesson> tableView = new TableView<>();

        for(Day day : Day.values()){
            ObservableList<Lesson> lessons = FXCollections.observableArrayList(Schedule.schedule.getDays().get(day).getLessons());
            TableColumn<Lesson, String> lessonColumn = new TableColumn(day.toString());

            lessonColumn.setCellValueFactory(p -> {
                return new ReadOnlyStringWrapper(p.getValue().getSubject().getName());
            });
            tableView.getColumns().add(lessonColumn);
        }

        gridPane.add(tableView, 1,1);


    }

}
