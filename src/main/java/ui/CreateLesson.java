package ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import logic.Day;
import logic.Schedule;
import logic.Subject;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Objects;

public class CreateLesson extends Application{
    @FXML
    JFXTextField hourTextField;

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
    public void initialize(){
        setHourTextFieldIntegersOnly();

        dayComboBox.getItems().clear();
        dayComboBox.setItems(FXCollections.observableArrayList(Day.values()));


        ArrayList<Subject> subjects = Schedule.schedule.getSubjects();
        subjectComboBox.getItems().clear();
        subjectComboBox.setItems(FXCollections.observableArrayList(subjects));

    }



    public void saveChanges() {

    }

    public void chooseDay(){

    }

    public void chooseSubject(){

    }

    public void chooseLesson(){

    }

    /**
     * Configures the hourTextField-Control to only accept integers as input.
     */
    public void setHourTextFieldIntegersOnly(){
        DecimalFormat format = new DecimalFormat( "#.0" );

        hourTextField.setTextFormatter( new TextFormatter<>(c ->
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
