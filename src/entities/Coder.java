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
public class Coder implements Serializable {
    private static final long serialVersionUID = 111;
    public String handle;
    public int rating;
    public int problemsSolved;
    public int submissions;
    public int accepted;
    public int compilationErrors;
    public int wrongAnswers;
    public int runtimeErrors;
    public int timeLimitExceeds;
    public int contests;
    public String aboutMe;
    public String name;
    
    public Coder() {
        
    }
    
    public Coder(String handle) {
        this.handle = handle;
    }
}
