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
public class AccessCard extends Item {
    
    AccessCard (){
        super(5, "accesscard", "lock and unlock doors");
    }
    
    @Override
    double use(Hero currentCharacter){
        System.out.println("You wave the access card around.");
        currentCharacter.setCharacterInitiative(currentCharacter.getCharacterInitiative()+3*currentCharacter.getSpeedFactor());
        return 0;
    }
    
}
