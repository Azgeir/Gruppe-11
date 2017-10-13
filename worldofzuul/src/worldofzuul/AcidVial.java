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

// This class represents an AcidVial item.
public class AcidVial extends Item {
    // Data fields:
    private int damageAmount; // Damage caused to the player when the acid vial is used
    
    // (£) This constructor creates an acid vial with a specified damage amount
    public AcidVial(int damageAmount) {
        this.damageAmount = damageAmount;
    }
    
    // (£) This constructor creates an acid vial with a default damage amount
    public AcidVial() {
        this.damageAmount = 5;
    }
    
    // This method returns the damage amount of the acid vial.
    public int getDamageAmount() {
        return this.damageAmount;
    }
}
