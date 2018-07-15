package ui;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import data.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.Homework;
import logic.Schedule;
import logic.Subject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateHomework extends Pane {

    private Subject subject;

    //@FXML private
    @FXML
    private JFXTextArea description_field;
    @FXML
    private JFXDatePicker deadline;
    @FXML
    private Label priority_label;
    @FXML
    private Label subject_name;
    @FXML
    private ImageView back_button;
    @FXML
    private ImageView add_button;
    @FXML
    private TextField priority_field;

    public CreateHomework(Subject subject) {
        this.subject = subject;

        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("CreateHomework.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        Calendar c = GregorianCalendar.getInstance();
        deadline.setValue(LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)));
        deadline.setEditable(false);

        subject_name.setText(subject.getName());

        back_button.setOnMouseClicked(event -> {
            back();
        });

        add_button.setOnMouseClicked(event -> {

            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(Date.from(deadline.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            Homework homework = new Homework(description_field.getText(), calendar, Integer.parseInt(priority_field.getText()));
            subject.getHomework().add(homework);
            DataManager.saveHomework(subject, homework);
            back();
        });
    }

    private void back() {
        BaseWindow.Stage.setScene(new Scene(new ViewSubject(subject)));
    }

}
