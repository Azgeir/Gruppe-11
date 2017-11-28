/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

// Imports:
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents Zuul (the monster of the game). The class extends the
 * superclass Character and implements the interface Serializable.
 * 
 * @author HCHB
 */
public class Zuul extends Character implements Serializable {
    
    private String previousRoomName;
    private ArrayList<String> triedLockedExits = new ArrayList<>();
    private int stayCounter = 0;
    private int stayCounterMax = 1;
    private boolean heroIsInSameRoom;
    private boolean heroWasInSameRoom;
    private double heroInRoomInitiative = -Double.MAX_VALUE;
    private boolean heroHadTurn;
    
    /**
     * This constructor creates a Zuul with the specified current room, name,
     * and speed factor. The constructor calls the constructor from the
     * superclass via constructor chaining. As part of the construction, it is
     * determined whether or not the player (hero) is in the same room as Zuul.
     * 
     * @param currentRoom current room of Zuul.
     * @param name name of the character (i.e., "Zuul").
     * @param speedFactor used when updating Zuul's initiative.
     */
    Zuul(Room currentRoom, String name, double speedFactor){
        super(currentRoom, name, speedFactor);
        // Check if the player is in the same room as Zuul.
        this.heroIsInSameRoom = currentRoom.hasCharacter("Hero");
        /*
        When a Zuul is created, it does not know if the player has been in the
        room or if the player has had its turn.
        */
        this.heroWasInSameRoom = false;
        this.heroHadTurn = false;
    }
    
    /**
     * This method is used to generate a command for Zuul. The method overrides
     * the getCommand() method from Character.
     * 
     * @param commands valid commands in the game.
     * @param GUICommand command word from GUI.
     * 
     * @return a command.
     */
    @Override
    Command getCommand(CommandWords commands, String GUICommand) {
        // Set words 1, 2, and 3 to null.
        String word1 = null;
        String word2 = null;
        String word3 = null;
        
        /*
        If heroIsInSameRoom is true, then either the player is still in the
        same room (set heroWasInSameRoom to false) or the player has left the
        room during their turn (set heroWasInSameRoom to true).
        */
        if (this.heroIsInSameRoom) {
            if (!this.getCurrentRoom().hasCharacter("Hero")) {
                this.heroWasInSameRoom = true;
            }
            else {
                this.heroWasInSameRoom = false;
            }
        }
        
        if (this.heroIsInSameRoom || this.heroWasInSameRoom) {
            this.heroHadTurn = true;
        }
        else {
            this.heroHadTurn = false;
        }
        
        if ((this.getCharacterInitiative() <= (this.heroInRoomInitiative
            + 10 * this.getSpeedFactor())) && (this.heroIsInSameRoom 
            || this.heroWasInSameRoom) && this.heroHadTurn) {
            word1 = "kill";
        }
        else if (this.getCurrentRoom().hasCharacter("Hero")) {
            word1 = "stay";
            // This sets word1 to have the value/command 'stay' for the player
        }
        else {
            this.heroIsInSameRoom = false;
            this.heroWasInSameRoom = false;
            this.heroInRoomInitiative = -Double.MAX_VALUE;
            this.heroHadTurn = false;
            // This creates a array list based on the rooms and where the
            // commands changes the payers position
            ArrayList<String> exits = new ArrayList(
                this.getCurrentRoom().getExits().keySet());
            
            exits.removeAll(triedLockedExits);
            
            if (exits.size()!=1) {
                exits.remove(this.previousRoomName);    
            }
            
            if (stayCounter<stayCounterMax || exits.size()==0) {
                exits.add("stay");
            }
            
            int numberMoveActions = exits.size();
            int action = (int)(Math.random()*numberMoveActions);
            
            if (exits.get(action).equals("stay")) {
                word1 = exits.get(action);
                stayCounter++;
            }
            else {
                word1 = "go";
                word2 = exits.get(action);
                stayCounter = 0;
            }
            
        }
        
        heroIsInSameRoom = this.getCurrentRoom().hasCharacter("Hero");
        if (heroIsInSameRoom) {
            heroInRoomInitiative = this.getCharacterInitiative();
        }
        
        // Create a Command object based on words 1 and 2, and return the command.
        return new Command(commands.getCommandWord(word1), word2, word3);
    }
    
    /**
     * This method is called when Zuul is to execute a command. This is done by
     * matching the command word of the command to the corresponding action. The
     * method overrides the performCommand() method in Character.
     * 
     * @param command the command to be executed by Zuul.
     * 
     * @return 0 as the action does not affect Zuul's initiative.
     */
    @Override
    double performCommand(Command command) {
        /*
        Create an instance of CommandWord using the command word of the
        specified command.
        */
        CommandWord commandWord = command.getCommandWord();
        
        /*
        If the command word matches a valid command, call the corresponding 
        method.
        */
        if (commandWord != null) {
            switch (commandWord) {
                // If the command is "go", call go() method from Zuul.
                case GO:
                    this.go(command);
                    break;
                // If the command is "stay", call stay() method from Character
                case STAY:
                    this.stay(command);
                    break;
                // If the command is "kill", call kill() method from Zuul.
                case KILL:
                    this.kill();
                // If command does not match any of the options, break.
                default:
                    break;
            }
        }
        
        // Return 0 as the action does not affect Zuul's initiative.
        return 0;
    }
    
    /**
     * This method is called when Zuul tries to move from one room to another.
     * The method overrides the go() method in Character.
     * 
     * @param command the command to be executed.
     */
    @Override
    void go(Command command){
        /*
        If the command does not have a second word (i.e., a direction), break
        out of the method, as Zuul cannot go in an undefined direction.
        */
        if(!command.hasSecondWord()) {
            return;
        }

        // Set the command's second word as the direction String.
        String direction = command.getSecondWord();

        /*
        Define the next room as the exit from the current room associated with
        the direction String.
        */
        Room nextRoom = this.getCurrentRoom().getExit(direction);
        
        // Check that the next room is not null.
        if (nextRoom != null) {
            /*
            If the exit in the specified direction is locked, add the exit to
            the HashMap of tried locked exits.
            */
            if (this.getCurrentRoom().isExitLocked(direction)) {
                this.triedLockedExits.add(direction);
            }
            // If the exit is not locked, Zuul moves to the next room.
            else {
                /*
                The HashMap of tried locked exits is cleared, as Zuul is now
                moving to a new room with a new set of available exits.
                */
                this.triedLockedExits.clear();
                /*
                Set the previous room to be the room that Zuul is currently 
                leaving.
                */
                String roomName = this.getCurrentRoom().getName();
                if (roomName != null) {
                    this.previousRoomName = roomName;
                }
                /*
                As Zuul moves from one room to another, update its presence/
                absence in the two rooms.
                */
                this.getCurrentRoom().setHasCharacter(this.getName(), false);
                this.setCurrentRoom(nextRoom);
                this.getCurrentRoom().setHasCharacter(this.getName(), true);
            }
        }
        
        // Increase Zuul's initiative.
        this.setCharacterInitiative(this.getCharacterInitiative()
            + 10 * this.getSpeedFactor());
        
        // Check if the player is in the room that Zuul enters.
        this.heroIsInSameRoom = this.getCurrentRoom().hasCharacter("Hero");
        
        /*
        If the player is in the same room, update the value of
        heroInRoomInitiative and send a message to the facade.
        */
        if (this.heroIsInSameRoom) {
            this.heroInRoomInitiative = this.getCharacterInitiative();
            LogicFacade.appendMessage("The Zuul is in this room.");
        }
    }
    
    /**
     * This method is called when the player is killed by Zuul. The method is
     * private as it is called internally from the performCommand() method. The
     * method determines a random kill message and assigns its label to the 
     * character's message. This message is later read, when the kill message
     * is to be printed.
     */
    private void kill(){
        // Genereate a random integer (0, 1, or 2)
        int whichKillMessage = (int)(Math.random() * 3);
        
        /*
        If the player tries to run away from Zuul, the default kill message
        associated with the label "lose" is chosen.
        */
        this.setMessage("lose");
        
        /*
        If the player is still in the same room as Zuul, the kill message is
        determined by the random integer.
        */
        if (this.getCurrentRoom().hasCharacter("Hero")) {
        switch (whichKillMessage){
            case 1:
                this.setMessage("lose1");
                break;
            case 2:
                this.setMessage("lose2");
                break;
            default:
                this.setMessage("lose3");
            }
        }
        
        // Increase Zuul's initiative.
        this.setCharacterInitiative(this.getCharacterInitiative()
            + 10 * this.getSpeedFactor());
    }
}