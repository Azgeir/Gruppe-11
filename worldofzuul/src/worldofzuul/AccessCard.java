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
    
    public AccessCard (){
        super(5, "accesscard", "lock and unlock doors");
    }
    
    @Override
    public double use(Character currentCharacter){
        System.out.println("You wave the access card around");
        
        return 0;
    }
    
}
