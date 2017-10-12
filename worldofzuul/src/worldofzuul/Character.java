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
public class Character {
    
    /*
    Initiative needs to be double because the speedfactor
    is a double
    Direction is needed for locking doors and peeking
    */
    private Room currentRoom;
    private double characterInitiative;
    private double speedFactor;
    private String direction;
    
    public Character(){
        this.characterInitiative = 0;
        this.speedFactor = 1;
        
    }
    
    // just copied from sourcecode Game.goRoom
    public void go(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = this.currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            this.currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
        this.characterInitiative += 5;
    }
    
    //£
    public void stay(Command command){
        this.characterInitiative += 5;
    }
    
    
    
}
