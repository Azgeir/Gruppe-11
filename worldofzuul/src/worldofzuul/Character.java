/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 *
 * @author HCHB
 */
// This class represents a character
public class Character {

    /*
    Data fields:
    Initiative needs to be double because the speedfactor
    is a double
    Direction is needed for locking doors and peeking
     */
    private Room currentRoom;
    private double characterInitiative;
    private double speedFactor;
    private String direction;
    private String name;
    private int hostility;

    // This constructor creates a character with default initiative and speed factor
    public Character() {
        this.characterInitiative = 0;
        this.speedFactor = 1;
    }

    // This constructor creates a character with the specified current room and name
    public Character(Room currentRoom, String name) {
        this();
        this.currentRoom = currentRoom;
        this.name = name;
    }

    // This constructor creates a character with the specified current room, name, and speed factor
    public Character(Room currentRoom, String name, double speedFactor) {
        this(currentRoom, name);
        this.speedFactor = speedFactor;
    }

    //£ just copied from sourcecode Game.goRoom
    public void go(Command command) {
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
        
        this.characterInitiative += 10 * this.speedFactor;
    }

    // This method is overridden in the Hero class
    public void pickUp(Command command) {
        System.out.println("This does nothing");
    }

    // This method is overridden in the Hero class
    public void dropItem(Command command) {
        System.out.println("This does nothing");
    }
    
    // This method is overridden in the Hero class
    public void look(Command command) {
        System.out.println("This does nothing");
    }
    
    // This method is overridden in the Hero class
    public void peek(Command command) {
        System.out.println("This does nothing");
    }
    
    // This method is overridden in the Hero class
    public double use(Command command) {
        System.out.println("This does nothing");
        return -1;
    }
    
    // This method is overridden in the Hero class
    public void lock(Command command) {
        System.out.println("This does nothing");
    }
    
    // This method is overridden in the Hero class
    public void unlock(Command command) {
        System.out.println("This does nothing");
    }
    
    // (£) This method increases the character's initiative
    public void stay(Command command) {
        this.characterInitiative += 10 * this.speedFactor;
    }

    // This method returns the current room of the character
    public Room getCurrentRoom() {
        return currentRoom;
    }

    // This method sets the current room of the character to the specfied room
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    // This method returns the character's initiative
    public double getCharacterInitiative() {
        return characterInitiative;
    }

    // This method sets the character's initiative to the specified value
    public void setCharacterInitiative(double characterInitiative) {
        this.characterInitiative = characterInitiative;
    }

    // This method returns the character's speed factor
    public double getSpeedFactor() {
        return speedFactor;
    }

    // This method sets the character's speed factor to the specified value
    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    // This method returns the character's direction
    public String getDirection() {
        return direction;
    }

    // This method sets the character's direction to the specified string
    public void setDirection(String direction) {
        this.direction = direction;
    }

    // This method is overridden in the Hero, TechDude, and Zuul classes
    public Command getCommand(CommandWords commands) {
        String word1 = null;
        String word2 = null;
        String word3 = null;
        return new Command(commands.getCommandWord(word1), word2, word3);
    }

    // This method returns the name of the character
    public String getName() {
        return name;
    }
    
    // (£) This method is overridden in the Hero class
    public double activate(Command Command, boolean reactorActivated) {
        System.out.println("You are not a hero and therefore cannot activate things");
        return Double.MAX_VALUE;
    }
    
    // This method is overridden in the TechDude class
    public void followsHero(Character hero, boolean follows){
    }

    public int getHostility() {
        return hostility;
    }

    public void setHostility(int hostility) {
        this.hostility = hostility;
    }
    
    public boolean isFollowsHero(){
        return false;
    }
    
    public String kill(){
        
        return "derp";
    }
    
    
    
}
