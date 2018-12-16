package sample.MainStage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import sample.DataModel.DataSource;
import sample.DataModel.Tables.Courses;
import sample.DataModel.Tables.Students;
import sample.DataModel.Views.EnrollDropCourses;
import sample.DataModel.Views.FinalCoursesView;
import sample.DataModel.Views.FinalStudentsView;
import sample.DataModel.Views.ListOfAllCourses;
import sample.coursesAddEditDialog.AddEditDialogCoursesController;
import sample.studentAddEditDialog.AddEditDialogStudentController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class Controller{

    @FXML
    private BorderPane mainPanel;

    @FXML
    private TableView sampleTable;

    @FXML
    private TextField recordCounter;

    DataSource data = DataSource.getInstance();

    public void initialize(){  }

    public void showFinalStudentsView(){
        sampleTable.setPlaceholder(new Label("Loading data from database"));
        Task<ObservableList<FinalStudentsView>> task = new GetAllRecordsFromFinalStudentViewTask();
        setFinalStudentsViewColumns(task);
    }

    public void showFinalCoursesView(){
        sampleTable.setPlaceholder(new Label("Loading data from database"));
        Task<ObservableList<FinalCoursesView>> task = new GetAllRecordsFromFinalCourseViewTask();
        setFinalCoursesViewColumns(task);
    }

    public void showListOfAllCoursesView(){
        sampleTable.setPlaceholder(new Label("Loading data from database"));
        Task<ObservableList<ListOfAllCourses>> task = new GetAllRecordsFromListOfAllCoursesViewTask();
        setListOfAllCoursesViewColumns(task);
    }

    @FXML
    public void displayFinalStudentsView(){
        showFinalStudentsView();
    }

    @FXML
    public void displayListOfAllCoursesView(){
        showListOfAllCoursesView();
    }

    @FXML
    public void displayFinalCoursesView(){
        showFinalCoursesView();
    }

    @FXML
    public void showAddStudentEntryDialog(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../studentAddEditDialog/addEditDialog.fxml"));
        Dialog<ButtonType> dialog = createNewDialog("Add new Student", fxmlLoader);
        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            AddEditDialogStudentController addEditDialogStudentController = fxmlLoader.getController();
            Students newEntry = addEditDialogStudentController.getNewStudent();
            if(newEntry != null){
                data.addNewStudent(newEntry);
                showFinalStudentsView();
            }
        }
    }

    @FXML
    public void showEditStudentEntryDialog(){
        FinalStudentsView selectedEntry = (FinalStudentsView) sampleTable.getSelectionModel().getSelectedItem();
        if(selectedEntry == null){
            alertNoRecordSelected("No Record Selected", "Please select the student you want to EDIT");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../studentAddEditDialog/addEditDialog.fxml"));
        Dialog<ButtonType> dialog = createNewDialog("Edit Student's Info", fxmlLoader);

        AddEditDialogStudentController addEditDialogStudentController = fxmlLoader.getController();
        addEditDialogStudentController.prePopulateEditingForm(selectedEntry); //to pre-populate form
        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            data.editExistingStudent(addEditDialogStudentController.editSelectedStudent(selectedEntry));
            showFinalStudentsView();
        }
    }

    @FXML
    public void deleteStudentEntry(){
        FinalStudentsView selectedEntry = (FinalStudentsView) sampleTable.getSelectionModel().getSelectedItem();
        if(selectedEntry == null){
            alertNoRecordSelected("No Record Selected", "Please select the student you want to DELETE");
            return;
        }

        Optional<ButtonType> result = alertConfirmation("Delete Student",
                "Are you sure you want to delete the selected contact: " + selectedEntry.getFullName());

        if(result.isPresent() && result.get() == ButtonType.OK){
            data.deleteExistingStudent(selectedEntry);
            showFinalStudentsView();
        }
    }

    @FXML
    public void showAddStudentToCourse(){
        FinalStudentsView selectedEntry = (FinalStudentsView) sampleTable.getSelectionModel().getSelectedItem();

        if(selectedEntry == null){
            alertNoRecordSelected("No Record Selected", "Please select the student you want to ENROLL INTO a course");
            return;
        }

        sampleTable.getColumns().clear();
        sampleTable.setPlaceholder(new Label("Loading data from database"));
        List<EnrollDropCourses> list = data.getCoursesStudentNOTEnrolledIntoQuery(Integer.parseInt(selectedEntry.getid()));

        Task<ObservableList<EnrollDropCourses>> task = new Task<ObservableList<EnrollDropCourses>>() {
            @Override
            protected ObservableList<EnrollDropCourses> call() throws Exception {
                return FXCollections.observableArrayList(list);
            }
        };

        setEnrollDropCoursesViewColumns(task);
    }

    @FXML
    public void showDropStudentFromCourse(){
        FinalStudentsView selectedEntry = (FinalStudentsView) sampleTable.getSelectionModel().getSelectedItem();

        if(selectedEntry == null){
            alertNoRecordSelected("No Record Selected", "Please select the student you want to DROP FROM a course");
            return;
        }

        sampleTable.getColumns().clear();
        sampleTable.setPlaceholder(new Label("Loading data from database"));
        List<EnrollDropCourses> list = data.getCoursesStudentEnrolledIntoQuery(Integer.parseInt(selectedEntry.getid()));

        Task<ObservableList<EnrollDropCourses>> task = new Task<ObservableList<EnrollDropCourses>>() {
            @Override
            protected ObservableList<EnrollDropCourses> call() throws Exception {
                return FXCollections.observableArrayList(list);
            }
        };

        setEnrollDropCoursesViewColumns(task);
    }

    @FXML
    public void showAddCourseEntryDialog(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../coursesAddEditDialog/addEditDialog.fxml"));
        Dialog<ButtonType> dialog = createNewDialog("Add new Course", fxmlLoader);
        AddEditDialogCoursesController addEditDialogCoursesController = fxmlLoader.getController();
        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            Courses newEntry = addEditDialogCoursesController.getNewCourse();
            if(newEntry != null){
                data.addNewCourse(newEntry);
                showListOfAllCoursesView();
            }
        }

    }

    @FXML
    public void showEditCourseEntryDialog(){
        ListOfAllCourses selectedEntry = (ListOfAllCourses) sampleTable.getSelectionModel().getSelectedItem();
        if(selectedEntry == null){
            alertNoRecordSelected("No Record Selected", "Please select the course you want to EDIT");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../coursesAddEditDialog/addEditDialog.fxml"));
        Dialog<ButtonType> dialog = createNewDialog("Edit Course Info", fxmlLoader);

        AddEditDialogCoursesController addEditDialogStudentController = fxmlLoader.getController();
        addEditDialogStudentController.prePopulateEditingForm(selectedEntry); //to pre-populate form
        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            Courses recordToUpdate = addEditDialogStudentController.editSelectedCourse(selectedEntry);
            if(recordToUpdate != null) {
                data.editExistingCourse(addEditDialogStudentController.editSelectedCourse(selectedEntry));
                showListOfAllCoursesView();
            }
        }
    }

    @FXML
    public void deleteCourseEntry(){
        ListOfAllCourses selectedEntry = (ListOfAllCourses) sampleTable.getSelectionModel().getSelectedItem();
        if(selectedEntry == null){
            alertNoRecordSelected("No Record Selected", "Please select the course you want to DELETE");
            return;
        }

        Optional<ButtonType> result = alertConfirmation("Delete Course",
                "Are you sure you want to delete the selected course: " + selectedEntry.getCourseName());

        if(result.isPresent() && result.get() == ButtonType.OK){
            data.deleteExistingCourse(selectedEntry);
            showListOfAllCoursesView();
        }
    }

    private void alertNoRecordSelected(String title, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private Optional<ButtonType> alertConfirmation(String title, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }

    private Dialog<ButtonType> createNewDialog(String title, FXMLLoader fxmlLoader){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle(title);

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Could Not Load Dialog");
            e.printStackTrace();
            return null;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        return dialog;
    }

    private void setFinalStudentsViewColumns(Task<ObservableList<FinalStudentsView>> task){
        sampleTable.getColumns().clear();
        TableColumn id = new TableColumn("Student ID");
        id.setPrefWidth(133);
        id.setStyle( "-fx-alignment: CENTER;");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn fullName = new TableColumn("Full Name");
        fullName.setPrefWidth(133);
        fullName.setStyle( "-fx-alignment: CENTER;");
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn gpa = new TableColumn("GPA");
        gpa.setPrefWidth(133);
        gpa.setStyle( "-fx-alignment: CENTER;");
        gpa.setCellValueFactory(new PropertyValueFactory<>("gpa"));

        TableColumn major = new TableColumn("Major");
        major.setPrefWidth(133);
        major.setStyle( "-fx-alignment: CENTER;");
        major.setCellValueFactory(new PropertyValueFactory<>("major"));

        TableColumn department = new TableColumn("Department");
        department.setPrefWidth(133);
        department.setStyle( "-fx-alignment: CENTER;");
        department.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn departmentChair = new TableColumn("Department's Chair");
        departmentChair.setPrefWidth(133);
        departmentChair.setStyle( "-fx-alignment: CENTER;");
        departmentChair.setCellValueFactory(new PropertyValueFactory<>("departmentChair"));

        sampleTable.getColumns().addAll(id,fullName,gpa,major,department,departmentChair);

        sampleTable.itemsProperty().bind(task.valueProperty());
        Thread t = new Thread(task);
        t.start();

        try {
            t.join();
            recordCounter.setText(Integer.toString(task.get().size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void setFinalCoursesViewColumns(Task<ObservableList<FinalCoursesView>> task){
        sampleTable.getColumns().clear();

        TableColumn courseName = new TableColumn("Course Name");
        courseName.setPrefWidth(199);
        courseName.setStyle( "-fx-alignment: CENTER;");
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn major = new TableColumn("Major");
        major.setPrefWidth(199);
        major.setStyle( "-fx-alignment: CENTER;");
        major.setCellValueFactory(new PropertyValueFactory<>("major"));

        TableColumn department = new TableColumn("Department");
        department.setPrefWidth(199);
        department.setStyle( "-fx-alignment: CENTER;");
        department.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn numberOfStudentsEnrolled = new TableColumn("# of Students Enrolled");
        numberOfStudentsEnrolled.setPrefWidth(199);
        numberOfStudentsEnrolled.setStyle( "-fx-alignment: CENTER;");
        numberOfStudentsEnrolled.setCellValueFactory(new PropertyValueFactory<>("numberOfStudentsEnrolled"));

        sampleTable.getColumns().addAll(courseName, major, department, numberOfStudentsEnrolled);

        sampleTable.itemsProperty().bind(task.valueProperty());
        Thread t = new Thread(task);
        t.start();

        try {
            t.join();
            recordCounter.setText(Integer.toString(task.get().size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void setListOfAllCoursesViewColumns(Task<ObservableList<ListOfAllCourses>> task){
        sampleTable.getColumns().clear();

        TableColumn course_id = new TableColumn("Course ID");
        course_id.setPrefWidth(159);
        course_id.setStyle( "-fx-alignment: CENTER;");
        course_id.setVisible(false);
        course_id.setCellValueFactory(new PropertyValueFactory<>("course_id"));

        TableColumn courseAttribute = new TableColumn("Course Attribute");
        courseAttribute.setPrefWidth(159);
        courseAttribute.setStyle( "-fx-alignment: CENTER;");
        courseAttribute.setCellValueFactory(new PropertyValueFactory<>("courseAttribute"));

        TableColumn courseName = new TableColumn("Course Name");
        courseName.setPrefWidth(159);
        courseName.setStyle( "-fx-alignment: CENTER;");
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn major = new TableColumn("Major");
        major.setPrefWidth(159);
        major.setStyle( "-fx-alignment: CENTER;");
        major.setCellValueFactory(new PropertyValueFactory<>("major"));

        TableColumn department = new TableColumn("Department");
        department.setPrefWidth(159);
        department.setStyle( "-fx-alignment: CENTER;");
        department.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn departmentChair = new TableColumn("Chair Person");
        departmentChair.setPrefWidth(159);
        departmentChair.setStyle( "-fx-alignment: CENTER;");
        departmentChair.setCellValueFactory(new PropertyValueFactory<>("departmentChair"));

        sampleTable.getColumns().addAll(course_id, courseAttribute, courseName, major, department, departmentChair);

        sampleTable.itemsProperty().bind(task.valueProperty());
        Thread t = new Thread(task);
        t.start();

        try {
            t.join();
            recordCounter.setText(Integer.toString(task.get().size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void setEnrollDropCoursesViewColumns(Task<ObservableList<EnrollDropCourses>> task){
        sampleTable.getColumns().clear();

        TableColumn student_id = new TableColumn("StudentID?");
        student_id.setPrefWidth(159);
        student_id.setStyle( "-fx-alignment: CENTER;");
        student_id.setVisible(false);
        student_id.setCellValueFactory(new PropertyValueFactory<>("student_id"));

        TableColumn select = new TableColumn("Select");
        select.setPrefWidth(99);
        select.setStyle( "-fx-alignment: CENTER;");
        select.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

        TableColumn course_id = new TableColumn("Course ID");
        course_id.setPrefWidth(99);
        course_id.setStyle( "-fx-alignment: CENTER;");
        course_id.setCellValueFactory(new PropertyValueFactory<>("course_id"));

        TableColumn courseAttribute = new TableColumn("Course Attribute");
        courseAttribute.setPrefWidth(199);
        courseAttribute.setStyle( "-fx-alignment: CENTER;");
        courseAttribute.setCellValueFactory(new PropertyValueFactory<>("courseAttribute"));

        TableColumn courseName = new TableColumn("Course Name");
        courseName.setPrefWidth(399);
        courseName.setStyle( "-fx-alignment: CENTER;");
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        sampleTable.getColumns().addAll(student_id, select, course_id, courseAttribute, courseName);

        sampleTable.itemsProperty().bind(task.valueProperty());
        Thread t = new Thread(task);
        t.start();

        try {
            t.join();
            recordCounter.setText(Integer.toString(task.get().size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class GetAllRecordsFromFinalStudentViewTask extends Task<ObservableList<FinalStudentsView>> {
        @Override
        protected ObservableList<FinalStudentsView> call() throws Exception {
            return FXCollections.observableArrayList(DataSource.getInstance().queryFinalStudentsView());
        }
    }

    private class GetAllRecordsFromFinalCourseViewTask extends Task<ObservableList<FinalCoursesView>> {
        @Override
        protected ObservableList<FinalCoursesView> call() throws Exception {
            return FXCollections.observableArrayList(DataSource.getInstance().queryFinalCoursesView());
        }
    }

    private class GetAllRecordsFromListOfAllCoursesViewTask extends Task<ObservableList<ListOfAllCourses>> {
        @Override
        protected ObservableList<ListOfAllCourses> call() throws Exception {
            return FXCollections.observableArrayList(DataSource.getInstance().queryListOfAllCoursesView());
        }
    }
}
