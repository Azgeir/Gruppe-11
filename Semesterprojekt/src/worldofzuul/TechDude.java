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

// This class represents the tech dude
public class TechDude extends Character {
    // Data fields:
    private boolean followsHero = false; // This boolean value indicates if the tech dude has met hero
    private int health = 10; // Tech dude's health
    private Character hero; // Tech dude's hero
    private int hostility = 0;


    
    // (£) This is an empty no-arg constructor
    public TechDude() {
    }
    
    // This constructor creates a tech dude with the specified current room and name
    public TechDude(Room currentRoom, String name){
        super(currentRoom, name);
}
    // This constructor creates a tech dude with the specified current room, name, and speed factor.
    public TechDude(Room currentRoom, String name, double speedFactor){
        super(currentRoom, name, speedFactor);
    }
    
    // This constructor creates a tech dude with the specified current room, name, speed factor, and health.
    public TechDude(Room currentRoom, String name, double speedFactor, int health){
        this(currentRoom, name, speedFactor);
        this.health = health;
    }
    
    /* This method overrides the original method because TechDude does not change
    room according to his own command input.
    Set and get methods are used because the attributes are private in the superclass
    */
    @Override
    // This method changes the current room of tech dude to that of hero
    public void go(Command command){
        // Remove tech dude from current room
        this.getCurrentRoom().setHasCharacter(this.getName(), false);
        // Change current room of tech dude to current room of hero.
        this.setCurrentRoom(this.hero.getCurrentRoom());
        // Add tech dude to the new current room
        this.getCurrentRoom().setHasCharacter(this.getName(), true);
        // Set character initiative
        this.setCharacterInitiative(this.getCharacterInitiative() + 10 * this.getSpeedFactor());
    }
    
    // This method helps in defining what commands should be chosen when it is 
    // the tech dude's turn.
    @Override
    public void followsHero(Character hero, boolean follows){
        // Set data fields
        if (follows){
           this.followsHero = true;
           this.hero = hero; 
        }
        else{
            this.followsHero = false;
            this.hero = null;
        }
        
    }
    
    // This method returns tech dude's command based on whether or not he has met the hero
    @Override
    public Command getCommand(CommandWords commands) {
        // Declare String variables for the input
        String word1, word2, word3;
        
        // If tech dude has not met the hero, return "stay" command
        if (this.followsHero == false){
            word1 = "stay";
            word2 = null;
            word3 = null;
            return new Command(commands.getCommandWord(word1), word2, word3);
        }
        // If tech dude has met the hero, return "go" command
        else {
            word1 = "go";
            word2 = null;
            word3 = null;
            return new Command(commands.getCommandWord(word1), word2, word3);
        }
    }

    public boolean isFollowsHero() {
        return followsHero;
    }
    
    
}
