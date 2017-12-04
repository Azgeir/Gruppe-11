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
   
   static void initializeGame(int numberOfZuulAtStart, double spawnTime, String name){
       logic.initializeGame(numberOfZuulAtStart, spawnTime, name);
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
   
   static boolean isGameFinished(){
       boolean finished = logic.isGameFinished();
       return finished;
   }
   
   static Set<String> getInventorySet(){
       Set<String> inventorySet = logic.getInventorySet();
       return inventorySet;
   }
   static void saveGame(){
       logic.saveGame();
   }
   static void loadGame(){
       logic.loadGame();
   }
   static IHighscore loadHighscore(){
       IHighscore highscore = logic.loadHighscore();
       return highscore;
   }
   
   static String readAndDeleteGameMessage(){
       String gameMessage = logic.readAndDeleteMessage();
       return gameMessage;
   }
   
   static int getNumberOfItems(String inventory, String item){
       return logic.getNumberOfItems(inventory, item);
   }
   
   static boolean isRoomKnown(IRoom room){
       boolean isKnown = logic.isRoomKnown(room);
       return isKnown;
   }
   
   static boolean isTalking(){
       boolean talking = logic.isTalking();
       return talking;
   }
}
