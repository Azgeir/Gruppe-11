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
    
    
    
}
