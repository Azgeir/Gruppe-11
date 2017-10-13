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
    
    private Room previousRoom;
    
    public Zuul(){
        
    }
    
    public Zuul(Room currentRoom){
        super(currentRoom);
    }
    
    public Zuul(Room currentRoom, double speedFactor){
        super(speedFactor, currentRoom);
    }
    
    /*£
    same go command as source, except the monster gets to remember where it
    comes from
    */
    @Override
    public void go(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = this.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            this.previousRoom = this.getCurrentRoom();
            this.setCurrentRoom(nextRoom);
            System.out.println(this.getCurrentRoom().getLongDescription());
        }
        this.setCharacterInitiative(this.getCharacterInitiative()+5*this.getSpeedFactor());
    }
    
    @Override
    public Command getCommand(CommandWords commands) {
        // Set words 1, 2 and 3 to null
        String word1 = null;
        String word2 = null;
        String word3 = null;
        
        ArrayList<String> exits = new ArrayList(this.getCurrentRoom().getExits().keySet());
        
        
        int numberMoveActions = exits.size()+1;
        int action = (int)(Math.random()*numberMoveActions);
                
        exits.add("stay");    
        
        if (numberMoveActions != (action+1)){
            word1 = "go";
            word2 = exits.get(action);
        }
        else {
            word1 = exits.get(action);
        }
        
        // Create a Command object based on words 1 and 2, and return the command.
        return new Command(commands.getCommandWord(word1), word2, word3);
    }
    
}
