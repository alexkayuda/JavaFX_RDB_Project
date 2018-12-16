package sample.DataModel.Tables;


import javafx.beans.property.SimpleStringProperty;

public class Departments {
    private SimpleStringProperty _id;
    private SimpleStringProperty departmentName;
    private SimpleStringProperty departmentChair;

    public Departments() {
        _id = new SimpleStringProperty("");
        departmentName = new SimpleStringProperty("");
        departmentChair = new SimpleStringProperty("");
    }

    public Departments(String _id, String departmentName, String departmentChair) {
        this._id.set(_id);
        this.departmentName.set(departmentName);
        this.departmentChair.set(departmentChair);
    }

    public String get_id() {
        return _id.get();
    }

    public SimpleStringProperty _idProperty() {
        return _id;
    }

    public String getDepartmentName() {
        return departmentName.get();
    }

    public SimpleStringProperty departmentNameProperty() {
        return departmentName;
    }

    public String getDepartmentChair() {
        return departmentChair.get();
    }

    public SimpleStringProperty departmentChairProperty() {
        return departmentChair;
    }
}
