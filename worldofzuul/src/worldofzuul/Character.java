/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

import java.io.Serializable;

/**
 * This class represents a character. This is the superclass for Hero, TechDude,
 * and Zuul. The class implements the interface Serializable, which is used for
 * saving the game.
 * 
 * @author HCHB
 */

public class Character implements Serializable {

    /**
     * Data fields.
     * currentRoom: character's current room
     * characterInitiative: used to determine whose turn it is
     * speedFactor: used to update character initiative
     * name: used to identify character type in other classes
     * hostility: used when conversing with a character (currently only used for
     * TechDude, but it is put in Character to allow future development of
     * characters that the player can converse with).
     * message: used for various purposes throughout the game
     */
    private Room currentRoom;
    private double characterInitiative;
    private double speedFactor;
    private String name;
    private int hostility;
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
     * @param currentRoom indicates current room of character.
     * @param name indicates character type.
     */
    Character(Room currentRoom, String name) {
        this();
        this.currentRoom = currentRoom;
        this.name = name;
    }

    /**
     * This constructor creates a character with the specified current room,
     * name, and speed factor. The character has default character initiative 
     * (via constructor chaining), while the default speed factor is overwritten.
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
     * This method returns a command. It is overridden in Hero, TechDude, and 
     * Zuul, so it is never called on Character.
     * 
     * @param commands represents the valid commands
     * @param GUICommand sent from GUI controller, which represents the command
     * 
     * @return Command object, which represents the specified command.
     */
    Command getCommand(CommandWords commands, String GUICommand) {
        String word1 = null;
        String word2 = null;
        String word3 = null;
        return new Command(commands.getCommandWord(word1), word2, word3);
    }
    
    /**
     * This method is used to perform a command. It is overridden in Zuul, Hero
     * and TechDude, so is never called from Character.
     * 
     * @param command to be executed
     * 
     * @return double value depending on the command being executed (most
     * commands return 0).
     */
    public double performCommand(Command command) {
        return 0;
    }

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
     * "go" command. This method is overridden in Hero, TechDude, and Zuul,
     * and is never called from Character.
     * 
     * @param command represents the command being executed whose commandWord
     * is "go".
     */
    void go(Command command) {
        LogicFacade.appendMessage("This does nothing.");
    }

    /**
     * This method returns the current room of the character.
     * 
     * @return currentRoom, which represents the current room of the character.
     */
    Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * This method sets the current room of the character to the specified room.
     * 
     * @param currentRoom, which is the new current room of the character.
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
        return characterInitiative;
    }

    /**
     * This method sets the character's initiative to the specified value.
     * 
     * @param characterInitiative, which is the new value of the character's
     * initiative.
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
        return speedFactor;
    }

    /**
     * This method sets the character's speed factor to the specified value.
     * 
     * @param speedFactor, which is the new value of the character's speed factor.
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
        return name;
    }
    
    /**
     * This method returns the character's hostility value.
     * 
     * @return hostility
     */
    int getHostility() {
        return hostility;
    }

    /**
     * This method sets the character's hostility value.
     * 
     * @param hostility, which is the new hostility value
     */
    void setHostility(int hostility) {
        this.hostility = hostility;
    }
    
    /**
     * This method sets the value of the message.
     * 
     * @param message, which is the new value of message
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
