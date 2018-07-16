package ui;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.util.Callback;
import logic.*;
import sun.applet.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainWindow extends Pane {
    @FXML
    private ProgressBar hallo;

    private Scene scene;
    private Stage stage;

    @FXML
    private GridPane gridPane;

    @FXML
    public JFXListView<Lesson> mondayListView;
    @FXML
    public JFXListView<Lesson> tuesdayListView;
    @FXML
    public JFXListView<Lesson> wednesdayListView;
    @FXML
    public JFXListView<Lesson> thursdayListView;
    @FXML
    public JFXListView<Lesson> fridayListView;
    @FXML
    public JFXListView<Lesson> saturdayListView;
    @FXML
    public JFXListView<Lesson> sundayListView;

    @FXML public ImageView add_button;

    public MainWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("MainWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for (Day day : Day.values()) {
            ObservableList<Lesson> lessons = FXCollections.observableArrayList(Schedule.schedule.getDays().get(day).getLessons());

            switch (day) {
                case Monday:

                    mondayListView.setItems(lessons);
                    mondayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Tuesday:

                    tuesdayListView.setItems(lessons);
                    tuesdayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Wednesday:

                    wednesdayListView.setItems(lessons);
                    wednesdayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Thursday:

                    thursdayListView.setItems(lessons);
                    thursdayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Friday:

                    fridayListView.setItems(lessons);
                    fridayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Saturday:

                    saturdayListView.setItems(lessons);
                    saturdayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Sunday:

                    sundayListView.setItems(lessons);
                    sundayListView.setCellFactory(listView -> new LessonElement());
                    break;
            }
        }


        add_button.setOnMouseClicked(event -> {
            BaseWindow.Stage.setScene(new Scene(new CreateSubject()));
        });
    }

    /*@Override
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
        for (Day day : Day.values()) {
            ObservableList<Lesson> lessons = FXCollections.observableArrayList(Schedule.schedule.getDays().get(day).getLessons());

            switch (day) {
                case Monday:

                    mondayListView.setItems(lessons);
                    mondayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Tuesday:

                    tuesdayListView.setItems(lessons);
                    tuesdayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Wednesday:

                    wednesdayListView.setItems(lessons);
                    wednesdayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Thursday:

                    thursdayListView.setItems(lessons);
                    thursdayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Friday:

                    fridayListView.setItems(lessons);
                    fridayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Saturday:

                    saturdayListView.setItems(lessons);
                    saturdayListView.setCellFactory(listView -> new LessonElement());
                    break;
                case Sunday:

                    sundayListView.setItems(lessons);
                    sundayListView.setCellFactory(listView -> new LessonElement());
                    break;
            }
        }

    }*/

    private class LessonElement extends JFXListCell<Lesson> {

        private LessonElement() {
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !isEmpty()) {
                    try {
                        BaseWindow.Stage.setScene(new Scene(new ViewSubject(((LessonElement) event.getSource()).getItem().getSubject())));
                    }catch(Exception e) {
                        BaseWindow.Stage.setScene(new Scene(new ViewSchedule()));
                    }

                }
            });
        }


        @Override
        protected void updateItem(Lesson item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty && item != null) setText(item.getSubject().getName());
            else if (!empty && item == null) setText("");
        }

    }
}