package sample.DataModel.Views;


import javafx.beans.property.SimpleStringProperty;

public class FinalCoursesView {
    private SimpleStringProperty courseAttribute;
    private SimpleStringProperty courseName;
    private SimpleStringProperty major;
    private SimpleStringProperty department;
    private SimpleStringProperty numberOfStudentsEnrolled;

    public FinalCoursesView() {
        courseAttribute = new SimpleStringProperty("");
        courseName = new SimpleStringProperty("");
        major = new SimpleStringProperty("");
        department = new SimpleStringProperty("");
        numberOfStudentsEnrolled = new SimpleStringProperty("");
    }

    public FinalCoursesView(String courseAttribute, String courseName, String major,
                            String department, String numberOfStudentsEnrolled) {
        this.courseAttribute.set(courseAttribute);
        this.courseName.set(courseName);
        this.major.set(major);
        this.department.set(department);
        this.numberOfStudentsEnrolled.set(numberOfStudentsEnrolled);
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

    public String getMajor() {
        return major.get();
    }

    public SimpleStringProperty majorProperty() {
        return major;
    }

    public void setMajor(String major) {
        this.major.set(major);
    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getNumberOfStudentsEnrolled() {
        return numberOfStudentsEnrolled.get();
    }

    public SimpleStringProperty numberOfStudentsEnrolledProperty() {
        return numberOfStudentsEnrolled;
    }

    public void setNumberOfStudentsEnrolled(String numberOfStudentsEnrolled) {
        this.numberOfStudentsEnrolled.set(numberOfStudentsEnrolled);
    }

    @Override
    public String toString() {
        return "FinalCoursesView{" +
                "courseAttribute=" + courseAttribute +
                ", courseName=" + courseName +
                ", major=" + major +
                ", department=" + department +
                ", numberOfStudentsEnrolled=" + numberOfStudentsEnrolled +
                '}';
    }
}
