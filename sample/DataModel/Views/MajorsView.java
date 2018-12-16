package sample.DataModel.Views;


import javafx.beans.property.SimpleStringProperty;

public class MajorsView {

    private SimpleStringProperty id;
    private SimpleStringProperty majorName;
    private SimpleStringProperty departmentid;

    public MajorsView() {
        id = new SimpleStringProperty("");
        majorName = new SimpleStringProperty("");
        departmentid = new SimpleStringProperty("");
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

    public String getMajorName() {
        return majorName.get();
    }

    public SimpleStringProperty majorNameProperty() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName.set(majorName);
    }

    public String getDepartmentid() {
        return departmentid.get();
    }

    public SimpleStringProperty departmentidProperty() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid.set(departmentid);
    }

    @Override
    public String toString() {
        return "MajorsView{" +
                "id=" + id +
                ", majorName=" + majorName +
                ", departmentid=" + departmentid +
                '}';
    }
}
