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
    
    Zuul(Room currentRoom, String name, double speedFactor){
        super(currentRoom, name, speedFactor);
        this.heroIsInSameRoom = currentRoom.hasCharacter("Hero");
        this.heroWasInSameRoom = false;
        this.heroHadTurn = false;
    }
    
    /*£
    same go command as source, except the monster gets to remember where it
    comes from
    */
    @Override
    void go(Command command){
        if(!command.hasSecondWord()) {
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = this.getCurrentRoom().getExit(direction);
        
        if (nextRoom == null) {
        }
        else if (this.getCurrentRoom().isExitLocked(direction)){
            triedLockedExits.add(direction);
        }
        else {
            triedLockedExits.clear();
            String roomName = this.getCurrentRoom().getName();
            if (roomName != null) {
                this.previousRoomName = roomName;
            }
            this.getCurrentRoom().setHasCharacter(this.getName(), false);
            this.setCurrentRoom(nextRoom);
            this.getCurrentRoom().setHasCharacter(this.getName(), true);
        }
        //System.out.println("Zuul is " + this.getCurrentRoom().getShortDescription());
        this.setCharacterInitiative(this.getCharacterInitiative()+10*this.getSpeedFactor());
        
        heroIsInSameRoom = this.getCurrentRoom().hasCharacter("Hero");
        if (heroIsInSameRoom) {
            heroInRoomInitiative = this.getCharacterInitiative();
        }
        if (this.getCurrentRoom().hasCharacter("Hero")){
            LogicFacade.appendMessage("The Zuul is in this room.");
        }
        
    }
    
    @Override
    public Command getCommand(CommandWords commands, String GUICommand) {
        // Set words 1, 2 and 3 to null
        String word1 = null;
        String word2 = null;
        String word3 = null;
        
        if (heroIsInSameRoom) {
            if (!this.getCurrentRoom().hasCharacter("Hero")) {
                heroWasInSameRoom = true;
            } else {
                heroWasInSameRoom = false;
            }
        } else {
        }
        
        if (heroIsInSameRoom || heroWasInSameRoom) {
            heroHadTurn = true;
        } else {
            heroHadTurn = false;
        }
        
        if ((this.getCharacterInitiative()<=(heroInRoomInitiative+10*this.getSpeedFactor())) && (heroIsInSameRoom || heroWasInSameRoom) && heroHadTurn) {
            word1 = "kill";
        }
        else if (this.getCurrentRoom().hasCharacter("Hero")) {
            word1 = "stay";
            // This sets word1 to have the value/command 'stay' for the player
        }
        else {
            heroIsInSameRoom = false;
            heroWasInSameRoom = false;
            heroInRoomInitiative = -Double.MAX_VALUE;
            heroHadTurn = false;
            // This creates a array list based on the rooms and where the commands changes the payers position
            ArrayList<String> exits = new ArrayList(this.getCurrentRoom().getExits().keySet());
            
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
    
    
    private void kill(){
        
        int whichKillMessage = (int)(Math.random() * 3);
        
        this.setMessage("lose");
        
        if (this.getCurrentRoom().hasCharacter("Hero")){
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
        
        this.setCharacterInitiative(this.getCharacterInitiative() + 10 * this.getSpeedFactor());
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
                // If command is "stay", call stay() method on current character
                case STAY:
                    this.stay(command);
                    break;
                case KILL:
                    this.kill();
                // If command does not match any of the options, break.
                default:
                    break;
            }
        }
        // Return boolean value (false = continue playing; true = quit game)
        return 0;
    }
    
}
