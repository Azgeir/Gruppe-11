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
public class TechDude extends Character {
    
    private boolean hasMetHero = false;
    private int health = 10;
    private Character hero;
    
    //£ health
    public TechDude(){
   
    }
    
    public TechDude(Room currentRoom, String name){
        super(currentRoom, name);
}
    
    public TechDude(Room currentRoom,String name, double speedFactor){
        super(currentRoom, name, speedFactor);
    }
    
    public TechDude(Room currentRoom, String name, double speedFactor, int health){
        this(currentRoom, name, speedFactor);
        this.health = health;
    }
    
    /* overrides the original method because TechDude don't change room according to 
    his own command input
    set and get methods are used because the attributes are private in the superClass
    */
    @Override
    public void go(Command command){
        this.getCurrentRoom().setHasCharacter(this.getName(), false);
        this.setCurrentRoom(this.hero.getCurrentRoom());
        this.getCurrentRoom().setHasCharacter(this.getName(), true);
        this.setCharacterInitiative(this.getCharacterInitiative()+5*super.getSpeedFactor());
    }
    
    // Helps in defining what commands should be chosen when it's the TechDudes turn
    @Override
    public void meetHero(Character hero){
        this.hasMetHero = true;
        this.hero = hero;
    }
    
    @Override
    public Command getCommand(CommandWords commands) {
        // Declare a String variable for the input
        String inputLine;
        String word1, word2, word3;
        
       if (this.hasMetHero == false){
           word1 = "stay";
           word2 = null;
           word3 = null;
           return new Command(commands.getCommandWord(word1), word2, word3);
       }
       else {
           word1 = "go";
           word2 = null;
           word3 = null;
           return new Command(commands.getCommandWord(word1), word2, word3);
           
       }
    }
}
