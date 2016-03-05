/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.User;
import java.sql.DriverManager;
import java.sql.SQLException;
import management.Database;
import management.Message;
/**
 *
 * @author nishant
 */
public class ClientService {
    
    public ClientService() throws SQLException {
        Database.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onjserver", "root", "123");
    }
    
    public Message serveClient(Message message) {
        Message reply = new Message();
        if(message.code == 1) { //search handle
            reply.user = new User(message.user.handle, message.user.password);
            reply.code = message.code;
            reply.status = Database.searchHandle(message.user.handle);
            return reply;
        }
        else if(message.code == 2) {  //check authenticity (login credentials)
            reply.user = new User(message.user.handle, message.user.password);
            reply.code = message.code;
            reply.status = Database.checkAuthenitcity(message.user.handle, message.user.password);
            return reply;
        }
        else if(message.code == 3) {  //check question-anwswer correctness
            reply.user = new User(message.user.handle, "", "", message.user.secQuestion,
                    message.user.answer, "");
            reply.code = message.code;
            reply.status = Database.checkUserValidity(message.user);
            return reply;
        }
        else if(message.code == 4) {  //reset password for a user
            reply.user = new User(message.user.handle, message.user.password);
            reply.code = message.code;
            reply.status = Database.resetPassword(reply.user);
            return reply;
        }
        else if(message.code == 5) {  //register user
            reply.user = message.user;
            reply.code = message.code;
            reply.status = Database.registerUser(message.user);
            return reply;
        }
        else if(message.code == 6) {  //get coder details based on handle
            reply.code = message.code;
            reply.status = true;
            reply.coder = Database.getCoderDetails(message.coder);
            if(reply.coder.name == null) {
                reply.status = false;
            }
            return reply;
        }
        else if(message.code == 7) {  //get list of problems
            reply.code = message.code;
            reply.status = true;
            reply.problemsList = Database.getProblemsList();
            return reply;
        }
        return new Message();
    }

    
}
