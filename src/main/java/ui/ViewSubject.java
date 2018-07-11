package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import logic.Subject;

public class ViewSubject extends Pane {
    private Subject subject;

    @FXML
    private Label subject_name;

    public ViewSubject(Subject subject) {
        this.subject = subject;
        subject_name.setText(subject.getName());
    }


    @FXML
    public void onClick() {
        subject_name.setText("Hallo");
    }
}
