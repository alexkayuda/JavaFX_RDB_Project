package sample.DataModel.Views;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import sample.DataModel.DataSource;

public class EnrollDropCourses {

    private SimpleIntegerProperty student_id;
    private CheckBox checkBox;
    private SimpleIntegerProperty course_id;
    private SimpleStringProperty courseAttribute;
    private SimpleStringProperty courseName;

    public EnrollDropCourses() {
        student_id = new SimpleIntegerProperty();
        checkBox = new CheckBox();

        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!DataSource.getInstance().enrollStudentIntoCourse(getStudent_id(), getCourse_id())){
                    DataSource.getInstance().dropStudentFromCourse(getStudent_id(), getCourse_id());

                }
            }
        });

        course_id = new SimpleIntegerProperty();
        courseAttribute = new SimpleStringProperty("");
        courseName = new SimpleStringProperty("");
    }

    public EnrollDropCourses(Integer student_id, CheckBox checkBox, Integer course_id, String courseAttribute, String courseName) {
        this.student_id.setValue(student_id);
        this.checkBox = checkBox;
        this.course_id.set(course_id);
        this.courseAttribute.set(courseAttribute);
        this.courseName.set(courseName);
    }

    public int getStudent_id() {
        return student_id.get();
    }

    public SimpleIntegerProperty student_idProperty() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id.set(student_id);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public boolean getCheckBoxValue() {
        return  checkBox.isSelected();
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void setCheckBoxValue(boolean value){
        this.checkBox.selectedProperty().setValue(value);
    }

    public int getCourse_id() {
        return course_id.get();
    }

    public SimpleIntegerProperty course_idProperty() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id.set(course_id);
    }

    public String getCourseAttribute() {
        return courseAttribute.get();
    }

    public SimpleStringProperty courseAttributeProperty() {
        return courseAttribute;
    }

    public void setCourseAttribute(String courseAttribute) {
        this.courseAttribute.set(courseAttribute);
    }

    public String getCourseName() {
        return courseName.get();
    }

    public SimpleStringProperty courseNameProperty() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    @Override
    public String toString() {
        return "EnrollDropCourses{" +
                "student_id=" + student_id +
                ", checkBox=" + checkBox +
                ", course_id=" + course_id +
                ", courseAttribute=" + courseAttribute +
                ", courseName=" + courseName +
                '}';
    }
}
