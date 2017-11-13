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
    @FXML
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
    private ChoiceBox<?> GoDropDown1;
    @FXML
    private Button goButton;
    @FXML
    private Button peekButton;
    @FXML
    private Button unlockButton;
    @FXML
    private Button unlockButton1;
    

    public void initialize() {
        // TODO
        game = new Game();
//        game.createGodDammit();
    }    

    @FXML
    private void pickupButtonHandler(ActionEvent event) {
        game.play("pickup");
    }

    @FXML
    private void inventoryButtonHandler(ActionEvent event) {
        game.play("inventory");
    }

    @FXML
    private void useButtonAction(ActionEvent event) {
        game.play("use");
    }

    @FXML
    private void activateButtonHandler(ActionEvent event) {
    }

    @FXML
    private void talkButtonHandler(ActionEvent event) {
       game.play("talk");
    }

    @FXML
    private void quitButtonHandler(ActionEvent event) {
        game.play("quit");
    }

    @FXML
    private void helpButtonHandler(ActionEvent event) {
        game.play("help");
    }

    @FXML
    private void stayButtonHandler(ActionEvent event) {
        game.play("stay");
        
    }

    @FXML
    private void lookButtonHandler(ActionEvent event) {
//        game.createGodDammit();
    }

    @FXML
    private void saveButtonHandler(ActionEvent event) {
        game.play("save");
    }

    @FXML
    private void dropButtonHandler(ActionEvent event) {
    }

    @FXML
    private void goButtonHandler(ActionEvent event) {
    }

    @FXML
    private void peekButtonHandler(ActionEvent event) {
    }

    @FXML
    private void unlockButtonHandler(ActionEvent event) {
    }
    
}
