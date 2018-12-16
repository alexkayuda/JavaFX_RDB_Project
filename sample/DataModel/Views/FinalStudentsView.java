package sample.DataModel.Views;

import javafx.beans.property.SimpleStringProperty;

public class FinalStudentsView {
    private SimpleStringProperty id;
    private SimpleStringProperty fullName;
    private SimpleStringProperty gpa;
    private SimpleStringProperty major;
    private SimpleStringProperty department;
    private SimpleStringProperty departmentChair;

    public FinalStudentsView() {
        id = new SimpleStringProperty("");
        fullName = new SimpleStringProperty("");
        gpa = new SimpleStringProperty("");
        major = new SimpleStringProperty("");
        department = new SimpleStringProperty("");
        departmentChair = new SimpleStringProperty("");
    }

    public FinalStudentsView(String id, String fullName, String gpa, String major,
                             String department, String departmentChair) {
        this.id.set(id);
        this.fullName.set(fullName);
        this.gpa.set(gpa);
        this.major.set(major);
        this.department.set(department);
        this.departmentChair.set(departmentChair);
    }

    public String getid() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setid(String id) {
        this.id.set(id);
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public String getGpa() {
        return gpa.get();
    }

    public SimpleStringProperty gpaProperty() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa.set(gpa);
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

    public String getChairPerson() {
        return departmentChair.get();
    }

    public SimpleStringProperty departmentChairProperty() {
        return departmentChair;
    }

    public void setChairPerson(String departmentChair) {
        this.departmentChair.set(departmentChair);
    }

    @Override
    public String toString() {
        return "FinalStudentsView{" +
                "id=" + id +
                ", fullName=" + fullName +
                ", gpa=" + gpa +
                ", major=" + major +
                ", department=" + department +
                ", departmentChair=" + departmentChair +
                '}';
    }
}
