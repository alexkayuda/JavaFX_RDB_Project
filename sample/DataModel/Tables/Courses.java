package sample.DataModel.Tables;


import javafx.beans.property.SimpleStringProperty;

public class Courses {
    private SimpleStringProperty course_id;
    private SimpleStringProperty major_id;
    private SimpleStringProperty courseAttribute;
    private SimpleStringProperty courseName;

    public Courses() {
        course_id = new SimpleStringProperty("");
        major_id = new SimpleStringProperty("");
        courseAttribute = new SimpleStringProperty("");
        courseName = new SimpleStringProperty("");
    }

    public Courses(String course_id, String major_id, String courseAttribute, String courseName) {
        this.course_id.set(course_id);
        this.major_id.set(major_id);
        this.courseAttribute.set(courseAttribute);
        this.courseName.set(courseName);
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

    public String getMajor_id() {
        return major_id.get();
    }

    public SimpleStringProperty major_idProperty() {
        return major_id;
    }

    public void setMajor_id(String major_id) {
        this.major_id.set(major_id);
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
        return "Courses{" +
                "course_id=" + course_id +
                ", major_id=" + major_id +
                ", courseAttribute=" + courseAttribute +
                ", courseName=" + courseName +
                '}';
    }
}
