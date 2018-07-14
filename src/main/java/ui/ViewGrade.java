package ui;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.DataManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import logic.Grade;
import logic.GradeType;
import logic.Subject;

import java.io.IOException;

public class ViewGrade extends Pane {

    private Subject subject;
    private boolean onEdit;

    //@FXML private
    @FXML private Label subject_name;
    @FXML private ImageView back_button;
    @FXML private ImageView add_button;
    @FXML private ImageView edit_button;
    @FXML private Label grade_average;
    @FXML private JFXTreeTableView<GradeElement> table;

    public ViewGrade(Subject subject) {
        this.subject = subject;
        onEdit = false;

        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("ViewGrade.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        subject_name.setText(subject.getName());
        grade_average.setText(String.format("%fØ", ((float) Math.round(subject.calculateAverage() * 100) / 100)));

        back_button.setOnMouseClicked(event -> {

            back();
        });


        add_button.setOnMouseClicked(event -> {

            BaseWindow.Stage.setScene(new Scene(new CreateGrade(subject)));
        });

        edit_button.setOnMouseClicked(event -> {
            if(!onEdit) {
                edit_button.setScaleY(1.2);
                edit_button.setScaleX(1.2);
                edit_button.setImage(new Image(ClassLoader.getSystemClassLoader().getResource("icons/baseline_done_black_18dp.png").toString()));
                onEdit = true;
                back_button.setDisable(true);
                add_button.setDisable(true);
                table.setEditable(true);
            }else {
                edit_button.setScaleY(1);
                edit_button.setScaleX(1);
                edit_button.setImage(new Image(ClassLoader.getSystemClassLoader().getResource("icons/baseline_edit_black_18dp.png").toString()));
                onEdit = false;
                back_button.setDisable(false);
                add_button.setDisable(false);
                table.setEditable(false);
                for(int i = 0; i < table.getCurrentItemsCount(); i++) {
                    //table.getSelectionModel().getSelectedCells().get(0).getTreeTableView().setEditable(false);
                    Grade grade = table.getTreeItem(i).getValue().getGrade();
                    grade.setValue(Integer.parseInt(table.getTreeItem(i).getValue().getValue().get()));
                    grade.setGradeType(GradeType.valueOf(table.getTreeItem(i).getValue().getType().get()));
                    DataManager.saveGrade(subject, grade);
                }
            }
        });

        Callback<TreeTableColumn<GradeElement, String>, TreeTableCell<GradeElement, String>> tableFactory = param -> {
            GenericEditableTreeTableCell<GradeElement, String> gt = new GenericEditableTreeTableCell<>();
            gt.setFont(new Font("Roboto", 15.0));
            gt.setPadding(new Insets(5));
            return gt;
        };

        Callback<TreeTableColumn<GradeElement, String>, TreeTableCell<GradeElement, String>> deleteFactory = param -> {
            GenericEditableTreeTableCell<GradeElement, String> gt = new GenericEditableTreeTableCell<>();
            gt.setFont(new Font("Roboto", 10.0));
            gt.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !onEdit) {
                    Grade gradeTemp = table.getSelectionModel().getSelectedCells().get(0).getTreeItem().getValue().getGrade();
                    subject.getGrades().remove(gradeTemp);
                    ObservableList<GradeElement> gradeElements = FXCollections.observableArrayList();
                    subject.getGrades().forEach((grade -> gradeElements.add(new GradeElement(grade))));
                    TreeItem<GradeElement> root = new RecursiveTreeItem<GradeElement>(gradeElements, RecursiveTreeObject::getChildren);
                    table.setRoot(root);
                    DataManager.deleteGrade(subject, gradeTemp);
                }
            });
            gt.setPadding(new Insets(5));
            return gt;
        };

        JFXTreeTableColumn<GradeElement, String> gradeType = new JFXTreeTableColumn<>("Noten Art");
        gradeType.setCellValueFactory(param -> param.getValue().getValue().getType());
        gradeType.setCellFactory(tableFactory);
        //gradeType.setEditable(false);
        gradeType.setCellValueFactory((TreeTableColumn.CellDataFeatures<GradeElement, String> param) ->{
            if(gradeType.validateValue(param)) return param.getValue().getValue().getType();
            else return gradeType.getComputedValue(param);
        });

        JFXTreeTableColumn<GradeElement, String> value = new JFXTreeTableColumn<>("Note");
        //value.setEditable(false);
        value.setCellFactory(tableFactory);
        value.setCellValueFactory((TreeTableColumn.CellDataFeatures<GradeElement, String> param) ->{
            if(value.validateValue(param)) return param.getValue().getValue().getValue();
            else return value.getComputedValue(param);
        });

        JFXTreeTableColumn<GradeElement, String> delete = new JFXTreeTableColumn<>("Löschen");
        delete.setEditable(false);
        delete.setCellFactory(deleteFactory);
        delete.setCellValueFactory((TreeTableColumn.CellDataFeatures<GradeElement, String> param) -> new SimpleStringProperty("Löschen"));


        ObservableList<GradeElement> gradeElements = FXCollections.observableArrayList();
        subject.getGrades().forEach((grade -> gradeElements.add(new GradeElement(grade))));
        TreeItem<GradeElement> root = new RecursiveTreeItem<GradeElement>(gradeElements, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setFixedCellSize(30.);
        table.showRootProperty().setValue(false);
        table.setShowRoot(false);
        table.setEditable(false);
        table.getColumns().setAll(gradeType, value, delete);


    }

    private void back() {
        BaseWindow.Stage.setScene(new Scene(new ViewSubject(subject)));
    }

    private class GradeElement extends RecursiveTreeObject<GradeElement> {
        private StringProperty type;
        private StringProperty value;
        private Grade grade;

        public GradeElement(Grade grade) {
            this.grade = grade;
            this.type = new SimpleStringProperty(grade.getGradeType().name()) ;
            this.value = new SimpleStringProperty(String.valueOf(grade.getValue()));
        }

        public StringProperty getType() {
            return type;
        }

        public StringProperty getValue() {
            return value;
        }

        public Grade getGrade() {
            return grade;
        }
    }

}
