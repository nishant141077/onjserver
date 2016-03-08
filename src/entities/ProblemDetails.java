/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author nishant
 */
public class ProblemDetails implements Serializable {
    private static final long serialVersionUID = 131;
    public String code;
    public String name;
    public String statement;
    /*
    public int submissions;
    public int solvedBy;
    public int attemptedBy;
    public int accepted;
    public int compilationErrors;
    public int runtimeErrors;
    public int wrongAnswers;
    public int timeLimitExceeds;
    */
    public String author;
    public int timeLimit;
    public int sourceLimit;
    public int memoryLimit;
    public int difficulty;

    
    public ProblemDetails(String code) {
        this.code = code;
    }
    
    /*public ProblemDetails(String code, String name, String statement, int submissions, int solvedBy,
            int attemptedBy, int accepted, int compilationErrors, int runtimeErrors, int wrongAnswers, 
            int timeLimitExceeds, int difficulty) {
        this.code = code;
        this.name = name;
        this.statement = statement;
        this.submissions = submissions;
        this.solvedBy = solvedBy;
        this.attemptedBy = attemptedBy;
        this.accepted = accepted;
        this.compilationErrors = compilationErrors;
        this.runtimeErrors = runtimeErrors;
        this.wrongAnswers = wrongAnswers;
        this.timeLimitExceeds = timeLimitExceeds;
        this.difficulty = difficulty;
    }*/
    
    public ProblemDetails(String code, String name, String statement, String author,
            int timeLimit, int sourceLimit, int memoryLimit, int difficulty) {
        this.code = code;
        this.name = name;
        this.statement = statement;
        this.author = author;
        this.timeLimit = timeLimit;
        this.sourceLimit = sourceLimit;
        this.memoryLimit = memoryLimit;
        this.difficulty = difficulty;
    }
}
