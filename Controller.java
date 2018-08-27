package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.datamodel.Datasource;
import sample.datamodel.View;
import sample.tabRecord.RecordController;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainPanel;

    @FXML
    private TableView<View> contactsTable;

    @FXML
    private Label counter;

    private ObservableList<View> listOfRecords;

    Datasource data = Datasource.getInstance();

    public void initialize(){
        data.loadRecords();
        contactsTable.setItems(data.getRecords());
    }

    public void listRecords(){
        Task<ObservableList<View>> task = new GetAllRecordsTask();
        contactsTable.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();

        /**
         * This should be computed value.
         */
        counter.setText("3");
    }

    @FXML
    public void showAddEntryDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("tabRecord/recordDialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Could Not Load Dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            RecordController recordController = fxmlLoader.getController();

            /**
             * Implement that
             */
            View newEntry = recordController.getNewEntry();
            data.addNewEntry(newEntry);
        }
    }

    @FXML
    public void showEditEntryDialog(){
        View selectedEntry = contactsTable.getSelectionModel().getSelectedItem();

        if(selectedEntry == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to edit");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("tabRecord/recordDialog.fxml"));

        //Note, this is the same code that was on line 40.
        //Could create new method instead
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println("Could Not Load");
            System.out.println(e.getStackTrace());
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        RecordController recordController = fxmlLoader.getController();

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            recordController.editExistingEntry(selectedEntry);
        }
    }

    @FXML
    public void deleteEntry(){
        //get selected contact
        View selectedEntry = contactsTable.getSelectionModel().getSelectedItem();
        if(selectedEntry == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a contact you want to delete");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");

        //if not null, looks ugly
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected contact: " + selectedEntry.getFullName());
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            data.deleteExistingEntry(selectedEntry);
        }
    }

    @FXML
    public void showAboutDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("About");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("tabAbout/aboutDialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Could Not Load Dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        Optional<ButtonType> result = dialog.showAndWait();
    }
}

class GetAllRecordsTask extends Task{

    @Override
    protected ObservableList<View> call() {
        return FXCollections.observableArrayList(Datasource.getInstance().queryView());
    }
}






















