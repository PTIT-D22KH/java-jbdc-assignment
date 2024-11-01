/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jbdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.Student;

/**
 *
 * @author P51
 */
public class StudentDao {
    private final Connection connection;
    public StudentDao() {
        this.connection = DatabaseConnector.getInstance().getConnection();
    }
    
    public void add(Student student) throws SQLException {
        if  (student == null) {
            throw new SQLException("Student is null!");
        }
        
        String query = "INSERT INTO `student` "
            + "(id, name, className, gpa) "
            + "VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setNString(1, student.getId());
        preparedStatement.setNString(2, student.getName());
        preparedStatement.setNString(3, student.getClassName());
        preparedStatement.setDouble(4, student.getGpa());
        preparedStatement.executeUpdate();

        
    }
    public boolean checkExists(String id) throws SQLException{
        String query = "SELECT * "
                + "FROM student "
                + "WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return true;
        }
        return false;
        
    }
    public ArrayList<Student> getAll() throws SQLException {
        String query = "SELECT * FROM student;";
        Statement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<Student> students = new ArrayList<>();
        while (resultSet.next()) {
            students.add(Student.getFromResultSet(resultSet));
        }
        return students;
    }
    public void deleteAll() throws SQLException {
        String query = "DELETE FROM student;";
        Statement statement = connection.prepareStatement(query);
        statement.executeUpdate(query);
    }
    public void update(String id, Student student) throws SQLException {
        String query = "UPDATE student "
                + "SET id = ?, name = ?, className = ?, gpa = ? "
                + "WHERE id =  ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, student.getId());
        preparedStatement.setString(2, student.getName());
        preparedStatement.setString(3, student.getClassName());
        preparedStatement.setDouble(4, student.getGpa());
        preparedStatement.setString(5, id);
        preparedStatement.executeUpdate();
    }
    public void delete(String id) throws SQLException {
        String query = "DELETE FROM student "
                + "WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id);
        statement.executeUpdate();
    }
}
