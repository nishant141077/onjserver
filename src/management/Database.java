/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import entities.Coder;
import entities.Problem;
import entities.ProblemDetails;
import entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.management.remote.JMXConnectionNotification;
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
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
            return false;
        }
        
        //Insert into coder table also
        String queryStr = "INSERT into coder(handle) values('"+user.handle+"')";
        
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            int temp = preparedStatement.executeUpdate();
            System.err.println(temp);
            return true;
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
            return false;
        }
        
    }

    public static Coder getCoderDetails(Coder coder) {
        Coder coderDetails = new Coder();
        coderDetails.handle = coder.handle;
        String query = "SELECT name from user where handle = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, coder.handle);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                coderDetails.name = resultSet.getString("name");
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());            
        }
        
        String queryStr = "SELECT * from coder where handle = ?";
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            preparedStatement.setString(1, coder.handle);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                coderDetails.aboutMe = resultSet.getString("about_me");
                coderDetails.accepted = resultSet.getInt("ac");
                coderDetails.compilationErrors = resultSet.getInt("cte");
                coderDetails.contests = resultSet.getInt("contests");
                coderDetails.problemsSolved = resultSet.getInt("problems_solved");
                coderDetails.rating = resultSet.getInt("rating");
                coderDetails.runtimeErrors = resultSet.getInt("rte");
                coderDetails.submissions = resultSet.getInt("submissions");
                coderDetails.timeLimitExceeds = resultSet.getInt("tle");
                coderDetails.wrongAnswers = resultSet.getInt("wa");
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        
        return coderDetails;
    }

    public static List<Problem> getProblemsList() {
        List<Problem> problemsList = new ArrayList<Problem>();
        String query = "SELECT code, name, difficulty, solvedby, attemptedby from problems";
        
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
         
            while(resultSet.next()) {
                Problem problem = new Problem();
                problem.code = resultSet.getString("code");
                problem.name = resultSet.getString("name");
                problem.difficulty = resultSet.getInt("difficulty");
                problem.solvedBy = resultSet.getInt("solvedby");
                
                int attemptedby = resultSet.getInt("attemptedby");
                if(attemptedby == 0) 
                    problem.accuracy = 0.0;
                else 
                    problem.accuracy = problem.solvedBy * 1.0 / attemptedby;
                
                problemsList.add(problem);
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        
        return problemsList;
    }

    public static ProblemDetails getProblemDetails(String code) {
        ProblemDetails problemDetails;
        String query = "SELECT name, statement, difficulty, author, time_limit, "
                + "source_limit, memory_limit from problems where code = ?";
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                problemDetails = new ProblemDetails(code, resultSet.getString("name"),
                        resultSet.getString("statement"), resultSet.getString("author"),
                        resultSet.getInt("time_limit"), resultSet.getInt("source_limit"), 
                        resultSet.getInt("memory_limit"), resultSet.getInt("difficulty"));
                return problemDetails;
            }
            
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        return new ProblemDetails(code);
    }
    
}
