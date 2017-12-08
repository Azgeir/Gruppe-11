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
 * This class represents the tech dude. The class extends the superclass
 * Character since tech dude is a character. The class also implements the
 * interface Serializable.
 * 
 * @author HCHB
 */

class TechDude extends Character implements Serializable {
    
    /**
     * Data fields.
     * followsHero: boolean value that indicates whether tech dude is following
     * the hero. This is initially set to false, as the tech dude does not
     * follow the hero at the start of the game.
     * 
     * hero: instance of Character that represents the hero.
     * 
     * hostility: integer value that represents the tech dude's hostility. This
     * is used when conversing with the hero. If the hero offends the tech dude,
     * his hostility increases. Eventually, tech dude will no longer engage in
     * conversation with the hero. The value is initially set to 0, as the hero
     * has not yet offended the tech dude.
     * 
     * metHero: boolean value that indicates whether tech dude has met the
     * hero. This is initially set to false, as tech dude has not met the hero
     * at the start of the game.
     * 
     * wantsToTalk: boolean value that indicates whether the tech dude wants to
     * talk to the hero.
     * 
     * counter: integer value that indicates how far the conversation with the
     * hero has progressed.
     * 
     * isTalking: boolean value that indicates whether tech dude is currently
     * talking to the hero.
     */
    private boolean followsHero = false;
    private Character hero = null;
    private int hostility = 0;
    private boolean metHero = false;
    private boolean wantsToTalk = false;
    private int counter = 1;
    private boolean isTalking = false;

    /**
     * This is an empty no-arg constructor. This is provided in case the class
     * will be extended at a later point.
     */
    TechDude() {
    }
    
    /**
     * This constructor creates a tech dude with the specified current room and
     * name. The constructor calls the constructor of the Character superclass
     * (constructor chaining).
     * 
     * @param currentRoom current room of tech dude
     * @param name name of tech dude (i.e. "TechDude")
     */
    TechDude(Room currentRoom, String name) {
        super(currentRoom, name);
    }
    
    /**
     * This constructor creates a tech dude with the specified current room,
     * name, and speed factor. The constructor calls the constructor of the
     * Character superclass (constructor chaining).
     * 
     * @param currentRoom current room of tech dude
     * @param name name of tech dude (i.e. "TechDude")
     * @param speedFactor speed factor of tech dude
     */
    TechDude(Room currentRoom, String name, double speedFactor) {
        super(currentRoom, name, speedFactor);
    }
    
    /**
     * This method is used to check whether tech dude is following the hero.
     * 
     * @return true if tech dude follows the hero; else it returns false.
     */
    boolean followsHero() {
        return this.followsHero;
    }
    
    /**
     * This method is used to check whether the tech dude wants to talk to
     * the hero.
     * 
     * @return true if tech dude wants to talk to hero; else it returns false.
     */
    boolean wantsToTalk() {
        return this.wantsToTalk;
    }
    
    /**
     * This method is used to update the value of followsHero. The method helps
     * in defining what commands should be chosen when it is the tech dude's
     * turn, as this depends on whether or not tech dude is following the hero.
     * 
     * @param hero instance of Character that represents the player of the game.
     * @param follows boolean value that indicates whether or not tech dude is
     * to follow the hero.
     */
    void setFollowsHero(Character hero, boolean follows) {
        // Tech dude has met the hero when this method is called.
        metHero = true;
        /*
        If tech dude is to follow hero, followsHero is set to true, and the
        value of hero is set to the hero argument.
        */
        if (follows) {
           this.followsHero = true;
           this.hero = hero; 
        }
        /*
        If tech dude is not to follow hero, followsHero is set to false, and
        the value of hero is set to null.
        */
        else {
            this.followsHero = false;
            this.hero = null;
        }
    }
    
    /**
     * This method returns tech dude's command based a String from the GUI
     * layer. The command is based on whether or not tech dude has met the hero.
     * The method overrides the getCommand() method from the Character
     * superclass.
     * 
     * @param commands valid commands available
     * @param GUICommand String that represents the command sent from the GUI
     * layer
     * 
     * @return instance of Command that represents the command
     */
    @Override
    Command getCommand(CommandWords commands, String GUICommand) {
        // Declare String variables for the command
        String word1, word2, word3;
        word2 = null;
        word3 = null;

        /*
        If tech dude is not currently following the hero, the current room of
        tech dude contains hero, and tech dude has not previously met hero,
        a talk command is created.
        */
        if (this.followsHero == false &&
            this.getCurrentRoom().hasCharacter("Hero") == true &&
            metHero == false) {
            // Send message to GUI
            LogicFacade.appendMessage("You see a man in the corner of the room."
                + " He notices you and comes over.");
            // Set words of command
            word1 = "talk";
            // Tech dude has now met the hero and wants to talk to them.
            metHero = true;
            wantsToTalk = true;
            // Return "talk" command
            return new Command(commands.getCommandWord(word1), word2, word3);
        }
        // Else if tech dude does not follow hero, return "stay" command.
        else if (this.followsHero == false){
            word1 = "stay";
            return new Command(commands.getCommandWord(word1), word2, word3);
        }
        // Else if tech dude is following the hero, return "go" command.
        else {
            word1 = "go";
            return new Command(commands.getCommandWord(word1), word2, word3);
        }
    }
    
    /**
     * This method is used when tech dude performs a command. The execution is
     * based on the command parameter. The method overrides the performCommand()
     * method in Character.
     * 
     * @param command instance of Command which represents the command to be
     * executed.
     * 
     * @return 0 as tech dude's actions do not affect the initiative of Zuul.
     */
    @Override
    double performCommand(Command command) {
        // Get the command word of the specified command.
        CommandWord commandWord = command.getCommandWord();
        
        /*
        If the command word is different from null, perfrorm the specified
        command.
        */
        if (null != commandWord) {
            switch (commandWord) {
                // If command is "go", call go() method on current character
                case GO:
                    this.go(command);
                    break;
                /*
                If command is "stay", call stay() method on current character.
                */
                case STAY:
                    this.stay(command);
                    break;
                /*
                If the command is "talk", call talk() method on current
                character.
                */
                case TALK:
                    this.talk(command);
                    break;
                // If command does not match any of the options, break.
                default:
                    break;
            }
        }
        // Return 0.
        return 0;
    }
    
    /**
     * This method is used to execute a "go" command. The method implements the
     * go() method in Character. It changes the current room of tech dude to
     * that of the hero. Set and get methods are used because the attributes
     * are private in the Character superclass.
     * 
     * @param command instance of Command, which represents the command to be
     * executed.
     */
    @Override
    void go(Command command) {
        // Remove tech dude from current room
        this.getCurrentRoom().removeCharacterInRoom(this.getName());
        // Change current room of tech dude to current room of hero.
        this.setCurrentRoom(this.hero.getCurrentRoom());
        // Add tech dude to the new current room
        this.getCurrentRoom().addCharacterInRoom(this.getName());
        // Set character initiative
        this.setCharacterInitiative(this.getCharacterInitiative()
            + 10 * this.getSpeedFactor());
    }
    
    /**
     * This method is used to execute a "talk" command. The method is private
     * as it is only called from within the class.
     * 
     * @param command instance of Command, which represents the command to be
     * executed.
     */
    private void talk(Command command){
        // Create instance of Conversation.
        Conversation talk = new Conversation();
        /*
        Initially set validAnswer to true, as the conversation starts with the
        assumption of a valid answer.
        */
        boolean validAnswer = true;
        
        /*
        Check if tech dude's hostility is less than 3, as a higher hostility
        value causes tech dude to refuse to engage in a conversation with the
        hero.
        */
        if (this.hostility < 3) {
            /*
            Number is initially set to -1. If the talk command is valid, number
            is set to the integer value of the second word of the command.
            */
            int number = -1;
            /*
            Check if the command has a second word. This should be parsed to an
            integer to update the value of number, which determines whether the
            conversation starts. This value is chosen from a drop down menu in
            the GUI.
            */
            if (command.hasSecondWord()) {
                try {
                    number = Integer.parseInt(command.getSecondWord());
                }
                catch (NumberFormatException ex) {
                    LogicFacade.appendMessage("The input wasn't a number");
                }
                /*
                Use the command's third word to update the value of isTalking.
                */
                if (command.hasThirdWord()) {
                    try {
                        if (wantsToTalk) {
                            isTalking = Boolean.parseBoolean(command.getThirdWord());
                        }
                        wantsToTalk = true;

                        if (!isTalking) {
                            counter = 1;
                        }
                    }
                    catch (NumberFormatException ex) {
                        LogicFacade.appendMessage("The input wasn't a boolean");
                    }
                }
            }
            
            if (isTalking) {
                switch(number){
                    case 1:
                        if (counter == 4) {
                            wantsToTalk = false;
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
                        wantsToTalk = false;
                        isTalking = false;
                        break;
                    case 3:
                        if (counter == 3) {
                            wantsToTalk = false;
                            followsHero = true;
                        }
                        else {
                            LogicFacade.appendMessage("Not a valid answer");
                            validAnswer = false;
                        }
                        break;
                        
                    default:
                        break;
                }
                if (validAnswer) {
                    counter++;
                }
            }
            
            if (wantsToTalk){
                String messsageChooser = this.getName()+counter;
                talk.talk(messsageChooser);
                talk.options(messsageChooser); 
                isTalking = true;
            }
            
        } else {
            LogicFacade.appendMessage("Fuck you. I hate you");
            wantsToTalk = false;
            followsHero = false;
        }
        
        if (!wantsToTalk){
            counter = 1;
            isTalking = false;
        }
    }
}
