package sample.DataModel.Views;


import javafx.beans.property.SimpleStringProperty;

public class StudentsView {
    private SimpleStringProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty gpa;
    private SimpleStringProperty major_id;

    public StudentsView() {
        id = new SimpleStringProperty("");
        firstName = new SimpleStringProperty("");
        lastName = new SimpleStringProperty("");
        gpa = new SimpleStringProperty("");
        major_id = new SimpleStringProperty("");
    }

    public StudentsView(String id, String firstName, String lastName, String gpa, String major_id) {
        this.id.set(id);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.gpa.set(gpa);
        this.major_id.set(major_id);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
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

    public String getMajor_id() {
        return major_id.get();
    }

    public SimpleStringProperty major_idProperty() {
        return major_id;
    }

    public void setMajor_id(String major_id) {
        this.major_id.set(major_id);
    }

    @Override
    public String toString() {
        return "StudentsView{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", gpa=" + gpa +
                ", major_id=" + major_id +
                '}';
    }
}
