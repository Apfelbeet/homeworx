package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import data.DataManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import logic.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;

public class CreateLesson extends Pane {
    @FXML
    JFXComboBox<Integer> hourTextField;
    @FXML
    JFXTextField lengthTextField;

    @FXML
    JFXComboBox subjectComboBox;
    @FXML
    JFXComboBox dayComboBox;

    @FXML
    JFXButton saveButton;
    @FXML JFXButton backButton;

    private Subject subject;

    public CreateLesson(Subject subject) {
        this.subject = subject;
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("CreateLesson.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setTextFieldIntegersOnly(lengthTextField);

        dayComboBox.getItems().clear();
        dayComboBox.setItems(FXCollections.observableArrayList(Day.values()));


        ArrayList<Subject> subjects = Schedule.schedule.getSubjects();
        subjectComboBox.getItems().clear();
        subjectComboBox.setItems(FXCollections.observableArrayList(subjects));

        hourTextField.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11));

        backButton.setOnAction(event -> {
            back();
        });

        saveButton.setOnAction(event -> {
            Lesson l = new Lesson(Integer.parseInt(lengthTextField.getText()), (Subject)subjectComboBox.getValue());
            SchoolDay day = Schedule.schedule.getDays().get(Day.valueOf(dayComboBox.getValue().toString()));

            day.setLesson(hourTextField.getValue() -1, l);
            DataManager.saveLesson(l, Day.valueOf(dayComboBox.getValue().toString()), day);
            back();
        });

    }

    private void back() {
        BaseWindow.Stage.setScene(new Scene(new ViewSubject(subject)));
    }

    /*@Override
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
    }*/

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
