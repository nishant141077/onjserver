/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author nishant
 */
public class AdminService {
    
    private boolean queryExecutor(String query) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onjserver", "root", "123");
            statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        }
        catch(Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
            return false;
        }
                
    }
    
    public boolean handleAvailable(String handle) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select handle from admin where handle = ?";
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onjserver", "root", "123");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return false;
            }
            else return true;
        }
        catch(Exception exception) {
            System.err.println(exception.toString());
            JOptionPane.showMessageDialog(null, "Error occured while executing query!");
        }
        return true;
    }
    
    public boolean registerAdmin(String name, String handle, String password, String gender) {
        String query = "insert into admin values('"+name+"', '"+handle+"', '"+password+"', '"+gender+"')";
        if(queryExecutor(query)) {
            JOptionPane.showMessageDialog(null, "Registered Successfully!");
            return true;
        }
        return false;
    }

    public boolean loginAdmin(String handle, String password) 
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select handle from admin where handle = ? and password = ?";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onjserver", "root", "123");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                //JOptionPane.showMessageDialog(null, "Logged in Successfully!");
                return true;
            }
            JOptionPane.showMessageDialog(null, "Invalid credentials");
        }
        catch(Exception exception) {
            System.err.println(exception.toString());
            JOptionPane.showMessageDialog(null, "Error occured while executing query!");
        }
        return false;
    }
}
