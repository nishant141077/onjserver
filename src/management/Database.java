/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sound.midi.SysexMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author nishant
 */
public class Database {
    public static Connection connection;
    public static PreparedStatement preparedStatement;
    public static ResultSet resultSet;
    
    public static boolean searchHandle(String handle) {
        
        String query = "select handle from user where handle = ?";
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
        return false;
    }

    public static boolean checkAuthenitcity(String handle, String password) {
        String query = "select handle from user where handle = ? and password = ?";
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return true;
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        return false;
    }

    public static boolean checkUserValidity(User user) {
        String query = "select handle from user where handle = ? and sec_question = ? and answer = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.handle);
            preparedStatement.setString(2, user.secQuestion);
            preparedStatement.setString(3, user.answer);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                return true;
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        return false;
    }

    public static boolean resetPassword(User user) {
        String query = "UPDATE user SET password = ? where handle = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.password);
            preparedStatement.setString(2, user.handle);
            int temp = preparedStatement.executeUpdate();
            System.err.println(temp);
            return true;
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
            return false;
        }
    }

    public static boolean registerUser(User user) {
        String query = "INSERT into user(name, handle, password, gender, sec_question, "
                + "answer) values('"+user.name+"', '"+user.handle+"', '"+user.password+"', '"+
                user.gender+"', '"+user.secQuestion+"', '"+user.answer+"')";
        
        try {
            preparedStatement = connection.prepareStatement(query);
            int temp = preparedStatement.executeUpdate();
            System.err.println(temp);
            return true;
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
            return false;
        }
    }
    
}
