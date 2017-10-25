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
    public static void main(String[] args) {
        // Create an instance of Game.
        boolean playAgain;

        do {
            Scanner input = new Scanner(System.in);
            playAgain = false;
            // Call the play method on game
            Game game = new Game();
            game.play();
            System.out.print("Enter true if you want to continue\n");

            if (input.hasNextBoolean() == true) {
                playAgain = true;
            }

        } while (playAgain);
    }
}
