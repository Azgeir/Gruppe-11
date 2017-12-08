/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The class is located in the Starter package.
package Starter;

// Imports:
import Acquaintance.*;
import GUI.GUIFacade;
import Data.DataFacade;
import Logic.LogicFacade;

/**
 * This class is used to start the application.
 * 
 * @author HCHB
 */

public class Start {
    // Main method
    public static void main(String[] args) {
        // Create data facade.
        IDataFacade data = new DataFacade();
        
        // Create logic facade.
        ILogicFacade logic = new LogicFacade();
        
        // Create GUI facade.
        IGUIFacade GUI = new GUIFacade();
        
        // Inject logic into GUI.
        GUI.injectLogic(logic);
        
        // Inject data into logic.
        logic.injectData(data);
        
        // Start application
        GUI.startApplication(args);
    }
}
