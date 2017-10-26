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
    private String name;

    public Character() {
        this.characterInitiative = 0;
        this.speedFactor = 1;
    }

    public Character(Room currentRoom, String name) {
        this();
        this.currentRoom = currentRoom;
        this.name = name;
    }

    public Character(Room currentRoom, String name, double speedFactor) {
        this(currentRoom, name);
        this.speedFactor = speedFactor;
    }

    //£ just copied from sourcecode Game.goRoom
    public void go(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = this.currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } 
        else if (this.getCurrentRoom().getLockedExit(direction)){
            System.out.println("This exit is locked, you can't get through");
        }
        else {
            this.getCurrentRoom().setHasCharacter(this.name, false);
            this.currentRoom = nextRoom;
            this.getCurrentRoom().setHasCharacter(this.name, true);
            System.out.println(currentRoom.getLongDescription());
        }
        
        
        this.characterInitiative += 5 * this.speedFactor;
    }

    public void pickUp(Command command) {
        System.out.println("This does nothing");
    }

    public void dropItem(Command command) {
        System.out.println("This does nothing");
    }
    
    public void look(Command command) {
        System.out.println("This does nothing");
    }
    public void peek(Command command) {
        System.out.println("This does nothing");
    }
    public int use(Command command) {
        System.out.println("This does nothing");
        return -1;
    }
    public void lock(Command command) {
        System.out.println("This does nothing");
    }
    public void unlock(Command command) {
        System.out.println("This does nothing");
    }
        //£
    public void stay(Command command) {
        this.characterInitiative += 5 * this.speedFactor;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public double getCharacterInitiative() {
        return characterInitiative;
    }

    public void setCharacterInitiative(double characterInitiative) {
        this.characterInitiative = characterInitiative;
    }

    public double getSpeedFactor() {
        return speedFactor;
    }

    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Command getCommand(CommandWords commands) {

        String word1 = null;
        String word2 = null;
        String word3 = null;
        return new Command(commands.getCommandWord(word1), word2, word3);
    }

    public String getName() {
        return name;
    }
    
    //£
    public double activate(Command Command){
        this.characterInitiative += 5;
        
        if (this instanceof Hero){
            if (this.currentRoom.getName().equals("reactor")) {
                if (this.currentRoom.getHasCharacter("TechDude")) {
                    return (this.characterInitiative+50);
                }
                else {
                    System.out.println("You need the TechDude to do this");
                    return Double.MAX_VALUE;
                }
            } else {
                System.out.println("There is no reactor in this room");
                return Double.MAX_VALUE;
            }
        }
        return Double.MAX_VALUE;
    }
    
    public void meetHero(Character hero){
    }

    
}
