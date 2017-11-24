/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

import java.io.Serializable;

/**
 * This class represents a character.
 * 
 * @author HCHB
 */

public class Character implements Serializable {

    /*
    Data fields:
    Initiative needs to be double because the speed factor is a double.
     */
    private Room currentRoom;
    private double characterInitiative;
    private double speedFactor;
    // private String direction; // (?)
    private String name;
    private int hostility;

    // This constructor creates a character with default initiative and speed factor
    Character() {
        this.characterInitiative = 0;
        this.speedFactor = 1;
    }

    // This constructor creates a character with the specified current room and name
    Character(Room currentRoom, String name) {
        this();
        this.currentRoom = currentRoom;
        this.name = name;
    }

    // This constructor creates a character with the specified current room, name, and speed factor
    Character(Room currentRoom, String name, double speedFactor) {
        this(currentRoom, name);
        this.speedFactor = speedFactor;
    }

    //£ just copied from sourcecode Game.goRoom
    void go(Command command) {
        // If the command does not have a second word, print error message
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        // Set the direction string to the second word of the command
        String direction = command.getSecondWord();

        // Set nextRoom to be the neighbouring room specified by the direction string
        Room nextRoom = this.currentRoom.getExit(direction);

        // If the specified exit does not exist, print error message
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } 
        // If the specified exit is locked, print error message
        else if (this.getCurrentRoom().getLockedExit(direction)){
            System.out.println("This exit is locked, so you can't get through.");
        }
        // If the specified exit exists and is not locked, move character
        else {
            // Remove character from current room
            this.getCurrentRoom().setHasCharacter(this.name, false);
            // Change the value of currentRoom to the specified neighbouring room
            this.currentRoom = nextRoom;
            // Add character to the new current room
            this.getCurrentRoom().setHasCharacter(this.name, true);
            // Print description of the current room
            System.out.println(currentRoom.getLongDescription());
        }
        
        if (this.getCurrentRoom().getHasCharacter("Zuul")){
            System.out.println("The Zuul is in this room.");
        }
        
        this.characterInitiative += 10 * this.speedFactor;
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

//    // This method returns the character's direction
//    String getDirection() {
//        return direction;
//    }
//
//    // This method sets the character's direction to the specified string
//    void setDirection(String direction) {
//        this.direction = direction;
//    }

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
