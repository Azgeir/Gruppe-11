/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Starter;
import Acquaintance.*;
import GUI.GUIFacade;
import Data.DataFacade;
import worldofzuul.LogicFacade;

/**
 *
 * @author HCHB
 */
public class Start {
    public static void main(String[] args) {
        IDataFacade data = new DataFacade();
        ILogicFacade logic = new LogicFacade();
        IGUIFacade GUI = new GUIFacade();
        
        GUI.injectLogic(logic);
        logic.injectData(data);
        
        GUI.startApplication(args);
        
    }
}
