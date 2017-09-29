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
public class Inventory {
    private int[] inventory;
    private static int beer = 0;
    private static int accessCard = 1;
    
    public Inventory(int n){
        this.inventory = new int[n];
    }
    
    public void setInventoryBeer(int n){
        this.inventory[beer] += n;
    }
    
    public int getInventoryBeer(){
        return this.inventory[beer];
    }
    
    public void useInventoryBeer(int n){
        if(this.inventory[beer]>(0+n)){
            this.inventory[beer] -= n;
        }
        else if (this.inventory[beer]>0) {
            System.out.println("You don't have that many beers");
        }
        else {
            System.out.println("You don't have any beers");
        }
    }
    
}
