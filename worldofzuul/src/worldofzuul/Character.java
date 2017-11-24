/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

import java.io.Serializable;

/**
 * This class represents a character. This is the superclass for Hero, TechDude,
 * and Zuul.
 * 
 * @author HCHB
 */

public class Character implements Serializable {

    /**
     * Data fields.
     * currentRoom: character's current room
     * characterInitiative: used to determine turn
     * speedFactor: used to update character initiative
     * name: used to identify character type in other classes
     * hostility: used when conversing with a character (currently only used for
     * TechDude, but is put in Character class to allow future development of
     * characters that the player can converse with).
     */
    private Room currentRoom;
    private double characterInitiative;
    private double speedFactor;
    private String name;
    private int hostility;

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
     * This method is called from processCommand() in Game when a character
     * tries to go from one room to another.
     * 
     * @param command 
     */
    void go(Command command) {
        System.out.println("This does nothing.");
    }

    // This method is overridden in the Hero class
    void pickUp(Command command) {
        System.out.println("This does nothing.");
    }

    // This method is overridden in the Hero class
    void dropItem(Command command) {
        System.out.println("This does nothing.");
    }
    
    // This method is overridden in the Hero class
    void look(Command command) {
        System.out.println("This does nothing.");
    }
    
    // This method is overridden in the Hero class
    void peek(Command command) {
        System.out.println("This does nothing.");
    }
    
    // This method is overridden in the Hero class
    double use(Command command) {
        System.out.println("This does nothing.");
        return -1;
    }
    
    // This method is overridden in the Hero class
    void lock(Command command) {
        System.out.println("This does nothing.");
    }
    
    // This method is overridden in the Hero class
    void unlock(Command command) {
        System.out.println("This does nothing.");
    }
    
    // (£) This method increases the character's initiative
    void stay(Command command) {
        this.characterInitiative += 10 * this.speedFactor;
    }

    // This method returns the current room of the character
    Room getCurrentRoom() {
        return currentRoom;
    }

    // This method sets the current room of the character to the specfied room
    void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    // This method returns the character's initiative
    double getCharacterInitiative() {
        return characterInitiative;
    }

    // This method sets the character's initiative to the specified value
    void setCharacterInitiative(double characterInitiative) {
        this.characterInitiative = characterInitiative;
    }

    // This method returns the character's speed factor
    double getSpeedFactor() {
        return speedFactor;
    }

    // This method sets the character's speed factor to the specified value
    void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    // This method is overridden in the Hero, TechDude, and Zuul classes
    Command getCommand(CommandWords commands, String GUICommand) {
        String word1 = null;
        String word2 = null;
        String word3 = null;
        return new Command(commands.getCommandWord(word1), word2, word3);
    }

    // This method returns the name of the character
    String getName() {
        return name;
    }
    
    // (£) This method is overridden in the Hero class
    double activate(Command Command, boolean reactorActivated) {
        System.out.println("You are not a hero and therefore cannot activate things.");
        return Double.MAX_VALUE;
    }
    
    // This method is overridden in the TechDude class
    void followsHero(Character hero, boolean follows){
    }

    int getHostility() {
        return hostility;
    }

    void setHostility(int hostility) {
        this.hostility = hostility;
    }
    
    boolean isFollowsHero(){
        return false;
    }
    
    String kill(){
        
        return "derp";
    }
}
