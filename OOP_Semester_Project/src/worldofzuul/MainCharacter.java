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
public class MainCharacter {
    private int[] inventory;
    
    public MainCharacter(){
        this.inventory = new int[5];
        
    }

    public int[] getInventory() {
        return inventory;
    }

    public void setInventory(int i, int n) {
        
        int sum = 0;
                
        for (int j = 0 ; j < this.inventory.length ; j++){
            sum += this.inventory[j];
        }
        
        if(sum+n<=4){
            this.inventory[i] += n;
        }
        else{
            System.out.println("You can't carry this much");
        }
        
    }
    
    
    
    
    
    
    
}
