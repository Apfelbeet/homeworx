package ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.File;
import java.util.Objects;

public class LoadSchedule extends Application {
    @FXML
    private ListView schedulesListView;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("LoadSchedule.fxml")));

        Scene scene = new Scene(root, 500, 250);

        stage.setTitle("FXML Welcome Location: " + System.getProperty("user.dir"));
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @FXML
    public void initialize(){

        File schedulesDirectory = new File(System.getProperty("user.dir").toString() + "\\schedules\\");
        if(schedulesDirectory.isDirectory()){
            File[] files = schedulesDirectory.listFiles();
            if(files.length >= 1){
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
