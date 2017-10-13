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
        this.healthAmount = healthAmount;
    }
    
    // (£)This constructor creates an instance of MedKit with a default health amount
    public MedKit() {
        this.healthAmount = 5;
    }
    
    // This method returns the health amount of the MedKit
    public int getHealthAmount() {
        return this.healthAmount;
    }
}
