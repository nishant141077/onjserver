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
public class Problem implements Serializable {
    private static final long serialVersionUID = 121;
    public String name;
    public String code;
    public int difficulty;
    public int solvedBy;
    public double accuracy;
    
    public Problem() {}
    
    public Problem(String name, String code, int difficulty, int solvedBy, double accuracy) {
        this.accuracy = accuracy;
        this.code = code;
        this.difficulty = difficulty;
        this.name = name;
        this.solvedBy = solvedBy;
    }
}
