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
    private double initiativeReduction;
    
    // (£) This constructor creates an acid vial with a specified damage amount
    public AcidVial(int damageAmount, int initiativeReduction) {
        super(5, "acidvial", "attack something.");
        this.damageAmount = damageAmount;
        this.initiativeReduction = initiativeReduction;
    }
    
    // (£) This constructor creates an acid vial with a default damage amount
    public AcidVial() {
        this.damageAmount = 5;
        this.initiativeReduction = 15;
    }
    
    // This method returns the damage amount of the acid vial.
    public int getDamageAmount() {
        return this.damageAmount;
    }
    
    @Override
    public double use(Character currentCharacter){
        Hero tempCharacter = (Hero)currentCharacter;
        tempCharacter.setHealth(tempCharacter.getHealth()-this.damageAmount);
        tempCharacter.getInventory().removeItem(this);
        
        if (tempCharacter.getCurrentRoom().getHasCharacter("Zuul")) {
            System.out.println("You throw an acidvial at the te_rrifying Zuul\nYou hit it in the face, you where hit some splashback but now have a chance to flee");
            return this.initiativeReduction;
        } else {
            System.out.println("You for some reason throw an acidvial at the wall\n You where hit by splashback and hurt yourself");
            return 0;
        }
    }
}
