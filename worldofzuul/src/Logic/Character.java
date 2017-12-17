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
 * This class represents a character. This is the superclass for Hero, TechDude,
 * and Zuul. The class implements the interface Serializable.
 * 
 * @author HCHB
 */

abstract class Character implements Serializable {
    
    /**
     * currentRoom: character's current room.
     */
    private Room currentRoom;
    /**
     * characterInitiative: used to determine whose turn it is.
     */
    private double characterInitiative;
    /**
     * speedFactor: used to update character initiative.
     */
    private double speedFactor;
    /**
     * name: used to identify character type in other classes.
     */
    private String name;
    /**
     * message: used for various purposes throughout the game.
     */
    private String message;

    /**
     * This constructor creates a character with default initiative and speed
     * factor.
     */
    Character() {
        this.characterInitiative = 0;
        this.speedFactor = 1;
    }

    /**
     * This constructor creates a character with the specified current room and
     * name. The character has default character initiative and speed factor via
     * constructor chaining.
     * 
     * @param currentRoom instance  of Room that indicates current room of
     * the character.
     * @param name String that indicates character type.
     */
    Character(Room currentRoom, String name) {
        this();
        this.currentRoom = currentRoom;
        this.name = name;
        this.currentRoom.addCharacterInRoom(name);
    }

    /**
     * This constructor creates a character with the specified current room,
     * name, and speed factor. The character has default character initiative 
     * (via constructor chaining), while the default speed factor is
     * overwritten.
     * 
     * @param currentRoom indicates current room of character.
     * @param name indicates character type.
     * @param speedFactor used to update character initiative during game play.
     */
    Character(Room currentRoom, String name, double speedFactor) {
        this(currentRoom, name);
        this.speedFactor = speedFactor;
    }

    /**
     * This constructor creates a character with the specified current room,
     * name, speed factor, and initiative. This constructor is used when
     * creating more Zuuls during the game, as their initiative has to be set to
     * a value greater than 0.
     * 
     * @param currentRoom indicates current room of character.
     * @param name indicates character type.
     * @param speedFactor used to update character initiative during game play.
     * @param initiative used to determine whose turn it is.
     */
    Character(Room currentRoom, String name, double speedFactor,
        double initiative) {
        this(currentRoom, name, speedFactor);
        this.characterInitiative = initiative;
    }
    
    /**
     * This method returns a command. It is implemented in Hero, TechDude, and 
     * Zuul.
     * 
     * @param commands instance of CommandWords which represents the valid
     * commands
     * @param GUICommand String sent from GUI controller, which represents the
     * command
     * 
     * @return Command object, which represents the specified command.
     */
    abstract Command getCommand(CommandWords commands, String GUICommand);
    
    /**
     * This method is used to perform a command. It is implemented in Zuul, Hero
     * and TechDude.
     * 
     * @param command commmand to be executed
     * 
     * @return double value depending on the command being executed (most
     * commands return 0).
     */
    abstract double performCommand(Command command);

    /**
     * This method is called from processCommand() in Game in response to a
     * "stay" command. The method increases the character's initiative, 
     * simulating the act of staying in the current room.
     * 
     * @param command represents the command being executed whose commandWord
     * is "stay".
     */
    void stay(Command command) {
        this.characterInitiative += 10 * this.speedFactor;
    }
    
    /**
     * This method is called from processCommand() in Game in response to a
     * "go" command. This method is implemented in Hero, TechDude, and Zuul.
     * 
     * @param command represents the command being executed whose commandWord
     * is "go".
     */
    abstract void go(Command command);

    /**
     * This method returns the current room of the character.
     * 
     * @return currentRoom, which represents the current room of the character.
     */
    Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * This method sets the current room of the character to the specified room.
     * 
     * @param currentRoom the new current room of the character.
     */
    void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * This method returns the character's initiative.
     * 
     * @return characterInitiative
     */
    double getCharacterInitiative() {
        return this.characterInitiative;
    }

    /**
     * This method sets the character's initiative to the specified value.
     * 
     * @param characterInitiative the new value of the character's initiative.
     */
    void setCharacterInitiative(double characterInitiative) {
        this.characterInitiative = characterInitiative;
    }

    /**
     * This method returns the character's speed factor.
     * 
     * @return speedFactor
     */
    double getSpeedFactor() {
        return this.speedFactor;
    }

    /**
     * This method sets the character's speed factor to the specified value.
     * 
     * @param speedFactor the new value of the character's speed factor.
     */
    void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    /**
     * This method returns the name of the character.
     * 
     * @return name
     */
    String getName() {
        return this.name;
    }
    
    /**
     * This method sets the value of the message.
     * 
     * @param message the new value of message
     */
    void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * This method returns the value of the message.
     * 
     * @return message
     */
    String getMessage() {
        return this.message;
    }
    
    /**
     * This method clears the value of the message by setting it to null.
     */
    void clearMessage() {
        this.message = null;
    }
}
