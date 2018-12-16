package sample.studentAddEditDialog;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.DataModel.DataSource;
import sample.DataModel.Tables.Students;
import sample.DataModel.Views.FinalStudentsView;

public class AddEditDialogStudentController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private TextField gpaField;
    private Double gpaFieldDouble;

    public void initialize(){
        choiceBox.setItems(FXCollections.observableArrayList(DataSource.getInstance().queryMajorsView()));
        choiceBox.getSelectionModel().selectFirst();
    }

    public Students getNewStudent(){
        if(firstNameField.getText().trim().equalsIgnoreCase("") ||
                lastNameField.getText().trim().equalsIgnoreCase("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enter First and Last Name!");
            alert.setHeaderText(null);
            alert.setContentText("Please enter student's first and last name");
            alert.showAndWait();
            return null;
        }

        try{
            if(!gpaField.getText().trim().equalsIgnoreCase("")) {
                gpaFieldDouble = Double.parseDouble(gpaField.getText());
                if(gpaFieldDouble < 0.0 || gpaFieldDouble > 4.0){
                    throw new Exception();
                }
                else {
                    gpaFieldDouble = 0d;
                }
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Check input in GPA field");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid student's GPA");
            alert.showAndWait();
            return null;
        }

        Students newEntry = new Students();
        newEntry.setFirstName(firstNameField.getText());
        newEntry.setLastName(lastNameField.getText());
        newEntry.setMajor_id(Integer.toString(choiceBox.getSelectionModel().getSelectedIndex() + 1));
        newEntry.setGpa(gpaField.getText().length() > 0 ? gpaField.getText() : "0.0");

        return newEntry;
    }

    public Students editSelectedStudent(FinalStudentsView recordToEdit){
        Students updatedEntry = new Students();
        updatedEntry.set_id(recordToEdit.getid());
        updatedEntry.setFirstName(firstNameField.getText());
        updatedEntry.setLastName(lastNameField.getText());
        updatedEntry.setMajor_id(Integer.toString(choiceBox.getSelectionModel().getSelectedIndex() + 1));
        updatedEntry.setGpa(gpaField.getText().length() > 0 ? gpaField.getText() : "0.0");

        return updatedEntry;
    }

    public void prePopulateEditingForm(FinalStudentsView recordToEdit){
        firstNameField.setText(recordToEdit.getFullName().split(" ")[0]);
        lastNameField.setText(recordToEdit.getFullName().split(" ")[1]);

        int indexOfMajor = choiceBox.getItems().indexOf(recordToEdit.getMajor());
        choiceBox.getSelectionModel().select(indexOfMajor);

        gpaField.setText(recordToEdit.getGpa());
    }

}
