/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Acquaintance.*;
import java.util.Set;
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
   
   static Set<String> getExits(){
       Set<String> exits = logic.getExits();
       return exits;
   }
   
   static Set<String> getRoomItemSet(){
       Set<String> itemSet = logic.getRoomItemSet();
       return itemSet;
   }
   
   static boolean isRoomLookedBefore(){
       boolean lookedBefore = logic.isRoomLookedBefore();
       return lookedBefore;
       
   }
   
}
