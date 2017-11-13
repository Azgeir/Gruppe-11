/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import worldofzuul.Game;

/**
 *
 * @author Aske Wulf
 */
public class GUIController{
    
    @FXML
    private TextArea output;
    @FXML
    private ChoiceBox<?> useDropDown;
    private ChoiceBox<?> PickupDropDown;
    @FXML
    private Button stayButton;
    @FXML
    private Button pickupButton;
    @FXML
    private Button inventoryButton;
    @FXML
    private Button useButton;
    @FXML
    private Button activateButton;
    @FXML
    private Button talkButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button lookButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button dropButton;
    
    private Game game;
    @FXML
    private ChoiceBox<?> pickupDropDown;
    
    
    public void initialize() {
        // TODO
        game = new Game();
//        game.createGodDammit();
    }
    
    @FXML
    private void pickupButtonHandler(ActionEvent event) {
        String command;
        if (this.PickupDropDown.getValue() != null) {
            command = "pickup";
            command = command + " " + this.pickupDropDown.getValue();
            game.play(command);
        }
        else {
            System.out.println("choose something to pickup from the dropbox");
        }
    }
    
    @FXML
    private void inventoryButtonHandler(ActionEvent event) {
        String command;
        command = "look inventory";
        game.play(command);
        
    }
    
    @FXML
    private void useButtonAction(ActionEvent event) {
        String command;
        if (this.PickupDropDown.getValue() != null) {
            command = "use";
            command = command + " " + this.useDropDown.getValue();
            game.play(command);
        }
        else {
            System.out.println("choose something to use from the dropbox");
        }
    }
    
    @FXML
    private void activateButtonHandler(ActionEvent event) {
        String command;
        command = "activate reactor";
        game.play(command);
    }
    
    @FXML
    private void talkButtonHandler(ActionEvent event) {
        String command;
        command = "talk";
        game.play(command);
        
    }
    
    @FXML
    private void quitButtonHandler(ActionEvent event) {
        String command;
        command = "quit";
        game.play(command);
    }
    
    @FXML
    private void helpButtonHandler(ActionEvent event) {
        String command;
        command = "help";
        game.play(command);
    }
    
    @FXML
    private void stayButtonHandler(ActionEvent event) {
        String command;
        command = "stay";
        game.play(command);
    }
    
    @FXML
    private void lookButtonHandler(ActionEvent event) {
        String command;
        command = "look around";
        game.play(command);
        
    }
    
    @FXML
    private void saveButtonHandler(ActionEvent event) {
        System.out.println("Save stuff and stuff");
    }
    
    @FXML
    private void dropButtonHandler(ActionEvent event) {
        String command;
        if (this.PickupDropDown.getValue() != null) {
            command = "drop";
            command = command + " " + this.useDropDown.getValue();
            game.play(command);
        }
        else {
            System.out.println("choose something to drop from the dropbox");
        }
    }
    
}
