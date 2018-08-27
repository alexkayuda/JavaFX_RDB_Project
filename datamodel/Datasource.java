
package sample.datamodel;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

    public static final String DB_NAME = "contacts.db";

    public static final String CONNECTION_STRING = "jdbc:sqlite:/Users/alex/Desktop/IntelliJ_Projects/ContactList/" + DB_NAME;

    public static final String TABLE_INFO = "info";
    public static final String TABLE_INFO_COLUMN_ID = "_id";
    public static final String TABLE_INFO_COLUMN_FIRST_NAME = "firstName";
    public static final String TABLE_INFO_COLUMN_LAST_NAME = "lastName";
    public static final String TABLE_INFO_COLUMN_EMAIL = "email";

    public static final int TABLE_INFO_INDEX_COLUMN_ID = 1;
    public static final int TABLE_INFO_INDEX_COLUMN_FIRST_NAME = 2;
    public static final int TABLE_INFO_INDEX_COLUMN_LAST_NAME = 3;
    public static final int TABLE_INFO_INDEX_COLUMN_EMAIL = 4;

    public static final String TABLE_NOTES = "notes";
    public static final String TABLE_NOTES_COLUMN_ID = "_id";
    public static final String TABLE_NOTES_COLUMN_NOTE = "note";

    public static final int TABLE_NOTES_INDEX_COLUMN_ID = 1;
    public static final int TABLE_NOTES_INDEX_COLUMN_NOTE = 2;

    public static final String VIEW_SAMPLE = "sample";
    public static final String VIEW_SAMPLE_COLUMN_ID = "ID";
    public static final String VIEW_SAMPLE_COLUMN_FULL_NAME = "Full_Name";
    public static final String VIEW_SAMPLE_COLUMN_EMAIL = "email";
    public static final String VIEW_SAMPLE_COLUMN_NOTE = "note";

    public static final int VIEW_SAMPLE_INDEX_COLUMN_ID = 1;
    public static final int VIEW_SAMPLE_INDEX_COLUMN_FULL_NAME = 2;
    public static final int VIEW_SAMPLE_INDEX_COLUMN_EMAIL = 3;
    public static final int VIEW_SAMPLE_INDEX_COLUMN_NOTE = 4;

    public static final String QUERY_CREATE_TABLE_INFO = "CREATE TABLE IF NOT EXISTS " + TABLE_INFO +
            " ( " + TABLE_INFO_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    TABLE_INFO_COLUMN_FIRST_NAME + " TEXT, " +
                    TABLE_INFO_COLUMN_LAST_NAME + " TEXT, " +
                    TABLE_INFO_COLUMN_EMAIL + " TEXT " +
            " ) ";
    public static final String QUERY_CREATE_TABLE_NOTES = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES +
            " ( " + TABLE_NOTES_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    TABLE_NOTES_COLUMN_NOTE + " TEXT " +
            " ) ";

    /**
     * create view if not exists sample as
     * select (a._id) as ID, (a.firstName || " " || a.lastName) as Full_Name , a.email, b.note
     * from info a, notes b
     * where a._id = b._id;
     */
    public static final String QUERY_CREATE_VIEW = "CREATE VIEW IF NOT EXISTS " + VIEW_SAMPLE + " AS SELECT" +
            "(a." + TABLE_INFO_COLUMN_ID + ")" +
            " AS " + VIEW_SAMPLE_COLUMN_ID + ", " +
            "(a." + TABLE_INFO_COLUMN_FIRST_NAME + " || ' ' || " + TABLE_INFO_COLUMN_LAST_NAME + ")" +
            "AS " + VIEW_SAMPLE_COLUMN_FULL_NAME + ", " +
            "a." + TABLE_INFO_COLUMN_EMAIL + ", " +
            "b." + TABLE_NOTES_COLUMN_NOTE +
            "FROM " + TABLE_INFO + "a, " + TABLE_NOTES + "b " +
            "WHERE " + "a." + TABLE_INFO_COLUMN_ID + " = " + "b." + TABLE_NOTES_COLUMN_ID;


    public static final String QUERY_INSERT_INTO_INFO_TABLE = "INSERT INTO " + TABLE_INFO +
            " ( " + TABLE_INFO_COLUMN_FIRST_NAME + ", " +
                    TABLE_INFO_COLUMN_LAST_NAME + ", " +
                    TABLE_INFO_COLUMN_EMAIL +
            " ) " + " VALUES ";

    public static final String QUERY_INSERT_INTO_NOTES_TABLE = "INSERT INTO " + TABLE_NOTES +
            " ( " + TABLE_NOTES_COLUMN_NOTE +
            " ) " + " VALUES ";


    public static final int ORDER_BY_NONE = 0;
    public static final int ORDER_BY_ASC = 1;
    public static final int ORDER_BY_DEC = 2;

    private Connection connection;

    /**
     * Prepared Statements go here
     */
//    private PreparedStatement queryView;

    private static Datasource instance = new Datasource();

    private Datasource() {   }

    public static Datasource getInstance(){  return instance;  }

    public boolean openConnection() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);

            /**
             * instantiate prepared statements
             */

            return true;
        }catch (SQLException e){
            System.out.println("Error occurred. Was not able to connect to a database.");
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try{

            /**
             * if there are prepared statements open - CLOSE THEM NOW
             */

            if(connection != null)
                connection.close();
        }catch (SQLException e){
            System.out.println("Error occurred. Was not able to close connection.");
            e.getMessage();
        }
    }

    public void loadRecords(){
        /**
         * Connect to db,
         * create view if not exists,
         * query view,
         * populate inner list
         */

        if(!openConnection()){
            System.out.println("Can not establish connection");
            return;
        }

        try(Statement statement = connection.createStatement()) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<View> getRecords(){

        /**
         * Just return populated list from loadRecords()
         */
        return null;
    }

    public void addNewEntry(View newEntry){

    }

    public void deleteExistingEntry(View existingEntry){

    }


    public List<View> queryView(){
        try(Statement statement = connection.createStatement();
            ResultSet results =  statement.executeQuery("select * from sample")){

            List<View> viewTable = new ArrayList<>();

            while(results.next()){
                View record = new View();
                record.setId(Integer.toString(results.getInt(VIEW_SAMPLE_COLUMN_ID)));
                record.setFullName(results.getString(VIEW_SAMPLE_COLUMN_FULL_NAME));
                record.setEmail(results.getString(VIEW_SAMPLE_COLUMN_EMAIL));
                record.setNote(results.getString(VIEW_SAMPLE_COLUMN_NOTE));

                viewTable.add(record);
            }

            return viewTable;
        }catch (SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    //    public List<Info> queryInfoTable() {
//
//        /**
//         * Both, statement and results, will be closed automatically regardless the outcome (success / fail)
//         * No need to close any of them manually.Keeps code clean.
//         */
//        try (Statement statement = connection.createStatement();
//            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_INFO)) {
//
//            List<Info> personalInformation = new ArrayList<>();
//
//            while (results.next()){
//                Info info = new Info();
//                info.setId(Integer.toString(results.getInt(TABLE_INFO_COLUMN_ID)));
//                info.setFirstName(results.getString(TABLE_INFO_COLUMN_FIRST_NAME));
//                info.setLastName(results.getString(TABLE_INFO_COLUMN_LAST_NAME));
//                info.setEmail(results.getString(TABLE_INFO_COLUMN_EMAIL));
//                personalInformation.add(info);
//            }
//
//            return  personalInformation;
//
//        }catch (SQLException e){
//            System.out.println("Query failed: " + e.getMessage());
//            return null;
//        }
//    }


//    public List<Notes> queryNotesTable(){
//
//        /**
//         * Both, statement and results, will be closed automatically regardless the outcome (success / fail)
//         * No need to close any of them manually.Keeps code clean.
//         */
//
//        try(Statement statement = connection.createStatement();
//            ResultSet results =  statement.executeQuery("SELECT * FROM " + TABLE_NOTES)){
//
//            List<Notes> personalNotes = new ArrayList<>();
//
//            while(results.next()){
//                Notes note = new Notes();
//                note.setId(Integer.toString(results.getInt(TABLE_NOTES_COLUMN_ID)));
//                note.setNote(results.getString(TABLE_NOTES_COLUMN_NOTE));
//                personalNotes.add(note);
//            }
//
//            return personalNotes;
//
//        }catch (SQLException e){
//            System.out.println("Query failed: " + e.getMessage());
//            return null;
//        }
//    }

}















