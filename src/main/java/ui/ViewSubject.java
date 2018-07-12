package ui;

import com.jfoenix.controls.JFXListView;
import data.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import logic.Lesson;
import logic.Schedule;
import logic.Subject;

import java.io.IOException;
import java.util.ArrayList;

public class ViewSubject extends Pane {
    private Subject subject;
    private Scene parent;


    @FXML private Label subject_name;
    @FXML private GridPane basic_pane;
    @FXML private GridPane top_pane;
    @FXML private Label homework_label;
    @FXML private JFXListView<String> homework_list;
    @FXML private Pane top_bar;
    @FXML private JFXListView<String> term_list;
    @FXML private Label grade_label;
    @FXML private Label lesson_label;


    public ViewSubject(Subject subject, Scene parent) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("ViewSubject.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.subject = subject;
        this.parent = parent;
        subject_name.setText(subject.getName());
        homework_label.setText(String.format("%d %s", subject.getHomework().size(), subject.getHomework().size() != 1 ? "Hausaufgaben" : "Hausaufgabe"));
        ArrayList<String> homeworkString = new ArrayList<>();
        subject.getHomework().stream().forEach((e -> homeworkString.add(String.format("%s: %s",DataManager.DATE_FORMAT.format(e.getDeadline().getTime()), e.getDescription()))));
        ObservableList<String> homework = FXCollections.observableList(homeworkString);
        homework_list.setItems(homework);

        ArrayList<String> lessons = new ArrayList<>();
        Schedule.schedule.getDays().forEach((key, value) -> {
            int index = 0;
            for(Lesson lesson: value.getLessons()) {
                if(lesson != null && lesson.getSubject().equals(subject)) lessons.add(String.format("%s: %d.%s Stunde", key.name(), index +1, lesson.getLength() > 1 ? String.format("- %d.", index +1 + lesson.getLength()) : ""));
                index++;
            }
        });
        ObservableList<String> lessonItems = FXCollections.observableList(lessons);
        term_list.setItems(lessonItems);
        lesson_label.setText(String.format("%d %s", lessons.size(), lessons.size() != 1 ? "Unterrichtstermine" : "Unterrichtstermin"));

        grade_label.setText(String.format("Notendurchschnitt: %sØ",  ((float) Math.round(subject.calculateAverage() * 100) / 100)));
    }


}
