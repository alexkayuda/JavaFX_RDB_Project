package sample.datamodel;

import javafx.beans.property.SimpleStringProperty;

public class View {

    private SimpleStringProperty id;
    private SimpleStringProperty fullName;
    private SimpleStringProperty email;
    private SimpleStringProperty note;

    public View() {
        id = new SimpleStringProperty("");
        fullName = new SimpleStringProperty("");
        email = new SimpleStringProperty("");
        note = new SimpleStringProperty("");
    }

    public View (String id, String fullName, String email, String note){
        this.id.set(id);
        this.fullName.set(fullName);
        this.email.set(email);
        this.note.set(note);
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

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getNote() {
        return note.get();
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    @Override
    public String toString() {
        return "View{" +
                "id=" + id +
                ", fullName=" + fullName +
                ", email=" + email +
                ", note=" + note +
                '}';
    }
}
