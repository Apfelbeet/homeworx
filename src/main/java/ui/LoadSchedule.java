package ui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.File;
import java.net.URL;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.nio.file.Files;

public class LoadSchedule extends Application {
    @FXML
    private ListView schedulesListView;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoadSchedule.fxml"));

        Scene scene = new Scene(root, 500, 250);

        stage.setTitle("FXML Welcome Location: " + System.getProperty("user.dir"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void initialize(){
        File schedulesDirectory = new File(System.getProperty("user.dir").toString() + "\\schedules\\");
        if(schedulesDirectory.isDirectory()){
            File[] files = schedulesDirectory.listFiles();
            if(files.length > 1){
                for(File f : files){
                    schedulesListView.getItems().add(f.getName());
                }
            } else {
                // TODO: Choose Schedule automatically
            }

        } else {
            // TODO: Create Folder and new Schedule
        }

    }

}
