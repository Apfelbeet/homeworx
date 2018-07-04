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
    // importieren der Libarys für die GUI

public class LoadSchedule extends Application {
    @FXML
    private ListView schedulesListView;

    /** Fenster Informationen werden festgelegt
     *  Z.B die Fenstergröße, Titel, verzerrbar
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("LoadSchedule.fxml")));

        Scene scene = new Scene(root, 500, 250);

        stage.setTitle("FXML Welcome Location: " + System.getProperty("user.dir"));
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    /** Fenster wird erzeugt und Methoden ausgeführt
     * Stundenpläne werden geladen --> nur 1 wird automatisch ausgewählt / mehrere Auswahlfenster öffnet sich
     *                             --> kein Stundenplan vorhanden --> neuer Stundenplan wird erstellt
     */
    @FXML
    public void initialize(){

        File schedulesDirectory = new File(System.getProperty("user.dir").toString() + "\\schedules\\");
        if(schedulesDirectory.isDirectory()){
            File[] files = schedulesDirectory.listFiles();
            if(files.length >= 1){
                for(File f : files){
                    schedulesListView.getItems().add(f.getName());
                }
            } else
                // TODO: Choose Schedule auto
            }

        } else {
            // TODO: Create Folder and new Schedule
        }

    }

}
