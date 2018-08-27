package sample.datamodel;


import javafx.beans.property.SimpleStringProperty;

public class Notes {
    private SimpleStringProperty _id;
    private SimpleStringProperty note;

    public Notes() {
        _id = new SimpleStringProperty("");
        note = new SimpleStringProperty("");
    }

    public Notes(String _id, String note){
        this._id.set(_id);
        this.note.set(note);
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
        return "Notes{" +
                "_id=" + _id +
                ", note=" + note +
                '}';
    }
}
