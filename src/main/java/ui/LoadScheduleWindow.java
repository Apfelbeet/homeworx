package ui;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import javafx.*;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * BUG: Schedules List View not Updating
 */
public class LoadScheduleWindow extends Application{
    @FXML public ListView schedulesListView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoadSchedule.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadSchedule.fxml"));
        loader.setController(this);
        Scene scene = new Scene(root, 500, 250);

        primaryStage.setTitle("Hello World! CurDir = " + System.getProperty("user.dir"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();

        schedulesListView.getItems().add("oof");
    }
}
