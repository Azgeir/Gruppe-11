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

// This class starts the game.
public class Starter {
    // Main method
    public static void main(String[] args){
        // Create an instance of Game.
        
        boolean playAgain;
        Scanner input = new Scanner(System.in);
        
        do {
            // Call the play method on game
            Game game = new Game();
            game.play();
            System.out.print("Do you want to play again?\nEnter true or false: ");
            playAgain = input.nextBoolean();
            
        } while (playAgain);
    }
}
