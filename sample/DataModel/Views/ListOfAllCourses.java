package sample.DataModel.Views;

import javafx.beans.property.SimpleStringProperty;

public class ListOfAllCourses {
    private SimpleStringProperty course_id;
    private SimpleStringProperty courseAttribute;
    private SimpleStringProperty courseName;
    private SimpleStringProperty major;
    private SimpleStringProperty department;
    private SimpleStringProperty departmentChair;

    public ListOfAllCourses(){
        course_id = new SimpleStringProperty("");
        courseAttribute = new SimpleStringProperty("");
        courseName = new SimpleStringProperty("");
        major = new SimpleStringProperty("");
        department = new SimpleStringProperty("");
        departmentChair = new SimpleStringProperty("");
    }

    public ListOfAllCourses(String course_id, String courseAttribute, String courseName, String major, String department, String departmentChair) {
        this.course_id.set(course_id);
        this.courseAttribute.set(courseAttribute);
        this.courseName.set(courseName);
        this.major.set(major);
        this.department.set(department);
        this.departmentChair.set(departmentChair);
    }

    public String getCourse_id() {
        return course_id.get();
    }

    public SimpleStringProperty course_idProperty() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
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

    public String getDepartmentChair() {
        return departmentChair.get();
    }

    public SimpleStringProperty departmentChairProperty() {
        return departmentChair;
    }

    public void setDepartmentChair(String departmentChair) {
        this.departmentChair.set(departmentChair);
    }

    @Override
    public String toString() {
        return "ListOfAllCourses{" +
                "course_id=" + course_id +
                ", courseAttribute=" + courseAttribute +
                ", courseName=" + courseName +
                ", major=" + major +
                ", department=" + department +
                ", departmentChair=" + departmentChair +
                '}';
    }
}
