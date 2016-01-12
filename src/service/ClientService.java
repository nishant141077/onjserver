/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import management.Message;

/**
 *
 * @author nishant
 */
public class ClientService {

    public Message serveClient(Message message) {
        Message reply = new Message();
        if(message.code == 1) { //search handle
            reply.handle = message.handle;
            reply.code = message.code;
            reply.status = searchHandle(message.handle);
            return reply;
        }
        return new Message();
    }

    private boolean searchHandle(String handle) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select handle from user where handle = ?";
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onjserver", "root", "123");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
            else return false;
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
        return false;
    }
    
}
