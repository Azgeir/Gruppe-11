/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

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
    
    /**
     * Data fields.
     * previousRoomName: indicates the previous room of Zuul. This is used to
     * allow Zuul to move forwards instead of backwards.
     * triedLockedExits: ArrayList of locked exits that Zuul has tried to go
     * through. This is used to prevent Zuul from repeatedly trying to go
     * through a locked door.
     * stayCounter: counts the number of times that Zuul has stayed in the
     * current room.
     * STAY_COUNTER_MAX: the maximum number of turns that Zuul can stay in the
     * same room (set to 1).
     * heroIsInSameRoom: boolean value that indicates if the player is in the
     * same room as Zuul.
     * heroWasInSameRoom: boolean value that indicates if the player has just
     * left Zuul's current room.
     * heroInRoomInitiative: this value is set to Zuul's initiative when it
     * detects the presence of the player. It is used to allow the player to
     * escape Zuul. The value is initially set to -Double.MAX_VALUE so it always
     * fails its test in getCommand() unless it has been updated.
     */
    private String previousRoomName;
    private ArrayList<String> triedLockedExits = new ArrayList<>();
    private int stayCounter = 0;
    private final int STAY_COUNTER_MAX = 1;
    private boolean heroIsInSameRoom;
    private boolean heroWasInSameRoom;
    private double heroInRoomInitiative = -Double.MAX_VALUE;
    
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
    Zuul(Room currentRoom, String name, double speedFactor) {
        this(currentRoom, name, speedFactor, 0);
    }
    
    /**
    * This constructor creates a Zuul with the specified current room, name,
     * and speed factor. The constructor calls the constructor from the
     * superclass via constructor chaining. As part of the construction, it is
     * determined whether or not the player (hero) is in the same room as Zuul.
     * 
     * @param currentRoom current room of Zuul.
     * @param name name of the character (i.e., "Zuul").
     * @param speedFactor used when updating Zuul's initiative.
     * @param initiative used to set when the zuul gets it's turn
     */
    Zuul (Room currentRoom, String name, double speedFactor, double initiative){
        super(currentRoom, name, speedFactor, initiative);
        // Check if the player is in the same room as Zuul.
        this.heroIsInSameRoom = currentRoom.hasCharacter("Hero");
        /*
        When a Zuul is created, it does not know if the player has been in the
        room.
        */
        this.heroWasInSameRoom = false;
        
        if (heroIsInSameRoom) {
            LogicFacade.appendMessage("A boarding pod comes crashing through the wall of the space station.\n"
                    + "Then a Zuul steps out of it and says in the voice of Little Nicky\n"
                    + "\"I will eat your heart\"");
        }
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
                this.heroIsInSameRoom = false;
            }
            else {
                this.heroWasInSameRoom = false;
            }
        }
        
        /*
        If Zuul's current initiative is less than or equal to Zuul's initiative
        when it detected the presence of the player + 10 times Zuul's speed
        factor AND the player either is in the same room as Zuul or has just
        left the room, Zuul's command word is set to "kill". The initiative
        comparison is designed to allow the player to escape Zuul by using an
        acid vial, while all other actions will lead to the player's death.
        */
        if ((this.getCharacterInitiative() <= (this.heroInRoomInitiative
            + 10 * this.getSpeedFactor())) && (this.heroIsInSameRoom 
            || this.heroWasInSameRoom)) {
            word1 = "kill";
        }
        /*
        Else if the player is in the same room as Zuul, Zuul's command word is
        set to "stay". This gives the player a chance to react to the presence
        of Zuul, before the player is killed.
        */
        else if (this.getCurrentRoom().hasCharacter("Hero")) {
            word1 = "stay";
        }
        /*
        Else the player's presence is "erased" and Zuul's move is either a "go"
        command or a "stay" command.
        */
        else {
            // "Erase" the presence of the player.
            this.heroIsInSameRoom = false;
            this.heroWasInSameRoom = false;
            this.heroInRoomInitiative = -Double.MAX_VALUE;
            
            /*
            Create an ArrayList based on the available exits from the current
            room.
            */
            ArrayList<String> exits = new ArrayList(
                this.getCurrentRoom().getExits().keySet());
            
            /*
            Remove the locked exits that Zuul has already tried from the
            ArrayList of exits.
            */
            exits.removeAll(triedLockedExits);
            
            /*
            If there is more than one exit available, remove the exit that leads
            to Zuul's previous room. This is done to allow Zuul to move forwards
            instead of moving back and forth between rooms.
            */
            if (exits.size() > 1) {
                exits.remove(this.previousRoomName);    
            }
            
            /*
            If Zuul has not stayed in the room before (stayCounter <
            stayCounterMax, where stayCounterMax is 1) or the ArrayList of exits
            is empty, add "stay" to the ArrayList of exits.
            */
            if (stayCounter < STAY_COUNTER_MAX || exits.isEmpty()) {
                exits.add("stay");
            }
            
            /*
            The number of available moves for Zuul is set to the size of the
            ArrayList of exits.
            */
            int numberMoveActions = exits.size();
            
            /*
            The action to be performed is determined by a random integer from 0
            to (numberMoveActions - 1).
            */
            int action = (int)(Math.random() * numberMoveActions);
            
            /*
            If the random integer indicates the "stay" entry in the ArrayList of
            exits, the command word is set to "stay" and the stay counter is
            incremented.
            */
            if (exits.get(action).equals("stay")) {
                word1 = exits.get(action);
                stayCounter++;
            }
            /*
            Else the command word is set to "go", and the second word of the
            command (i.e., the direction) is determined by the String specified
            by the random integer. Furthermore, the stay counter is reset to 0.
            */
            else {
                word1 = "go";
                word2 = exits.get(action);
                stayCounter = 0;
            } 
        }
        
        // Check if the player is in the same room as Zuul.
        heroIsInSameRoom = this.getCurrentRoom().hasCharacter("Hero");
        
        /*
        If the player is in the same room, Zuul's current initiative is recorded
        in heroInRoomInitiative. This value is used to determine whether Zuul is
        allowed to kill the player in its next turn.
        */
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
    void go(Command command) {
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
                this.getCurrentRoom().removeCharacterInRoom(this.getName());
                this.setCurrentRoom(nextRoom);
                this.getCurrentRoom().addCharacterInRoom(this.getName());
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
    private void kill() {
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
