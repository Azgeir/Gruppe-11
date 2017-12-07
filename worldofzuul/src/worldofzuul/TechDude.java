/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The class is located in the logic layer.
package worldofzuul;

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
     * This method helps in defining what commands should be chosen when it is the tech dude's turn.
     * 
     * @param hero
     * @param follows 
     */
    void setFollowsHero(Character hero, boolean follows) {
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
            wantsToTalk = true;
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
    
    @Override
    double performCommand(Command command) {
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
    
    /**
     * This method overrides the original method because TechDude does not
     * change room according to his own command input. Set and get methods are
     * used because the attributes are private in the superclass.
     * 
     * @param command 
     */
    @Override
    // This method changes the current room of tech dude to that of hero
    void go(Command command){
        // Remove tech dude from current room
        this.getCurrentRoom().removeCharacterInRoom(this.getName());
        // Change current room of tech dude to current room of hero.
        this.setCurrentRoom(this.hero.getCurrentRoom());
        // Add tech dude to the new current room
        this.getCurrentRoom().addCharacterInRoom(this.getName());
        // Set character initiative
        this.setCharacterInitiative(this.getCharacterInitiative() + 10 * this.getSpeedFactor());
    }
    
    private void talk(Command command){
        Conversation talk = new Conversation();
        boolean validAnswer = true;
        
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
                        if (wantsToTalk) {
                            isTalking = Boolean.parseBoolean(command.getThirdWord());
                        }
                        wantsToTalk = true;

                        if (!isTalking) {
                            counter = 1;
                            isTalking = false;
                        }
                    }
                    catch (NumberFormatException ex) {
                        LogicFacade.appendMessage("The input wasn't a boolean");
//                        isTalking = false;
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
            
            
            
//            if (wantToTalk && validAnswer){
//                String messsageChooser = this.getName()+counter++;
//                talk.talk(messsageChooser);
//                talk.options(messsageChooser);
//            }
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
