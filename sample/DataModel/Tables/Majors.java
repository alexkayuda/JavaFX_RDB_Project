package sample.DataModel.Tables;


import javafx.beans.property.SimpleStringProperty;

public class Majors {
    private SimpleStringProperty _id;
    private SimpleStringProperty majorName;
    private SimpleStringProperty department_id;

    public Majors() {
        _id = new SimpleStringProperty("");
        majorName = new SimpleStringProperty("");
        department_id = new SimpleStringProperty("");
    }
}
