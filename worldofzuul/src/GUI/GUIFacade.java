/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Acquaintance.*;
/**
 *
 * @author Simon
 */
public class GUIFacade implements IGUIFacade  {
    
    private static ILogicFacade logic;
    
   @Override
   public void injectLogic(ILogicFacade logic){
      this.logic = logic; 
   } 
   
   @Override
   public void startApplication(String[] args){
       ZuulGUI.main(args);
   }
   
   static void initializeGame(int numberOfZuulAtStart){
       logic.initializeGame(numberOfZuulAtStart);
   }
   
   static void sendCommand(String command){
       logic.processCommand(command);
   }
   
}
