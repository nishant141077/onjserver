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
public class ProblemStats implements Serializable {
    private static final long serialVersionUID = 141;
    public String code;
    public String name;
    public int submissions;
    public int solvedBy;
    public int attemptedBy;
    public int accepted;
    public int compilationErrors;
    public int runtimeErrors;
    public int wrongAnswers;
    public int timeLimitExceeds;
    
    public ProblemStats(String code) {
        this.code = code;
    }
    
    public ProblemStats(String code, String name, int submissions, int solvedBy,
            int attemptedBy, int accepted, int compilationErrors, int runtimeErrors, int wrongAnswers, 
            int timeLimitExceeds) {
        this.code = code;
        this.name = name;
        this.submissions = submissions;
        this.solvedBy = solvedBy;
        this.attemptedBy = attemptedBy;
        this.accepted = accepted;
        this.compilationErrors = compilationErrors;
        this.runtimeErrors = runtimeErrors;
        this.wrongAnswers = wrongAnswers;
        this.timeLimitExceeds = timeLimitExceeds;
    }
}
