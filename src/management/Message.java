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
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author nishant
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 100;
    public int code;
    public User user;
    public boolean status;
    public String displayMessage;
    public Coder coder;
    public List<Problem> problemsList;
    public ProblemDetails problemDetails;
    public List<String> tagsList;
    public ProblemStats problemStats;
    public Submission submission;
    public List<Submission> mySubmissions;
}
