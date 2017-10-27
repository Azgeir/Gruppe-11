/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.ArrayList;

/**
 *
 * @author HCHB
 */
public class Zuul extends Character {
    
    private String previousRoomName;
    private ArrayList<String> triedLockedExits = new ArrayList<>();
    private int stayCounter = 0;
    private int stayCounterMax = 1;
    
    public Zuul(){
        
    }
    
    public Zuul(Room currentRoom, String name){
        super(currentRoom, name);
    }
    
    public Zuul(Room currentRoom, String name, double speedFactor){
        super(currentRoom, name, speedFactor);
    }
    
    /*£
    same go command as source, except the monster gets to remember where it
    comes from
    */
    @Override
    public void go(Command command){
        if(!command.hasSecondWord()) {
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = this.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
        }
        else if (this.getCurrentRoom().getLockedExit(direction)){
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
        this.setCharacterInitiative(this.getCharacterInitiative()+5*this.getSpeedFactor());
    }
    
    @Override
    public Command getCommand(CommandWords commands) {
        // Set words 1, 2 and 3 to null
        String word1 = null;
        String word2 = null;
        String word3 = null;
        
        if (this.getCurrentRoom().getHasCharacter("Hero")) {
            word1 = "stay";
            // This sets word1 to have the value/command 'stay' for the player
        }
        else {
            // This creates a array list based on the rooms and where the commands changes the payers position
            ArrayList<String> exits = new ArrayList(this.getCurrentRoom().getExits().keySet());
            
            exits.removeAll(triedLockedExits);
            
            if (exits.size()!=1) {
                exits.remove(this.previousRoomName);    
            }
            
            if (stayCounter<stayCounterMax) {
                exits.add("stay");
            }
            
            int numberMoveActions = exits.size();
            int action = (int)(Math.random()*numberMoveActions);
            
            if (numberMoveActions != (action+1)){
                word1 = "go";
                word2 = exits.get(action);
                stayCounter = 0;
            }
            else {
                word1 = exits.get(action);
                stayCounter++;
            }
        }
        
        // Create a Command object based on words 1 and 2, and return the command.
        return new Command(commands.getCommandWord(word1), word2, word3);
    }
    
}
