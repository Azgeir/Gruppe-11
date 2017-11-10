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
public class Driver {

    // Main method
    public void driver() {
        // Create an instance of Game.
        boolean playAgain;

        do {

            playAgain = false;
            // Call the play method on game
            Game game = new Game();
            game.play();
            System.out.println("Do you want to play again?");

            boolean correctInput = false;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter 'true' to continue or 'false' to quit.\n> ");
                if (input.hasNextBoolean()) {
                    playAgain = input.nextBoolean();
                    correctInput = true;
                }

            } while (!correctInput);

        } while (playAgain);
        System.out.println("Thank you for playing. Goodbye.");
    }
}
