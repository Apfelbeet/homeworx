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
import logic.Subject;

import javax.xml.soap.Text;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ViewHomework extends Pane {

    private Homework homework;
    private Subject subject;
    private boolean onEdit;

    //@FXML private
    @FXML private JFXTextArea description_field;
    @FXML private JFXDatePicker deadline;
    @FXML private Label priority_label;
    @FXML private Label subject_name;
    @FXML private ImageView back_button;
    @FXML private ImageView delete_button;
    @FXML private ImageView edit_button;
    @FXML private TextField priority_field;

    public ViewHomework(Homework homework, Subject subject) {
        this.homework = homework;
        this.subject = subject;
        onEdit = false;

        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("ViewHomework.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        Calendar c = homework.getDeadline();
        deadline.setValue(LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)));
        deadline.setEditable(false);

        subject_name.setText(subject.getName());
        priority_field.setText(String.valueOf(homework.getPriority()));
        description_field.setText(homework.getDescription());

        back_button.setOnMouseClicked(event -> {
            GregorianCalendar calendar =  new GregorianCalendar();
            calendar.setTime(Date.from(deadline.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            homework.setDeadline(calendar);
            DataManager.saveHomework(subject, homework);
            back();
        });

        delete_button.setOnMouseClicked(event -> {
            subject.getHomework().remove(homework);
            DataManager.deleteHomework(subject, homework);
            back();
        });

        edit_button.setOnMouseClicked(event -> {
            if(!onEdit) {
                onEdit = true;
                edit_button.setScaleX(1.2);
                edit_button.setScaleY(1.2);
                description_field.setEditable(true);
                priority_field.setEditable(true);
                deadline.setEditable(true);
                //subject_short.setEditable(true);
                //subject_name.selectAll();
                edit_button.setImage(new Image(ClassLoader.getSystemClassLoader().getResource("icons/baseline_done_black_18dp.png").toString()));
            }else {
                onEdit = false;
                edit_button.setScaleY(1);
                edit_button.setScaleY(1);
                description_field.setEditable(false);
                deadline.setEditable(false);
                priority_field.setEditable(false);
                homework.setDescription(description_field.getText());
                homework.setPriority(Integer.parseInt(priority_field.getText()));
                GregorianCalendar calendar =  new GregorianCalendar();
                calendar.setTime(Date.from(deadline.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                homework.setDeadline(calendar);
                DataManager.saveHomework(subject, homework);
                edit_button.setImage(new Image(ClassLoader.getSystemClassLoader().getResource("icons/baseline_edit_black_18dp.png").toString()));
            }
        });
    }

    private void back() {
        BaseWindow.Stage.setScene(new Scene(new ViewSubject(subject)));
    }

}
