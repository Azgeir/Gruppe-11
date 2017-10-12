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
    
    private boolean hasMetHero;
    private int health;
    private Character hero;
    
    //£ health
    public TechDude(){
        this.hasMetHero = false;
        this.health = 10;
    }
    
    /* overrides the original method because TechDude don't change room according to 
    his own command input
    set and get methods are used because the attributes are private in the superClass
    */
    @Override
    public void go(Command command){
        this.setCurrentRoom(this.hero.getCurrentRoom());
        this.setCharacterInitiative(this.getCharacterInitiative()+5*super.getSpeedFactor());
    }
    
    // Helps in defining what commands should be chosen when it's the TechDudes turn
    public void meetHero(Character hero){
        this.hasMetHero = true;
        this.hero = hero;
    }
    
    
}
