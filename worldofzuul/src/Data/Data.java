/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The class is located in the data layer.
package Data;

// Imports:
import Acquaintance.IGame;
import Acquaintance.IHighscore;
import Acquaintance.IScore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the data layer of the application.
 * 
 * @author HCHB
 */

class Data {
    
    /**
     * Data fields.
     * SAVE_GAME_FILE_NAME: a String that specifies the name of the file that
     * stores the saved game.
     * HIGHSCORE_FILE_NAME: a String that specifies the name of the file that
     * stores the high score.
     */
    private static final String SAVE_GAME_FILE_NAME = "Escape pod.Zuul";
    private static final String HIGHSCORE_FILE_NAME = "Escape pod highscore.txt";
    
    /**
     * This empty no-arg constructor creates a Data object.
     */
    Data() {
    }
    
    /**
     * This method is used to save the game.
     * 
     * @param game the game to be saved (instance of IGame).
     */
    void saveGame(IGame game) {
        // Define and initialize fileStream and objectStream.
        FileOutputStream fileStream = null;
        ObjectOutputStream objectStream = null;
        
        // Save game.
        try {
            fileStream = new FileOutputStream(this.SAVE_GAME_FILE_NAME);
            objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(game);
            
            // Close object stream.
            objectStream.close();
        }
        // Catch exception
        catch (IOException ex) {
            ex.printStackTrace();
        }   
    }
    
    /**
     * This method is used to save the high score.
     * 
     * @param highscore high score to be saved.
     */
    void saveHighscore(IHighscore highscore) {
        // Create new File for high score.
        File highscoreFile = new File(this.HIGHSCORE_FILE_NAME);

        try {
            PrintWriter output = new PrintWriter(highscoreFile);
            
            IScore[] scores = highscore.getScores();
            
            for (int i = 0; i < scores.length; i++) {
                IScore score = scores[i];
                if (score != null) {
                    output.println(i + " " + score.getName() + " " 
                        + score.getScore());
                }
                else {
                    break;
                }
            }
            
            output.close();
        
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * This method is used to load the game.
     * 
     * @return game (an instance of IGame) that represents the loaded game.
     */
    IGame loadGame() {
        FileInputStream fileStream = null;
        ObjectInputStream objectStream = null;
        IGame game = null;

        try {
            fileStream = new FileInputStream(this.SAVE_GAME_FILE_NAME);
            objectStream = new ObjectInputStream(fileStream);
            game = (IGame)objectStream.readObject();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } 

        return game;
    }
    
    /**
     * This method is used to load the high score.
     * 
     * @return highscore (an instance of IHighscore) which represents the loaded
     * high score.
     */
    IHighscore loadHighscore() {
        File highscoreFile = new File(HIGHSCORE_FILE_NAME);
        IHighscore highscore;
        
        try {
            Scanner input = new Scanner(highscoreFile);
            
            IScore[] scores = new IScore[10];
            
            while (input.hasNext()) {
                int i = input.nextInt();
                String name = input.next();
                double score = input.nextDouble();
                scores[i] = new ScoreData(name,score);
            }
            
            highscore = new HighscoreData(scores);
            
        }
        catch (FileNotFoundException ex) {
            highscore = new HighscoreData();
        }
            
        return highscore;
    }
}
