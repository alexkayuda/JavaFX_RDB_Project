package sample.DataModel.Tables;


import javafx.beans.property.SimpleStringProperty;

public class Students {
    private SimpleStringProperty _id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty gpa;
    private SimpleStringProperty major_id;

    public Students() {
        _id = new SimpleStringProperty("");
        firstName = new SimpleStringProperty("");
        lastName = new SimpleStringProperty("");
        gpa = new SimpleStringProperty("");
        major_id = new SimpleStringProperty("");
    }

    public Students(String _id, String firstName, String lastName, String gpa, String major_id) {
        this._id.set(_id);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.gpa.set(gpa);
        this.major_id.set(major_id);
    }

    public String get_id() {
        return _id.get();
    }

    public SimpleStringProperty _idProperty() {
        return _id;
    }

    public void set_id(String _id) {
        this._id.set(_id);
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
        return "Students{" +
                "_id=" + _id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", gpa=" + gpa +
                ", major_id=" + major_id +
                '}';
    }
}
