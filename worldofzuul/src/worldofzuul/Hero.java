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
public class Hero extends Character {
    
    private int health;
    private Inventory inventory;
    
    /*
    £
    */
    public Hero(){
        this.health = 10;
        this.inventory = new Inventory(100);
    }
    
    public Hero(int health, int capacity){
        this.health = health;
        this.inventory = new Inventory(capacity);
    }
    
    
}
