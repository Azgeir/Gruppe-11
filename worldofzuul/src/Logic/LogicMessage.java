/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.Serializable;

/**
 *
 * @author HCHB
 */
public class LogicMessage implements Serializable {
    
    private String message;
    
    LogicMessage(){
        this.message = "";
    }
    
    LogicMessage(String message){
        this.message = message;
    }
    
    String readAndDelete(){
        String returnMessage = this.message;
        this.message = "";
        return returnMessage;
    }
    
    void appendMessage(String message){
        this.message += message;
    }
    
}
