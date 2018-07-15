package ui;

import com.jfoenix.controls.JFXListView;
import data.DataManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import logic.Homework;
import logic.Lesson;
import logic.Schedule;
import logic.Subject;

import java.io.IOException;
import java.util.ArrayList;

public class ViewSubject extends Pane {
    private Subject subject;
    private boolean onEdit;

    @FXML private TextField subject_name;
    @FXML private GridPane basic_pane;
    @FXML private GridPane top_pane;
    @FXML private Label homework_label;
    @FXML private JFXListView<Homework> homework_list;
    @FXML private Pane top_bar;
    @FXML private JFXListView<String> term_list;
    @FXML private Label grade_label;
    @FXML private Label lesson_label;
    @FXML private TextField subject_short;
    @FXML private ImageView back_button;
    @FXML private ImageView edit_name;
    @FXML private ImageView add_homework;
    @FXML private ImageView add_lesson;
    @FXML private ImageView edit_grades;


    public ViewSubject(Subject subject) {

        //Initialise FXML-File
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("ViewSubject.fxml"));
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
        subject_name.selectRange(0,0);
        onEdit = false;
        subject_name.setText(subject.getName());
        subject_short.setText(String.format("%s", subject.getShortName()));
        homework_label.setText(String.format("%d %s", subject.getHomework().size(), subject.getHomework().size() != 1 ? "Hausaufgaben" : "Hausaufgabe"));
        grade_label.setText(String.format("Notendurchschnitt: %sØ",  ((float) Math.round(subject.calculateAverage() * 100) / 100)));

        //Homework ListView
        ArrayList<Homework> homework = new ArrayList<>();
        ObservableList<Homework> homeworkList = FXCollections.observableList(subject.getHomework());
        homework_list.setItems(homeworkList);
        homework_list.setCellFactory(listView -> new HomeworkCell());

        //Lesson ListView
        ArrayList<String> lessons = new ArrayList<>();
        Schedule.schedule.getDays().forEach((key, value) -> {
            int index = 0;
            for(Lesson lesson: value.getLessons()) {
                if(lesson != null && lesson.getSubject().equals(subject)) lessons.add(String.format("%s: %d.%s Stunde", key.name(), index +1, lesson.getLength() > 1 ? String.format("- %d.", index +1 + lesson.getLength()-1) : ""));
                index++;
            }
        });
        ObservableList<String> lessonItems = FXCollections.observableList(lessons);
        term_list.setItems(lessonItems);
        lesson_label.setText(String.format("%d %s", lessons.size(), lessons.size() != 1 ? "Unterrichtstermine" : "Unterrichtstermin"));

        back_button.setOnMouseClicked(event -> {
            //TODO
        });

        edit_name.setOnMouseClicked(event -> {
            if(!onEdit) {
                onEdit = true;
                edit_name.setScaleX(1.2);
                edit_name.setScaleY(1.2);
                subject_name.setEditable(true);
                subject_short.setEditable(true);
                subject_name.selectAll();
                edit_name.setImage(new Image(ClassLoader.getSystemClassLoader().getResource("icons/baseline_done_black_18dp.png").toString()));
            }else {
                onEdit = false;
                edit_name.setScaleY(1);
                edit_name.setScaleY(1);
                subject_name.setEditable(false);
                subject_short.setEditable(false);
                subject.setName(subject_name.getText());
                subject.setShortName(subject_short.getText());
                DataManager.saveSubjects(subject);
                edit_name.setImage(new Image(ClassLoader.getSystemClassLoader().getResource("icons/baseline_edit_black_18dp.png").toString()));
            }
        });

        add_homework.setOnMouseClicked(event -> {
            //TODO
        });

        add_lesson.setOnMouseClicked(event -> {
            BaseWindow.Stage.setScene(new Scene(new CreateLesson(subject)));
        });

        edit_grades.setOnMouseClicked(event -> {
            BaseWindow.Stage.setScene(new Scene(new ViewGrade(subject)));
        });




    }

    private class HomeworkCell extends ListCell<Homework> {

        public HomeworkCell() {
            setOnMouseClicked(onClick);
        }

        @Override
        protected void updateItem(Homework item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty) setText(String.format("%s: %s",DataManager.DATE_FORMAT.format(item.getDeadline().getTime()), item.getDescription().length() <= 30 ? item.getDescription() : item.getDescription().substring(0, 30) + " ..."));
        }

        private EventHandler<MouseEvent> onClick = event -> {
            if (event.getClickCount() == 2 && !isEmpty()) {
                BaseWindow.Stage.setScene(new Scene(new ViewHomework(((HomeworkCell) event.getSource()).getItem(), subject)));
            }
        };


    }


}
