package sample.coursesAddEditDialog;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.DataModel.DataSource;
import sample.DataModel.Tables.Courses;
import sample.DataModel.Views.ListOfAllCourses;

import java.util.Random;

public class AddEditDialogCoursesController {

    private Random random = new Random();

    @FXML
    private TextField courseNameField;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private TextField courseAttributeField;

    @FXML
    private void generateCourseAttribute(ActionEvent event){

        if(choiceBox.getSelectionModel().getSelectedIndex() == 0){
            courseAttributeField.setText("SELECT MAJOR");
        } else {
            int courseAttributeNumber = random.nextInt(498) + 1;
            courseAttributeField.setText(DataSource.getInstance().getDepartmentPrefixValue(
                    choiceBox.getSelectionModel().getSelectedIndex() + 1) + courseAttributeNumber);
        }
    }

    public void initialize(){
        choiceBox.setItems(FXCollections.observableArrayList(DataSource.getInstance().queryMajorsView()));
        choiceBox.getSelectionModel().selectFirst();
    }

    public Courses getNewCourse(){
        if(courseNameField.getText().trim().equalsIgnoreCase("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enter Course Name!");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the name of the course!");
            alert.showAndWait();
            return null;
        }

        if(choiceBox.getValue().equals("Undeclared")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Course can not belong to Undeclared Major!");
            alert.setHeaderText(null);
            alert.setContentText("Please select valid Major your course belongs to!");
            alert.showAndWait();
            return null;
        }

        Courses newEntry = new Courses();
        newEntry.setCourseName(courseNameField.getText());
        newEntry.setCourseAttribute(courseAttributeField.getText());
        newEntry.setMajor_id(Integer.toString(choiceBox.getSelectionModel().getSelectedIndex() + 1));
        return newEntry;
    }

    public Courses editSelectedCourse(ListOfAllCourses recordToEdit){
        if(choiceBox.getValue().equals("Undeclared")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Course can not belong to Undeclared Major!");
            alert.setHeaderText(null);
            alert.setContentText("Please select valid Major your course belongs to!");
            alert.showAndWait();
            return null;
        }

        Courses updatedEntry = new Courses();
        updatedEntry.setCourse_id(recordToEdit.getCourse_id());
        updatedEntry.setCourseName(courseNameField.getText());
        updatedEntry.setMajor_id(Integer.toString(choiceBox.getSelectionModel().getSelectedIndex() + 1));
        updatedEntry.setCourseAttribute(courseAttributeField.getText());

        return updatedEntry;
    }

    public void prePopulateEditingForm(ListOfAllCourses recordToEdit){
        courseNameField.setText(recordToEdit.getCourseName());

        int indexOfDepartment = choiceBox.getItems().indexOf(recordToEdit.getMajor());
        choiceBox.getSelectionModel().select(indexOfDepartment);

        courseAttributeField.setText(recordToEdit.getCourseAttribute());
    }

}
