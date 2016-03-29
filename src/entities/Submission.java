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
public class Submission implements Serializable {
    private static final long serialVersionUID = 141;
    public String handle;
    public String code;
    public String language;
    public String sourceCode;
    public String status;
    public String errorCode;
    public double time;
    public double memory;
    public String dateTime; //yyyy-mm-dd hh:mm:ss
    public String sid; //submission id
    
    public Submission(){}
    
    public Submission(String handle, String code, String language, String sourceCode) {
        this.handle = handle;
        this.code = code;
        this.language = language;
        this.sourceCode = sourceCode;
    }
    
    public Submission(String handle, String code, String language, String sourceCode, 
            String status, String errorCode, double time, double memory, String dateTime) {
        this.handle = handle;
        this.code = code;
        this.language = language;
        this.sourceCode = sourceCode;
        this.status = status;
        this.errorCode = errorCode;
        this.time = time;
        this.memory = memory;
        this.dateTime = dateTime;
    }
}
