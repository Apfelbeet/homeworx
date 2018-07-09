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
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.util.Callback;
import logic.Day;
import logic.Lesson;
import logic.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainWindow extends Application {
    @FXML
    private ProgressBar hallo;

    @FXML
    private TableView subjectGrid;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("MainWindow.fxml")));

        Scene scene = new Scene(root, 500, 250);

        stage.setTitle("HomeworX: " + System.getProperty("user.dir"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void initialize(){
        subjectGrid.getColumns().add(new TableColumn("Montag"));
        ObservableList<Lesson> xmpleList = FXCollections.observableArrayList(new ArrayList<Lesson>());
        TableView<Lesson> tableView = new TableView<Lesson>(xmpleList);

        TableColumn<Lesson, String> mondayColumn = new TableColumn("Monday");

        mondayColumn.setCellValueFactory(p -> {
            // p.getValue() returns the Person instance for a particular TableView row
            return new ReadOnlyStringWrapper(p.getValue().getSubject().getName());
        });


    }

}
