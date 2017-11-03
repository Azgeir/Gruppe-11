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
    private boolean heroIsInSameRoom;
    private boolean heroWasInSameRoom;
    private double heroInRoomInitiative = -Double.MAX_VALUE;
    private boolean heroHadTurn;
    
    public Zuul(){
        
    }
    
    public Zuul(Room currentRoom, String name){
        super(currentRoom, name);
        this.heroIsInSameRoom = currentRoom.getHasCharacter("Hero");
        this.heroWasInSameRoom = false;
        this.heroHadTurn = false;
    }
    
    public Zuul(Room currentRoom, String name, double speedFactor){
        super(currentRoom, name, speedFactor);
        this.heroIsInSameRoom = currentRoom.getHasCharacter("Hero");
        this.heroWasInSameRoom = false;
        this.heroHadTurn = false;
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
        this.setCharacterInitiative(this.getCharacterInitiative()+10*this.getSpeedFactor());
        
        heroIsInSameRoom = this.getCurrentRoom().getHasCharacter("Hero");
        if (heroIsInSameRoom) {
            heroInRoomInitiative = this.getCharacterInitiative();
        }
        
    }
    
    @Override
    public Command getCommand(CommandWords commands) {
        // Set words 1, 2 and 3 to null
        String word1 = null;
        String word2 = null;
        String word3 = null;
        
        if (heroIsInSameRoom) {
            if (!this.getCurrentRoom().getHasCharacter("Hero")) {
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
        else if (this.getCurrentRoom().getHasCharacter("Hero")) {
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
        
        heroIsInSameRoom = this.getCurrentRoom().getHasCharacter("Hero");
        if (heroIsInSameRoom) {
            heroInRoomInitiative = this.getCharacterInitiative();
        }
        
        // Create a Command object based on words 1 and 2, and return the command.
        return new Command(commands.getCommandWord(word1), word2, word3);
    }
    
    
    public String kill(){
        
        int whichKillMessage = (int)(Math.random()*3);
        
        String reason = "lose";
        
        switch (whichKillMessage){
            case 1:
                reason = "reason1";
                break;
            case 2:
                reason = "reason2";
                break;
            default:
                reason = "reason3";
            }
        
        return reason;
    }
    
}
