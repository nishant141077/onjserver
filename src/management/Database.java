/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import entities.Coder;
import entities.Problem;
import entities.ProblemDetails;
import entities.ProblemStats;
import entities.Submission;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

/**
 *
 * @author nishant
 */
public class Database {
    public static Connection connection;
    
    public static boolean searchHandle(String handle) {
        
        String query = "select handle from user where handle = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
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

    public static boolean checkAuthenitcity(String handle, String password, SecretKey desKey) {
        String query = "select password from user where handle = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String decryptedOriginalPassword = Security.getDecryptedPassword(resultSet.getString(1), desKey);
                String decryptedAttemptedPassword = Security.getDecryptedPassword(password, desKey);
                
                if(decryptedOriginalPassword.equals(decryptedAttemptedPassword)) {
                    return true;
                }
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        return false;
    }

    public static boolean checkUserValidity(User user) {
        String query = "select handle from user where handle = ? and sec_question = ? and answer = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
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
        String query = "UPDATE user SET password = '" + user.password + "' where handle = ?";
        PreparedStatement preparedStatement;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.handle);
            int temp = preparedStatement.executeUpdate();
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
        PreparedStatement preparedStatement;
        System.out.println("Registered password : " + user.password);
        try {
            preparedStatement = connection.prepareStatement(query);
            int temp = preparedStatement.executeUpdate();
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
            return false;
        }
        
        //Insert into coder table also
        String queryStr = "INSERT into coder(handle) values('"+user.handle+"')";
        
        try {
            preparedStatement = connection.prepareStatement(queryStr);
            int temp = preparedStatement.executeUpdate();
//            System.err.println(temp);
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
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
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
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
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
                    problem.accuracy = (problem.solvedBy * 1.0 / attemptedby) * 100;
                
                problemsList.add(problem);
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        Sorter.sortByName(problemsList, 1);
        return problemsList;
    }

    public static ProblemDetails getProblemDetails(String code, String handle) {
        //get problem tags by author
        List<String> problemTags = getProblemTags(code);
        
        //get problem tags by coder
        List<String> coderTags = getCoderTags(code, handle);
        
        ProblemDetails problemDetails;
        String query = "SELECT name, statement, difficulty, author, time_limit, "
                + "source_limit, memory_limit from problems where code = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                problemDetails = new ProblemDetails(code, resultSet.getString("name"),
                        resultSet.getString("statement"), resultSet.getString("author"),
                        resultSet.getInt("time_limit"), resultSet.getInt("source_limit"), 
                        resultSet.getInt("memory_limit"), resultSet.getInt("difficulty"),
                        problemTags, coderTags);
                return problemDetails;
            }
            
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        
        
        return new ProblemDetails(code);
    }

    public static List<String> getTagsList() {
        List<String> tagsList = new ArrayList<String>();
        String query = "SELECT tag from alltags";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                tagsList.add(resultSet.getString("tag"));
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        return tagsList;
    }

    private static List<String> getProblemTags(String code) {
        String query = "SELECT tag from problem_tags where pcode = ?";
        List<String> problemTags = new ArrayList<String>();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                problemTags.add(resultSet.getString("tag"));
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        return problemTags;
    }

    private static List<String> getCoderTags(String code, String handle) {
        String query = "SELECT tag from coder_tags where handle = ? and pcode = ?";
        List<String> coderTags = new ArrayList<String>();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            preparedStatement.setString(2, code);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                coderTags.add(resultSet.getString("tag"));
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        return coderTags;
    }

    public static void addCoderTags(String handle, String code, List<String> tagsList) {
        PreparedStatement preparedStatement;
        
        for(String tag : tagsList) {
            String query = "INSERT into coder_tags(handle, pcode, tag) values('" + handle + "', '"
                    + code + "', '" + tag + "')";
            try {
                preparedStatement = connection.prepareStatement(query);
                int executeUpdate = preparedStatement.executeUpdate();
            } catch(Exception exception) {
                JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
            }
        }
    }

    public static ProblemStats getProblemStats(String code) {
        ProblemStats problemStats;
        String query = "SELECT name, solvedby, attemptedby, submissions, ac, cte,"
                + " wa, rte, tle from problems where code = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                problemStats = new ProblemStats(code, resultSet.getString("name"), 
                        resultSet.getInt("submissions"), resultSet.getInt("solvedby"), 
                        resultSet.getInt("attemptedby"), resultSet.getInt("ac"), 
                        resultSet.getInt("cte"), resultSet.getInt("rte"), resultSet.getInt("wa"), 
                        resultSet.getInt("tle"));
                return problemStats;
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());
        }
        return new ProblemStats(code);
    }

    public static int getSubmissionsCount(String handle, String code) {
        String query = "SELECT count(*) from submissions where handle = ? and pcode = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            preparedStatement.setString(2, code);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());            
        }
        return 0;
    }

    static void addSubmission(Submission submission) {
        String query = "INSERT into submissions(SID, handle, pcode, language, source_code, "
                + "status, error_code, time, memory, date_time) values ('" + submission.sid
                +"', '" + submission.handle + "', '"
                + submission.code + "', '" + submission.language + "', '" + submission.sourceCode 
                + "', '" + submission.status + "', '" + submission.errorCode + "', '" + submission.time
                + "', '" + submission.memory + "', '" + submission.dateTime + "')";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            int executeUpdate = preparedStatement.executeUpdate();
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());            
        }
    }

    static void updateCoderTable(Submission submission) {
        Coder coder = new Coder(submission.handle);
        Coder cd = Database.getCoderDetails(coder);
        String query = "";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        try {
            if(submission.status.equals("Accepted")) {
                query = "UPDATE coder SET problems_solved = ?, submissions = ?, ac = ?, rating = ? where handle = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, cd.problemsSolved + 
                        getProblemSolvedIncrement(submission.handle, submission.code));
                preparedStatement.setInt(2, cd.submissions + 1);
                preparedStatement.setInt(3, cd.accepted + 1);
                preparedStatement.setInt(4, cd.rating + getRatingChange(submission));
                preparedStatement.setString(5, submission.handle);
            }
            else if(submission.status.equals("Compilation Error")) {
                query = "UPDATE coder SET submissions = ?, cte = ? where handle = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, cd.submissions + 1);
                preparedStatement.setInt(2, cd.compilationErrors + 1);
                preparedStatement.setString(3, submission.handle);
            }
            else if(submission.status.equals("Runtime Error")) {
                query = "UPDATE coder SET submissions = ?, rte = ?, rating = ? where handle = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, cd.submissions + 1);
                preparedStatement.setInt(2, cd.runtimeErrors + 1);
                preparedStatement.setInt(3, cd.rating + getRatingChange(submission));
                preparedStatement.setString(4, submission.handle);
            }
            else if(submission.status.equals("Wrong Answer")) {
                query = "UPDATE coder SET submissions = ?, wa = ?, rating = ? where handle = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, cd.submissions + 1);
                preparedStatement.setInt(2, cd.wrongAnswers + 1);
                preparedStatement.setInt(3, cd.rating + getRatingChange(submission));
                preparedStatement.setString(4, submission.handle);
            }
            else {
                query = "UPDATE coder SET submissions = ?, tle = ?, rating = ? where handle = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, cd.submissions + 1);
                preparedStatement.setInt(2, cd.timeLimitExceeds + 1);
                preparedStatement.setInt(3, cd.rating + getRatingChange(submission));
                preparedStatement.setString(4, submission.handle);
            }
            int executeUpdate = preparedStatement.executeUpdate();
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server codertable: " + exception.getMessage());                        
        }
    }

    static void updateProblemsTable(Submission submission) {
        ProblemStats oldProblemStats = Database.getProblemStats(submission.code);
        String query = "";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        try {
            if(submission.status.equals("Accepted")) {
                query = "UPDATE problems SET solvedby = ?, submissions = ?, ac = ? where code = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, oldProblemStats.solvedBy + 
                        getProblemSolvedIncrement(submission.handle, submission.code));
                preparedStatement.setInt(2, oldProblemStats.submissions + 1);
                preparedStatement.setInt(3, oldProblemStats.accepted + 1);
                preparedStatement.setString(4, submission.code);
            }
            else if(submission.status.equals("Compilation Error")) {
                query = "UPDATE problems SET submissions = ?, cte = ? where code = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, oldProblemStats.submissions + 1);
                preparedStatement.setInt(2, oldProblemStats.compilationErrors + 1);
                preparedStatement.setString(3, submission.code);
            }
            else if(submission.status.equals("Runtime Error")) {
                query = "UPDATE problems SET submissions = ?, rte = ? where code = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, oldProblemStats.submissions + 1);
                preparedStatement.setInt(2, oldProblemStats.runtimeErrors + 1);
                preparedStatement.setString(3, submission.code);
            }
            else if(submission.status.equals("Wrong Answer")) {
                query = "UPDATE problems SET submissions = ?, wa = ? where code = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, oldProblemStats.submissions + 1);
                preparedStatement.setInt(2, oldProblemStats.wrongAnswers + 1);
                preparedStatement.setString(3, submission.code);
            }
            else {
                query = "UPDATE problems SET submissions = ?, tle = ? where code = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, oldProblemStats.submissions + 1);
                preparedStatement.setInt(2, oldProblemStats.timeLimitExceeds + 1);
                preparedStatement.setString(3, submission.code);
            }
            int executeUpdate = preparedStatement.executeUpdate();
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());                        
        }
        
        try {
            int submissionCount = Database.getSubmissionsCount(submission.handle, submission.code);
            if(submissionCount == 0) {  //this is the first submission
                query = "UPDATE problems SET attemptedby = ? where code = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, oldProblemStats.attemptedBy + 1);
                preparedStatement.setString(2, submission.code);
                preparedStatement.executeUpdate();
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());                        
        }
    }

    private static int getProblemSolvedIncrement(String handle, String code) {
        String query = "SELECT count(*) from submissions where handle = ? and pcode = ? and status = 'Accepted'";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            preparedStatement.setString(2, code);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                if(resultSet.getInt(1) > 0)
                    return 0;
                else return 1;
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());            
        }
        return 1;
    }

    public static List<Problem> getSolvedProblemsList(String handle) {
        String query = "SELECT pcode from submissions where handle = ? and status = 'Accepted'";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        HashMap hashMap = new HashMap();
        List<Problem> solvedProblemsList = new ArrayList<Problem>();
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                hashMap.put(resultSet.getString(1), 1);
            }
            
            Iterator it = hashMap.entrySet().iterator();
            while(it.hasNext()) {
                solvedProblemsList.add(new Problem(((Map.Entry)it.next()).getKey().toString()));
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());            
        }
        return solvedProblemsList;
    }

    public static List<Problem> getAttemptedUnsolvedProblemsList(String handle) {
        String query = "SELECT pcode from submissions where handle = ? and status <> 'Accepted'";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        HashMap hashMap = new HashMap();
        List<Problem> attemptedUnsolvedProblemsList = new ArrayList<Problem>();
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                hashMap.put(resultSet.getString(1), 1);
            }
            
            Iterator it = hashMap.entrySet().iterator();
            while(it.hasNext()) {
                attemptedUnsolvedProblemsList.add(new Problem(((Map.Entry)it.next()).getKey().toString()));
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());            
        }
        return attemptedUnsolvedProblemsList;
    }

    public static List<Submission> getSubmissionsList(String handle, String code) {
        String query = "SELECT  SID, date_time, handle, status, error_code, time, language "
                + "from submissions where handle = ? and pcode = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Submission> submissions = new ArrayList<Submission>();
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            preparedStatement.setString(2, code);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                submissions.add(new Submission(resultSet.getString(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), 
                        resultSet.getDouble(6), resultSet.getString(7)));
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());            
        }
        Sorter.sortSubmissionsByDateTime(submissions, 2); //newer submission first
        return submissions;
    }

    private static int getRatingChange(Submission submission) {
        int difficulty = getProblemDifficulty(submission.code);
        int acSubmissions = getNumberOfACSubmissions(submission.handle, submission.code);
        if(acSubmissions > 0) { //the problem has been already got accepted by user
            return 0; //no rating change for this submission
        }
        
        if(submission.status.equals("Accepted")) {
            return 2*difficulty;
        }
        else {  //in case of TLE, RTE, WA
            return -1*difficulty;
        }        
    }

    private static int getProblemDifficulty(String code) {
        String query = "SELECT difficulty from problems where code = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());            
        }
        return -1;
    }

    private static int getNumberOfACSubmissions(String handle, String code) {
        String query = "SELECT count(*) from submissions where handle = ? and pcode = ? and status = 'Accepted'";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, handle);
            preparedStatement.setString(2, code);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : " + exception.getMessage());            
        }
        return 0;
    }
}
