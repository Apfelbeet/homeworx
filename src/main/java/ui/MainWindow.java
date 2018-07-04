package ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.fxml.FXML;
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

        TableColumn<Lesson, String> mondayColumn = new TableColumn("Monday");
        /*
        mondayColumn.setCellValueFactory(
                cellData -> cellData.getValue().getSubject().getName();
        );
        */
    }

}
