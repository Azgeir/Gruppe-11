/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.Serializable;

/**
 * This class is used to hold a message. This message can later be sent to the
 * GUI layer. The methods in the class has default visibility as they are only
 * used from within this package.
 * 
 * @author HCHB
 */
class LogicMessage implements Serializable {
    /**
     * message: String message
     */
    private String message;
    
    /**
     * This constructor creates a LogicMessage object with an empty message.
     */
    LogicMessage() {
        this.message = "";
    }
    
    /**
     * This constructor creates a LogicMessage object with the specified
     * message.
     * 
     * @param message String message
     */
    LogicMessage(String message) {
        this.message = message;
    }
    
    /**
     * This method reads and deletes the message.
     * 
     * @return the String message before it is deleted.
     */
    String readAndDelete() {
        String returnMessage = this.message;
        this.message = "";
        return returnMessage;
    }
    
    /**
     * This method is used to append a String to the message.
     * 
     * @param message String to be appended to the message
     */
    void appendMessage(String message) {
        this.message += message+"\n";
    }
}
