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
public class User implements Serializable {
    private static final long serialVersionUID = 101;
    public String handle;
    public String name;
    public String password;
    public String secQuestion;
    public String answer;
    public int problemSolved;
    public String gender;
    
    public User(String handle, String name, String password, String secQuestion,
            String answer, int problemSolved, String gender) {
        this.handle = handle;
        this.name = name;
        this.password = password;
        this.secQuestion = secQuestion;
        this.answer = answer;
        this.problemSolved = problemSolved;
        this.gender = gender;
    }
    
    public User(String handle, String password) {
        this.handle = handle;
        this.password = password;
    }
}
