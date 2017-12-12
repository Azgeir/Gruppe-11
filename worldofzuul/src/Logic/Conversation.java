/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The class is located in the logic layer.
package Logic;

// Import:
import java.io.Serializable;

/**
 * This class represents a conversation. The class implements Serializable.
 * 
 * @author Aske Wulf
 */
class Conversation implements Serializable {
    
    /**
     * Data fields.
     * messageClass: instance of LogicMessage for storing strings to be read
     * later.
     * TECHTALK1: tech dude's first statement
     * TECHTALK2: tech dude's second statement
     * TECHTALK3: tech dude's third statement
     * TECHANSWER1: player's first statement
     * TECHANSWER2: player's second statement
     * TECHANSWER3: player's third statement
     * LETSGO: player's last statement (positive)
     * FUCKOFF: negative statement that increases tech dude's hostility
     */
    private LogicMessage messageClass;
    
    // Tech dude statements
    private final String TECHTALK1 = "Is the Zuul gone?";
    private final String TECHTALK2 = "The arrival of Zuul has triggered the "
        + "station's quarantine mechanism, causing the doors to all the escape "
        + "pods to be locked.";
    private final String TECHTALK3 = "Yes, I can override the mechanism and "
        + "unlock one of the pods, but I will have to be in the dock to do "
        + "this.";
    private final String TECHTALK4 = "I can overload the reactor, causing it to"
        + " self-destruct along with the whole space station. This will give "
        + "you a short time to get off the station before it blows up. But I "
        + "must be near the reactor to overload it.";
    // Player responses
    private final String TECHANSWER1 = "For now. What has happened to the "
        + "station?";
    private final String TECHANSWER2 = "Can you help me get off this station?";
    private final String TECHANSWER3 = "Anything else you can help with?";
    private final String LETSGO = "Let's go!";
    private final String FUCKOFF = "Fuck off.";
    
    /**
     * This is an empty no-arg constructor.
     */
    Conversation() {
    }
    
    /**
     * This constructor calls it's own no-args constructor and sets messageClass
     * 
     * @param messageClass instance of LogicMessage for storing strings for to
     * read later.
     */
    Conversation(LogicMessage messageClass){
        this();
        this.messageClass = messageClass;
    }
    
    /**
     * This method prints the tech dude's statements when conversing with the
     * tech dude. The statement is determined by the argument string.
     * 
     * @param string determines the statement to be printed. The string
     * is created in Game by adding a counter (which indicates the progress in
     * the conversation) to the name of the character.
     */
    void talk(String string){
        if (string == null){
            messageClass.appendMessage("String is null.");
        }
        else switch (string){
            case "TechDude1":
                messageClass.appendMessage(TECHTALK1);
                break;
            case "TechDude2":
                messageClass.appendMessage(TECHTALK2);
                break;
            case "TechDude3":
                messageClass.appendMessage(TECHTALK3);
                break;
            case "TechDude4":
                messageClass.appendMessage(TECHTALK4);
                break;
            default:
                messageClass.appendMessage("I don't know what you want.");
                break;
        }
    }
    
    /**
     * This class prints the player's options when conversing with the tech
     * dude. The options are determined by the string argument.
     * 
     * @param string determines the options. It is created in Game by
     * adding a counter (which indicates the progress in the conversation) to
     * the name of the character.
     */
    void options(String string){
        // Check if string is null
        if (string == null){
            messageClass.appendMessage("String is null.");
        }
        // Print conversation options depending on the string
        else switch (string) {
            case "TechDude1":
                messageClass.appendMessage("1: " + TECHANSWER1);
                messageClass.appendMessage("2: " + FUCKOFF);
                break;
            case "TechDude2":
                messageClass.appendMessage("1: " + TECHANSWER2);
                messageClass.appendMessage("2: " + FUCKOFF);
                break;
            case "TechDude3":
                messageClass.appendMessage("1: " + TECHANSWER3);
                messageClass.appendMessage("2: " + FUCKOFF);
                messageClass.appendMessage("3: " + LETSGO);
                break;
            case "TechDude4":
                messageClass.appendMessage("1: " + LETSGO);
                messageClass.appendMessage("2: " + FUCKOFF);
                break;
            default:
                messageClass.appendMessage("I don't know what you mean.");
                break;
        }
    }
}
