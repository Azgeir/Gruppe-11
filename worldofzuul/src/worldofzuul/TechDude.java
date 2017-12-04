/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.io.Serializable;

/**
 *
 * @author HCHB
 */

// This class represents the tech dude
public class TechDude extends Character implements Serializable {
    // Data fields:
    private boolean followsHero = false; // This boolean value indicates if the tech dude has met hero
    private int health = 10; // Tech dude's health
    private Character hero; // Tech dude's hero
    private int hostility = 0;
    private boolean metHero;
    
    private boolean wantToTalk = false;
    private int counter = 1;


    
    // (£) This is an empty no-arg constructor
    TechDude() {
    }
    
    // This constructor creates a tech dude with the specified current room and name
    TechDude(Room currentRoom, String name){
        super(currentRoom, name);
        metHero = false;
}
    // This constructor creates a tech dude with the specified current room, name, and speed factor.
    TechDude(Room currentRoom, String name, double speedFactor){
        super(currentRoom, name, speedFactor);
        metHero = false;
    }
    
    // This constructor creates a tech dude with the specified current room, name, speed factor, and health.
    TechDude(Room currentRoom, String name, double speedFactor, int health){
        this(currentRoom, name, speedFactor);
        this.health = health;
    }
    
    /* This method overrides the original method because TechDude does not change
    room according to his own command input.
    Set and get methods are used because the attributes are private in the superclass
    */
    @Override
    // This method changes the current room of tech dude to that of hero
    void go(Command command){
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
//    @Override
    void followsHero(Character hero, boolean follows){
        // Set data fields
        metHero = true;
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
    public Command getCommand(CommandWords commands, String GUICommand) {
        // Declare String variables for the input
        String word1, word2, word3;

        if(this.followsHero == false && this.getCurrentRoom().hasCharacter("Hero") == true && metHero == false){
            LogicFacade.appendMessage("You see a man in the corner of the room. He notices you and comes over.");
            word1 = "talk";
            word2 = null;
            word3 = null;
            metHero = true;
            wantToTalk = true;
            return new Command(commands.getCommandWord(word1), word2, word3);
        }
        // If tech dude has not met the hero, return "stay" command
        else if (this.followsHero == false){
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

//    @Override
    boolean isFollowsHero() {
        return followsHero;
    }
    
    @Override
    public double performCommand(Command command) {
        // Create instance of CommandWord using the command word of the specified command (from Parser)
        CommandWord commandWord = command.getCommandWord();
        
        if (null != commandWord) // Execute the command if the input matches a valid command
        {
            switch (commandWord) {
                // If command is "go", call go() method on current character
                case GO:
                    this.go(command);
                    break;
                case STAY:
                    this.stay(command);
                    break;
                case TALK:
                    this.talk(command);
                    break;
                // If command does not match any of the options, break.
                default:
                    break;
            }
        }
        // Return boolean value (false = continue playing; true = quit game)
        return 0;
    }
    
    private void talk(Command command){
        Conversation talk = new Conversation();

        if (this.hostility < 3) {
            
            int number = -1;
            if (command.hasSecondWord()) {
                try {
                    number = Integer.parseInt(command.getSecondWord());
                }
                catch (NumberFormatException ex) {
                    LogicFacade.appendMessage("The input wasn't a number");
                }
                if (command.hasThirdWord()) {
                    try {
                        wantToTalk = Boolean.parseBoolean(command.getThirdWord());
                    }
                    catch (NumberFormatException ex) {
                        LogicFacade.appendMessage("The input wasn't a boolean");
                    }
                }
            }
            
            if (counter != 1) {
                switch(number){
                    case 1:
                        if (counter == 5) {
                            wantToTalk = false;
                            this.followsHero = true;
                        }
                        break;
                    case 2:
                        hostility += 1;
                        if (hostility == 3) {
                            LogicFacade.appendMessage("The tech dude hates you and will no longer talk to you.");
                            followsHero = false;
                        }
                        else {
                            LogicFacade.appendMessage("The tech dude got annoyed at you.");
                        }
                        wantToTalk = false;
                        break;
                    case 3:
                        if (counter == 4) {
                            wantToTalk = false;
                            followsHero = true;
                        }
                        else {
                            LogicFacade.appendMessage("Not a valid answer");
                        }
                        break;
                        
                    default:
                        break;
                }
            }
            
            
            
            
            if (wantToTalk){
                String messsageChooser = this.getName()+counter++;
                talk.talk(messsageChooser);
                talk.options(messsageChooser);
            }
            
            
            
        } else {
            LogicFacade.appendMessage("Fuck you. I hate you");
            wantToTalk = false;
            followsHero = false;
        }
        
        if (!wantToTalk){
            counter = 1;
        }
    }
    

    public boolean isWantToTalk() {
        return wantToTalk;
    }
    
    
}
