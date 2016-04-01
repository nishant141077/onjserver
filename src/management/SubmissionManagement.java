/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import entities.ProblemDetails;
import entities.Submission;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.String;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author nishant
 */
public class SubmissionManagement {

    private Submission submission;
    private String extension;
    private String sourceFileName;
    private String parentPath = "/home/nishant/onj";
    private String compiler;
    private int statusCode; //1 - AC, 2 - CTE, 3 - WA, 4 - RTE, 5 - TLE
    private String sourceFilePath;
    private String sourceExecPath;
    private String scriptFileName;
    private String scriptFilePath;
    private int submissionNo;
    private ProblemDetails problemDetails;
    private String[] scriptResult = new String[20];
    private String RTE  = "Command terminated by signal";
    private String rteType;
    private double timeTaken;
    private int size;
    private String waScriptFilePath;
    private String diffResultFilePath;
    
    public SubmissionManagement(Submission submission, ProblemDetails problemDetails) {
        this.submission = submission;
        this.problemDetails = problemDetails;
    }
    
    public Submission getSubmissionStatus() throws Exception {
        /**create source code file
         * file name format : handle_code_no#.extension
         */ 
        sourceFileName = getSourceFileName();
        writeInSourceFile();
        
        /**
         * compile source file and create exec file
         */
        compiler = getCompiler();
        if(compileSourceFile() == false) {
            return prepareSubmissionStatus();
        } 
        
        /**
         * run exec file against input file
         */
        scriptFileName = getScriptFileName();
        scriptFilePath = getScriptFilePath();
        writeInScriptFile();
        grantPermissionToScript();
        if(runCode() == false) {  //RTE is there
            timeTaken = getTimeTaken();
            return prepareSubmissionStatus();
        }
        
        /**
         * check for TLE
         */
        timeTaken = getTimeTaken();
        if(checkForTLE()) {  //TLE is there
            return prepareSubmissionStatus();
        }
        
        /**
         * check for WA
         */
        checkForWA();
        return prepareSubmissionStatus();
    }

    private String getFileExtension() {
        if(submission.language.equals("C")) {
            return ".c";
        }
        else if(submission.language.equals("C++")) {
            return ".cpp";
        }
        else return ".java";
    }

    private String getSourceFileName() {
        submissionNo = Database.getSubmissionsCount(submission.handle, submission.code) + 1;
        extension = getFileExtension();
        return submission.handle + "_" + submission.code + "_" + submissionNo;
    }

    private void writeInSourceFile() throws IOException {
        sourceFilePath = parentPath + "/submissions/codes/" + sourceFileName + extension;
        File sourceFile  = new File(sourceFilePath);
        if(!sourceFile.exists()) {
            try {
                sourceFile.createNewFile();
            } catch (IOException ex) {
                //file cannot be created
                JOptionPane.showMessageDialog(null, "Error in creating source file : " + ex.getMessage());
            }
        }
        
        FileWriter fw = new FileWriter(sourceFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write(submission.sourceCode);
        bw.close();
        
    }

    private String getCompiler() {
        if(submission.language.equals("C")) {
            return "gcc";
        }
        else if(submission.language.equals("C++")) {
            return "g++";
        }
        else return "javac";
    }

    private boolean compileSourceFile() throws IOException {
        if(compiler.equals("javac") == false) {
            sourceExecPath = parentPath + "/submissions/exec/" + sourceFileName;
            Process p = Runtime.getRuntime().exec(compiler + " " + sourceFilePath + " -o " + sourceExecPath);
            try {
                p.waitFor();
            } catch(Exception exception) {
                JOptionPane.showMessageDialog(null, "Server : Exception in compiling code - " +  exception.getMessage());
            }
            return checkForCompilationError();
        }
        return true;
    }

    private boolean checkForCompilationError() {
        File execFile = new File(sourceExecPath);
        if(!execFile.exists()) {
            statusCode = 2;
            return false;
        }
        return true;
    }

    private Submission prepareSubmissionStatus() {
        Submission submissionStatus = null;
        
        if(statusCode == 2) { // CTE
            submissionStatus = new Submission(submission.handle, submission.code, submission.language,
                    submission.sourceCode, "Compilation Error", "", 0.00, 0.00, getDateTime());    
        }
        else if(statusCode == 4) { //RTE
            submissionStatus = new Submission(submission.handle, submission.code, submission.language,
                    submission.sourceCode, "Runtime Error", rteType, timeTaken, 0.00, getDateTime());
        }
        else if(statusCode == 5) { //TLE
            submissionStatus = new Submission(submission.handle, submission.code, submission.language, 
                    submission.sourceCode, "Time Limit Exceeded", "", timeTaken, 0.00, getDateTime());
        }
        else if(statusCode == 3) { //WA
            submissionStatus = new Submission(submission.handle, submission.code, submission.language, 
                    submission.sourceCode, "Wrong Answer", "", timeTaken, 0.00, getDateTime());
        }
        else if(statusCode == 1) { //AC
            submissionStatus = new Submission(submission.handle, submission.code, submission.language, 
                    submission.sourceCode, "Accepted", "", timeTaken, 0.00, getDateTime());
        }
        
        submissionStatus.sid = getSubmissionID();
        //entry in database
        updateTables(submissionStatus);
        return submissionStatus;
    }

    private String getScriptFileName() {
        return submission.handle + "_" + submission.code + "_" + submissionNo + ".sh";
    }

    private String getScriptFilePath() {
        return parentPath + "/scripts/" + scriptFileName;
    }

    String content;
    private void writeInScriptFile() throws IOException {
        double relaxedTimeLimit = problemDetails.timeLimit + 0.5;
        content = "time timeout " + relaxedTimeLimit + " " + parentPath +  
                "/submissions/exec/./" + sourceFileName + " < " + getInputFilePath() + 
                " > " + getGeneratedOutputFilePath();
        File execFile = new File(scriptFilePath);
        if(!execFile.exists()) {
            try {
                execFile.createNewFile();
            } catch(Exception exception) {
                JOptionPane.showMessageDialog(null, "Error in creating script file : " + exception.getMessage());
            }
        }
        
        FileWriter fw = new FileWriter(execFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
    }

    private String getInputFilePath() {
        return parentPath + "/testdata/input/" + submission.code + "_in";
    }

    private String getGeneratedOutputFilePath() {
        return parentPath + "/submissions/output/" + submission.handle + "_" + submission.code +
                "_" + submissionNo;
    }

    private boolean runCode() throws IOException {
        Process p = Runtime.getRuntime().exec(parentPath + "/scripts/./" + scriptFileName);
        try {
            p.waitFor();
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : Exception in running script - " + exception.getMessage());
        }

        InputStream is = p.getErrorStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        size = 0;
        while(true) {
            String s = br.readLine();
            if(s == null)
                break;
            //System.out.println(s);
            scriptResult[size++] = s;
        }
        return !checkForRuntimeErrors();
    }

    private void grantPermissionToScript() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("chmod +x " + scriptFilePath);
        p.waitFor();
    }

    private int getPid(Process p) {
        Class<?> cImpl = p.getClass();
        try {
            Field fPid = cImpl.getDeclaredField("pid"); 
            if(!fPid.isAccessible()) {
                fPid.setAccessible(true);
            }
            return fPid.getInt(p);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            return -1;
        }
    }
    
    private boolean filePresent(int pid) {
        File file = new File("/proc/" + pid + "/status");
        if(!file.exists()) {
            return false;
        }
        return true;
    }

    private boolean checkForRuntimeErrors() {
        int i;
        for(i=0;i<size;i++) {
            int lastSpace = scriptResult[i].lastIndexOf(' ');
            String s = scriptResult[i].substring(0, lastSpace);
            if(s.equals(RTE)) {
                int rteSignal = Integer.parseInt(scriptResult[i].substring(lastSpace+1));
                statusCode = 4;
                rteType = getRteType(rteSignal);
                return true;
            }
        }
        return false;
    }

    private String getRteType(int rteSignal) {
        switch(rteSignal) {
            case 6: return "SIGABRT";
            case 8: return "SIGFPE";
            case 11: return "SIGSEGV";
            default: return "";
        }
    }

    private double getTimeTaken() {
        for(int i = 0;i<size;i++) {
            int firstSpace = scriptResult[i].indexOf(' ');
            int start = firstSpace - 4;
            start = Math.max(start, 0);
            String user = scriptResult[i].substring(start, firstSpace);
            if(user.equals("user")) {
                return Double.valueOf(scriptResult[i].substring(0, start));
            }
        }
        return 0.00;
    }

    private boolean checkForTLE() {
        if(timeTaken > problemDetails.timeLimit) {
            statusCode = 5;
            return true;
        }
        return false;
    }

    private void checkForWA() throws IOException {
        writeInWAScriptFile();
        grantPermissionToWAScript();
        Process p = Runtime.getRuntime().exec(parentPath + "/scripts/./WA_" + sourceFileName +".sh");
        try {
            p.waitFor();
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : Exception while running diff - " + exception.getMessage());
        }
        
        //check in temp folder file
        File diffResultFile = new File(diffResultFilePath);
        if(diffResultFile.length() > 0) { //WA
            statusCode = 3;
        }
        else statusCode = 1; //AC
    }
   
    private void writeInWAScriptFile() {
        String correctOutputPath = parentPath + "/testdata/output/" + submission.code + "_out";
        String generatedOutputPath = parentPath + "/submissions/output/" + sourceFileName;
        diffResultFilePath = parentPath + "/temp/diff_" + sourceFileName;
        waScriptFilePath = parentPath + "/scripts/WA_" + sourceFileName + ".sh";
        
        File waScriptFile = new File(waScriptFilePath);
        try {
            if(!waScriptFile.exists()) {
                waScriptFile.createNewFile();
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : Error creating wa script file - " + exception.getMessage());
        }
        
        try {
            FileWriter fw = new FileWriter(waScriptFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write("diff --ignore-blank-lines " + generatedOutputPath + " " +
                    correctOutputPath + " > " + diffResultFilePath);
            bw.close();
        } catch(Exception exception) {
             JOptionPane.showMessageDialog(null, "Server : Error writing in wa script file - " + exception.getMessage());           
        }
    }

    private void grantPermissionToWAScript() {
        try {
            Process p = Runtime.getRuntime().exec("chmod +x " + waScriptFilePath);
            p.waitFor();
        } catch(Exception exception) {
            JOptionPane.showMessageDialog(null, "Server : error in granting permission to waScriptFile - " + exception.getMessage());      
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void updateTables(Submission submission) {
        //update coder table
        Database.updateCoderTable(submission);
        System.out.println("coder table updated");
        
        //update problems table
        Database.updateProblemsTable(submission);
        System.out.println("problems table updated");
        
        //make a new entry of this submisison in submissions table
        Database.addSubmission(submission);
        System.out.println("submission added in submissions table");
    }

    private String getSubmissionID() {
        long f3 = 0;
        for(int i = 0;i<submission.handle.length();i++) {
            f3 = (f3 + (int)(submission.handle.charAt(i))) % 1000;
        }
        
        for(int i = 0;i<submission.code.length();i++) {
            f3 = (f3 + (int)(submission.code.charAt(i))) % 1000;
        }
        
        long l4 = submissionNo;
        DecimalFormat df3 = new DecimalFormat("000");
        DecimalFormat dl4 = new DecimalFormat("0000");
        return df3.format(f3) + dl4.format(l4);
    }
    
}
