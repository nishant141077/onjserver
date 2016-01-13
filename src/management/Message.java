/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import entities.User;
import java.io.Serializable;

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
}
