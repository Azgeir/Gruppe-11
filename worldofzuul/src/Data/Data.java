/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Acquaintance.IGame;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HCHB
 */
public class Data {
    
    private String saveGameFileName;
    
    public Data(){
        this.saveGameFileName = "Escape pod.Zuul";
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
    
    
    void saveHighscore(){
        
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
    
    void loadHighscore(){
        
    }
    
}
