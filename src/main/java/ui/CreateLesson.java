package ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import data.DataManager;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.*;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Objects;

public class CreateLesson extends Application{
    @FXML
    JFXTextField hourTextField;
    @FXML
    JFXTextField lengthTextField;

    @FXML
    JFXComboBox subjectComboBox;
    @FXML
    JFXComboBox dayComboBox;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("CreateLesson.fxml")));

        Scene scene = new Scene(root);

        stage.setTitle("Create Lesson");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @FXML
    public void initialize() {
        setTextFieldIntegersOnly(hourTextField);
        setTextFieldIntegersOnly(lengthTextField);

        dayComboBox.getItems().clear();
        dayComboBox.setItems(FXCollections.observableArrayList(Day.values()));


        ArrayList<Subject> subjects = Schedule.schedule.getSubjects();
        subjectComboBox.getItems().clear();
        subjectComboBox.setItems(FXCollections.observableArrayList(subjects));
    }


    public void saveButton_Action(){
        Lesson l = new Lesson(Integer.parseInt(lengthTextField.getText()), (Subject)subjectComboBox.getValue());
        SchoolDay day = Schedule.schedule.getDays().get(Day.valueOf((String)dayComboBox.getValue()));

        day.setLesson(Integer.parseInt(hourTextField.getText()), l);
        DataManager.saveLesson(l, Day.valueOf((String)dayComboBox.getValue()), day);
    }

    /**
     * Configures the hourTextField-Control to only accept integers as input.
     */
    public void setTextFieldIntegersOnly(JFXTextField field){
        DecimalFormat format = new DecimalFormat( "#.0" );

        field.setTextFormatter( new TextFormatter<>(c ->
        {
            if ( c.getControlNewText().isEmpty() )
            {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
            {
                return null;
            }
            else
            {
                return c;
            }
        }));
    }
}
