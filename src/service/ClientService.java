/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Coder;
import entities.User;
import java.sql.DriverManager;
import java.sql.SQLException;
import management.Database;
import management.Message;
import management.SubmissionManagement;
/**
 *
 * @author nishant
 */
public class ClientService {
    
    public ClientService() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Database.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onjserver", "root", "123");
    }
    
    public Message serveClient(Message message) throws Exception {
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
        else if(message.code == 8) {  //get problem details based on problem code and handle 
            reply.code = message.code;
            reply.status = true;
            reply.problemDetails = Database.getProblemDetails(message.problemDetails.code, message.coder.handle);
            return reply;
        }
        else if(message.code == 9) {  //get all tags available
            reply.code = message.code;
            reply.tagsList = Database.getTagsList();
            reply.status = true;
            return reply;
        }
        else if(message.code == 10) {  //add tags by coder to a problem
            reply.code = message.code;
            Database.addCoderTags(message.coder.handle, message.problemDetails.code, message.tagsList);
            reply.status = true;
            return reply;
        }
        else if(message.code == 11) {  //refresh problem page
            reply.code = message.code;
            reply.problemDetails = Database.getProblemDetails(message.problemDetails.code, message.coder.handle);
            reply.tagsList = Database.getTagsList();
            reply.status = true;
            return reply;
        }
        else if(message.code == 12) {  //get problem statistics
            reply.code = message.code;
            reply.problemStats = Database.getProblemStats(message.problemStats.code);
            reply.status = true;
            return reply;
        }
        else if(message.code == 13) {  //send submission
            reply.code = message.code;
            reply.submission = new SubmissionManagement(message.submission, message.problemDetails)
                    .getSubmissionStatus();
            reply.status = true;
            return reply;
        }
        else if(message.code == 14) {  //get solved problems list
            reply.code = message.code;
            reply.coder = new Coder(message.coder.handle);
            reply.problemsList = Database.getSolvedProblemsList(message.coder.handle);
            reply.status = true;
            return reply;
        }
        else if(message.code == 15) {  //get attempted unsolved problems list 
            reply.code = message.code;
            reply.coder = new Coder(message.coder.handle);
            reply.problemsList = Database.getAttemptedUnsolvedProblemsList(message.coder.handle);
            reply.status = true;
            return reply;
        }
        return new Message();
    }

    
}
