/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 *
 * @author laurabrinkholmjustesen
 */

// This class represents a MedKit item.
public class MedKit extends Item {
    // Data fields:
    private int healthAmount; // Health points added to player's health when used
    
    // (£)This constructor creates an instance of MedKit with a specified health amount
    public MedKit(int healthAmount) {
        super(5, "MedKit", "heal yourself.");
        this.healthAmount = healthAmount;
    }
    
    // (£)This constructor creates an instance of MedKit with a default health amount
    public MedKit() {
        super(5, "medkit", "heal yourself.");
        this.healthAmount = 5;
    }
    
    // This method returns the health amount of the MedKit
    public int getHealthAmount() {
        return this.healthAmount;
    }
    
    @Override
    public double use(Character currentCharacter){
        Hero tempCharacter = (Hero)currentCharacter;
        tempCharacter.setHealth(tempCharacter.getHealth()+this.healthAmount);
        tempCharacter.getInventory().removeItem(this);
        System.out.println("You healed youself with a medkit, but it is now spent");
        return 0;
    }
}
