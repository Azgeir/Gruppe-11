/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

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
 *
 * @author HCHB
 */
public class Data {
    
    private String saveGameFileName;
    private String highscoreFileName;
    
    public Data(){
        this.saveGameFileName = "Escape pod.Zuul";
        this.highscoreFileName = "Escape pod highscore.txt";
    }
    
    void saveGame(IGame game){
        FileOutputStream fileStream = null;
        ObjectOutputStream objectStream = null;
        
        try{
            fileStream = new FileOutputStream(this.saveGameFileName);
            objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(game);
            
            objectStream.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }   
    }
    
    
    void saveHighscore(IHighscore highscore){
        File highscoreFile = new File(this.highscoreFileName);

        try {
            PrintWriter output = new PrintWriter(highscoreFile);
            
            IScore[] scores = highscore.getScores();
            
            for (int i = 0; i < scores.length; i++) {
                IScore score = scores[i];
                output.println(i + " " + score.getName() + " " + score.getScore());
            }
            
            output.close();
        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    IGame loadGame() {
        FileInputStream fileStream = null;
        ObjectInputStream objectStream = null;
        IGame game = null;

        try {
            fileStream = new FileInputStream(this.saveGameFileName);
            objectStream = new ObjectInputStream(fileStream);
            game = (IGame) objectStream.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return game;
    }
    
    IHighscore loadHighscore(){
        File highscoreFile = new File(highscoreFileName);
        IHighscore highscore;
        
        try {
            Scanner input = new Scanner(highscoreFile);
            
            IScore[] scores = new IScore[10];
            
            while (input.hasNext()){
                int i = input.nextInt();
                String name = input.next();
                double score = input.nextDouble();
                scores[i] = new ScoreData(name,score);
            }
            
            highscore = new HighscoreData(scores);
            
        } catch (FileNotFoundException ex) {
            highscore = new HighscoreData();
        }
            
        return highscore;
    }
    
}
