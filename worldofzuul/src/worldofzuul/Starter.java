/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.Scanner;

/**
 *
 * @author Aske Wulf
 */
public class Starter {
    public static void main(String[] args){
        
        boolean playAgain;
        Scanner input = new Scanner(System.in);
        
        do {
            Game game = new Game();
            game.play();
            System.out.println("Do you want to play again?\n Enter true or false");
            playAgain = input.nextBoolean();
            
        } while (playAgain);
    }
    
}
