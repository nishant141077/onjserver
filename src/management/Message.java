/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import java.io.Serializable;

/**
 *
 * @author nishant
 */
public class Message implements Serializable {
    public int code;
    public String handle;
    public boolean status;
    public String password;
    public String displayMessage;
}
