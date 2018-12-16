package sample.DataModel;


import javafx.beans.property.SimpleStringProperty;
import sample.DataModel.Tables.Courses;
import sample.DataModel.Tables.Students;
import sample.DataModel.Views.EnrollDropCourses;
import sample.DataModel.Views.FinalCoursesView;
import sample.DataModel.Views.FinalStudentsView;
import sample.DataModel.Views.ListOfAllCourses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private static final String DB_NAME = "RegistarOfficeProject";
    private static final String MYSQL_PROPERTY = "?autoReconnect=true&useSSL=false";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME + MYSQL_PROPERTY;

    private static final String TABLE_STUDENTS = "Students";
    private static final String TABLE_COURSES = "Courses";
    private static final String TABLE_STUDENTSCOURSES = "StudentsCourses";
    private static final String TABLE_DEPARTMENTS = "Departments";
    private static final String TABLE_MAJORS = "Majors";

    private static final String VIEW_FINAL_STUDENTS = "FinalStudentsView";
    private static final String VIEW_FINAL_COURSES = "FinalCoursesView";
    private static final String VIEW_LISTOFALLCOURSES = "ListOfAllCourses";
    private static final String VIEW_FINAL_MAJORS = "MajorsView";

    private static PreparedStatement insertIntoStudentsTable;
    private static PreparedStatement insertIntoCoursesTable;
    private static PreparedStatement insertIntoStudentsCoursesTable;

    private static PreparedStatement updateRecordInStudentsTable;
    private static PreparedStatement updateRecordInCoursesTable;

    private static CallableStatement getCoursesStudentEnrolledInto;
    private static CallableStatement getCoursesStudentNOTEnrolledInto;
    private static PreparedStatement getDepartmentPrefixValue;

    private static PreparedStatement deleteFromStudentsTable;
    private static PreparedStatement deleteFromCoursesTable;
    private static PreparedStatement deleteStudentIDFromStudentsCoursesTable;
    private static PreparedStatement deleteCourseIDFromStudentsCoursesTable;
    private static PreparedStatement deleteFromStudentsCoursesTable;

    private static Connection connection;
    private static DataSource instance;

    private DataSource() {   }

    public static DataSource getInstance(){ return (instance == null) ? instance = new DataSource() : instance; }

    public boolean openConnection() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, "root", "root");

            insertIntoStudentsTable = connection.prepareStatement("insert into " +
                    TABLE_STUDENTS + " values (null, ?, ?, ?, ?)");

            insertIntoCoursesTable = connection.prepareStatement("insert into " +
                    TABLE_COURSES + " values (null, ?, ?, ?)");

            insertIntoStudentsCoursesTable = connection.prepareStatement("insert into " +
                    TABLE_STUDENTSCOURSES + " (StudentsCourses.Student_id," +
                    " StudentsCourses.Course_id) values (?,?);");

            updateRecordInStudentsTable = connection.prepareStatement("update " +
                    TABLE_STUDENTS + " set Students.Student_FirstName = ?," +
                                          "Students.Student_LastName = ?," +
                                          "Students.Student_GPA = ?," +
                                          "Students.Major_id = ?" +
                                     " where Students.Student_id = ?");

            updateRecordInCoursesTable = connection.prepareStatement("update " +
                    TABLE_COURSES + " set Major_id = ?," +
                                        "Course_Attribute = ?," +
                                        "Course_Name = ?" +
                                    " where Course_id = ?");

            deleteFromStudentsTable = connection.prepareStatement("delete from " +
                    TABLE_STUDENTS + " where Students.Student_id = ?");

            deleteFromCoursesTable = connection.prepareStatement("delete from " +
                    TABLE_COURSES + " where Courses.Course_id = ?");

            deleteStudentIDFromStudentsCoursesTable = connection.prepareStatement("delete from " +
                    TABLE_STUDENTSCOURSES + " where StudentsCourses.Student_id = ?");

            deleteCourseIDFromStudentsCoursesTable = connection.prepareStatement("delete from " +
                    TABLE_STUDENTSCOURSES + " where StudentsCourses.Course_id = ?");

            deleteFromStudentsCoursesTable = connection.prepareStatement("delete from " +
                    TABLE_STUDENTSCOURSES + " where " + TABLE_STUDENTSCOURSES + ".Student_id = ? and " +
                    TABLE_STUDENTSCOURSES + ".Course_id = ?;");

            getDepartmentPrefixValue = connection.prepareStatement("select Department_Prefix from " +
                    TABLE_DEPARTMENTS + ", " + TABLE_MAJORS + " where Majors.Department_id = Departments.Department_id " +
                                                " and Majors.Major_id = ?");

            getCoursesStudentEnrolledInto = connection.prepareCall("{ call showCoursesStudentEnrolledInto(?) }");

            getCoursesStudentNOTEnrolledInto = connection.prepareCall("{ call showCoursesStudentNotEnrolledInto(?) }");

            return true;
        }catch (SQLException e){
            System.out.println("Error occurred. Was not able to connect to a database.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void closeConnection() {
        try{
            if(connection != null) {
                insertIntoCoursesTable.close();
                insertIntoCoursesTable.close();
                insertIntoStudentsCoursesTable.close();

                updateRecordInStudentsTable.close();
                updateRecordInCoursesTable.close();

                deleteFromStudentsTable.close();
                deleteFromCoursesTable.close();
                deleteStudentIDFromStudentsCoursesTable.close();
                deleteCourseIDFromStudentsCoursesTable.close();
                deleteFromStudentsCoursesTable.close();

                getDepartmentPrefixValue.close();

                getCoursesStudentEnrolledInto.close();
                getCoursesStudentNOTEnrolledInto.close();


                connection.close();
            }
        }catch (SQLException e){
            System.out.println("Error occurred. Was not able to close connection.");
            e.getMessage();
        }
    }

    public boolean addNewStudent(Students student){
        try {
            insertIntoStudentsTable.setString(1, student.getFirstName());
            insertIntoStudentsTable.setString(2, student.getLastName());
            insertIntoStudentsTable.setDouble(3, Double.parseDouble(student.getGpa()));
            insertIntoStudentsTable.setInt(4, Integer.parseInt(student.getMajor_id()));
            insertIntoStudentsTable.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("addNewStudent Failed!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean addNewCourse(Courses course){
        try {
            insertIntoCoursesTable.setInt(1, Integer.parseInt(course.getMajor_id()));
            insertIntoCoursesTable.setString(2, course.getCourseAttribute());
            insertIntoCoursesTable.setString(3, course.getCourseName());
            insertIntoCoursesTable.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editExistingStudent(Students selectedEntry){
        try {
            updateRecordInStudentsTable.setString(1, selectedEntry.getFirstName());
            updateRecordInStudentsTable.setString(2, selectedEntry.getLastName());
            updateRecordInStudentsTable.setDouble(3, Double.parseDouble(selectedEntry.getGpa()));
            updateRecordInStudentsTable.setInt(4, Integer.parseInt(selectedEntry.getMajor_id()));
            updateRecordInStudentsTable.setInt(5, Integer.parseInt(selectedEntry.get_id()));
            updateRecordInStudentsTable.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editExistingCourse(Courses selectedEntry){
        try {
            updateRecordInCoursesTable.setInt(1, Integer.parseInt(selectedEntry.getMajor_id()));
            updateRecordInCoursesTable.setString(2, selectedEntry.getCourseAttribute());
            updateRecordInCoursesTable.setString(3, selectedEntry.getCourseName());
            updateRecordInCoursesTable.setInt(4, Integer.parseInt(selectedEntry.getCourse_id()));
            updateRecordInCoursesTable.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteExistingStudent(FinalStudentsView selectedEntry){
        try {
            deleteStudentIDFromStudentsCoursesTable.setInt(1, Integer.parseInt(selectedEntry.getid()));
            deleteStudentIDFromStudentsCoursesTable.executeUpdate();
            deleteFromStudentsTable.setInt(1, Integer.parseInt(selectedEntry.getid()));
            deleteFromStudentsTable.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteExistingCourse(ListOfAllCourses selectedEntry){
        try {
            deleteCourseIDFromStudentsCoursesTable.setInt(1, Integer.parseInt(selectedEntry.getCourse_id()));
            deleteCourseIDFromStudentsCoursesTable.executeUpdate();
            deleteFromCoursesTable.setInt(1, Integer.parseInt(selectedEntry.getCourse_id()));
            deleteFromCoursesTable.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean enrollStudentIntoCourse(int studentID, int courseID){
        try {
            insertIntoStudentsCoursesTable.setInt(1, studentID);
            insertIntoStudentsCoursesTable.setInt(2, courseID);
            insertIntoStudentsCoursesTable.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean dropStudentFromCourse(int studentID, int courseID){
        try {
            deleteFromStudentsCoursesTable.setInt(1, studentID);
            deleteFromStudentsCoursesTable.setInt(2, courseID);
            deleteFromStudentsCoursesTable.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<EnrollDropCourses> getCoursesStudentEnrolledIntoQuery(int id){
        try {
            getCoursesStudentEnrolledInto.setInt(1, id);
            getCoursesStudentEnrolledInto.execute();

            ResultSet resultSet = getCoursesStudentEnrolledInto.getResultSet();
            if(resultSet != null) {
                List<EnrollDropCourses> list = new ArrayList<>();
                while (resultSet.next()) {
                    EnrollDropCourses currentRecord = new EnrollDropCourses();
                    currentRecord.setStudent_id(resultSet.getInt("StudentID"));
                    currentRecord.setCheckBoxValue(resultSet.getBoolean("Choice"));
                    currentRecord.setCourse_id(resultSet.getInt("Course_id"));
                    currentRecord.setCourseAttribute(resultSet.getString("Course_Attribute"));
                    currentRecord.setCourseName(resultSet.getString("Course_Name"));
                    list.add(currentRecord);

//                    System.out.println(resultSet.getBoolean("Enrolled?") + " " +
//                            resultSet.getInt("Course_id") + " " +
//                            resultSet.getString("Course_Attribute"));
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<EnrollDropCourses> getCoursesStudentNOTEnrolledIntoQuery(int id){
        try {
            getCoursesStudentNOTEnrolledInto.setInt(1, id);
            getCoursesStudentNOTEnrolledInto.execute();

            ResultSet resultSet = getCoursesStudentNOTEnrolledInto.getResultSet();
            if(resultSet != null) {
                List<EnrollDropCourses> list = new ArrayList<>();
                while (resultSet.next()) {
                    EnrollDropCourses currentRecord = new EnrollDropCourses();
                    currentRecord.setStudent_id(resultSet.getInt("StudentID"));
                    currentRecord.setCheckBoxValue(resultSet.getBoolean("Choice"));
                    currentRecord.setCourse_id(resultSet.getInt("Course_id"));
                    currentRecord.setCourseAttribute(resultSet.getString("Course_Attribute"));
                    currentRecord.setCourseName(resultSet.getString("Course_Name"));
                    list.add(currentRecord);

//                    System.out.println(resultSet.getBoolean("Enrolled?") + " " +
//                            resultSet.getInt("Course_id") + " " +
//                            resultSet.getString("Course_Attribute"));
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FinalStudentsView> queryFinalStudentsView(){
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from " + VIEW_FINAL_STUDENTS)) {

            List<FinalStudentsView> list = new ArrayList<>();

            while(resultSet.next()){
                FinalStudentsView currentRecord = new FinalStudentsView();
                currentRecord.setid(Integer.toString(resultSet.getInt("Student_id")));
                currentRecord.setFullName(resultSet.getString("Full Name"));
                currentRecord.setGpa(Double.toString(resultSet.getDouble("GPA")));
                currentRecord.setMajor(resultSet.getString("Major_Name"));
                currentRecord.setDepartment(resultSet.getString("Department Name"));
                currentRecord.setChairPerson(resultSet.getString("Chair Person"));
                list.add(currentRecord);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("queryFinalStudentsView Failed!");
            e.printStackTrace();
            return null;
        }
    }

    public List<FinalCoursesView> queryFinalCoursesView(){
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from " + VIEW_FINAL_COURSES)) {

            List<FinalCoursesView> list = new ArrayList<>();

            while(resultSet.next()){
                FinalCoursesView currentRecord = new FinalCoursesView();
                currentRecord.setCourseAttribute(resultSet.getString("Course Attribute"));
                currentRecord.setCourseName(resultSet.getString("Course Name"));
                currentRecord.setMajor(resultSet.getString("Major"));
                currentRecord.setDepartment(resultSet.getString("Department"));
                currentRecord.setNumberOfStudentsEnrolled(Integer.toString(resultSet.getInt("counter")));
                list.add(currentRecord);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("queryFinalCoursesView Failed!");
            e.printStackTrace();
            return null;
        }
    }

    public List<ListOfAllCourses> queryListOfAllCoursesView(){
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from " + VIEW_LISTOFALLCOURSES)) {

            List<ListOfAllCourses> list = new ArrayList<>();

            while(resultSet.next()){
                ListOfAllCourses currentRecord = new ListOfAllCourses();
                currentRecord.setCourse_id(resultSet.getString("Course ID"));
                currentRecord.setCourseAttribute(resultSet.getString("Course Attribute"));
                currentRecord.setCourseName(resultSet.getString("Course Name"));
                currentRecord.setMajor(resultSet.getString("Major"));
                currentRecord.setDepartment(resultSet.getString("Department"));
                currentRecord.setDepartmentChair(resultSet.getString("Chair Person"));
                list.add(currentRecord);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("queryFinalCoursesView Failed!");
            e.printStackTrace();
            return null;
        }
    }

    public List<String> queryMajorsView(){
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from " + VIEW_FINAL_MAJORS)) {

            List<String> list = new ArrayList<>();

            while(resultSet.next()){
                SimpleStringProperty currentRecord = new SimpleStringProperty(resultSet.getString("Major_Name"));
                list.add(currentRecord.getValue());
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Query Failed!");
            e.printStackTrace();
            return null;
        }
    }

    public String getDepartmentPrefixValue(int Department_id){
        String departmentPrefix = "ERROR_";
        ResultSet resultSet;
        try {
            getDepartmentPrefixValue.setInt(1, Department_id);
            resultSet = getDepartmentPrefixValue.executeQuery();

            while (resultSet.next()){
                departmentPrefix = resultSet.getString("Department_Prefix");
            }
            return departmentPrefix;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
