package ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.skins.JFXComboBoxListViewSkin;
import data.DataManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.cell.ComboBoxTreeTableCell;
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
    @FXML
    private Label subject_name;
    @FXML
    private ImageView back_button;
    @FXML
    private ImageView add_button;
    @FXML
    private ImageView edit_button;
    @FXML
    private Label grade_average;
    @FXML
    private JFXTreeTableView<GradeElement> table;

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
            if (!onEdit) {
                edit_button.setScaleY(1.2);
                edit_button.setScaleX(1.2);
                edit_button.setImage(new Image(ClassLoader.getSystemClassLoader().getResource("icons/baseline_done_black_18dp.png").toString()));
                onEdit = true;
                back_button.setDisable(true);
                add_button.setDisable(true);
                table.setEditable(true);
            } else {
                edit_button.setScaleY(1);
                edit_button.setScaleX(1);
                edit_button.setImage(new Image(ClassLoader.getSystemClassLoader().getResource("icons/baseline_edit_black_18dp.png").toString()));
                onEdit = false;
                back_button.setDisable(false);
                add_button.setDisable(false);
                table.setEditable(false);
                for (int i = 0; i < table.getCurrentItemsCount(); i++) {
                    //table.getSelectionModel().getSelectedCells().get(0).getTreeTableView().setEditable(false);
                    Grade grade = table.getTreeItem(i).getValue().getGrade();
                    grade.setValue(Integer.parseInt(table.getTreeItem(i).getValue().getValue().getValue()));
                    grade.setGradeType(GradeType.valueOf(table.getTreeItem(i).getValue().getType().getValue()));
                    DataManager.saveGrade(subject, grade);
                }
            }
        });

        Callback<TreeTableColumn.CellDataFeatures<GradeElement, GradeType>, ObservableValue<GradeType>> typeFactory = param -> {
            TreeItem<GradeElement> treeItem = param.getValue();
            GradeElement type = treeItem.getValue();

            return new SimpleObjectProperty<>(type.getGrade().getGradeType());
        };

        Callback<TreeTableColumn.CellDataFeatures<GradeElement, Integer>, ObservableValue<Integer>> valueFactory = param -> {
            TreeItem<GradeElement> treeItem = param.getValue();
            GradeElement type = treeItem.getValue();
            return new SimpleObjectProperty<>(type.getGrade().getValue());
        };

        Callback<TreeTableColumn<GradeElement, String>, TreeTableCell<GradeElement, String>> tableFactory = param -> {
            GenericEditableTreeTableCell<GradeElement, String> gt = new GenericEditableTreeTableCell<>();
            gt.setFont(new Font("Roboto", 10.0));
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

        JFXTreeTableColumn<GradeElement, GradeType> gradeType = new JFXTreeTableColumn<>("Noten Art");
        gradeType.setCellValueFactory(typeFactory);
        //gradeType.setCellFactory(tableFactory);
        //gradeType.setEditable(false);
        //gradeType.setCellValueFactory((TreeTableColumn.CellDataFeatures<GradeElement, GradeType> param) -> {
        //    if (gradeType.validateValue(param)) return param.getValue().getValue().getGrade().getGradeType();
        //    else return gradeType.getComputedValue(param);
        //});
        ObservableList<GradeType> typeList = FXCollections.observableArrayList(GradeType.values());
        gradeType.setCellFactory(JFXComboBoxTreeTableCell.forTreeTableColumn(typeList));
        gradeType.setOnEditCommit(event -> {
            event.getRowValue().getValue().setType(event.getNewValue().toString());
        });


        JFXTreeTableColumn<GradeElement, Integer> value = new JFXTreeTableColumn<>("Note");
        //value.setEditable(false);
        value.setCellValueFactory(valueFactory);
        ObservableList<Integer> valueList = FXCollections.observableList(Grade.VALUES);
        value.setCellFactory(JFXComboBoxTreeTableCell.forTreeTableColumn(valueList));
        value.setOnEditCommit(event -> {
            event.getRowValue().getValue().setValue(event.getNewValue().toString());
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
            this.type = new SimpleStringProperty(grade.getGradeType().name());
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

        public void setValue(String value) {
            this.value.set(value);
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
        }

        public void setType(String type) {
            this.type.set(type);
        }
    }


}
