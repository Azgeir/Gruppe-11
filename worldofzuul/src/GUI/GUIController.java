/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class GUIController {

    @FXML
    private TextArea output;
    @FXML
    private ChoiceBox<String> useDropDown;
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
    @FXML
    private Button goButton;
    @FXML
    private Button peekButton;
    @FXML
    private Button unlockButton;

    @FXML
    private ChoiceBox<String> pickupDropDown;

//    private Game game;
    @FXML
    private ChoiceBox<String> GoDropDown;
    @FXML
    private Button lockButton;

    public void initialize() {
        // TODO
        int numberOfZuulAtStart = 3;
//        game = new Game(3);
        GUIFacade.initializeGame(numberOfZuulAtStart);
//        game.createGodDammit();

        ObservableList<String> list = FXCollections.observableArrayList();

        Set<String> exits = GUIFacade.getExits();
        
        GoDropDown.getItems().addAll(exits);
        
//        GoDropDown.getItems();
//        GoDropDown.setItems(value);
//        game.
    }

    @FXML
    private void pickupButtonHandler(ActionEvent event) {

        String command;
        if (this.pickupDropDown.getValue() != null) {
            command = "pickup";
            String item = this.pickupDropDown.getValue();
            command = command + " " + item;
            GUIFacade.sendCommand(command);
            
            this.pickupDropDown.getItems().clear();
            Set<String> itemSet = GUIFacade.getRoomItemSet();
            this.pickupDropDown.getItems().addAll(itemSet);
//            game.play(command);
        } else {
            System.out.println("choose something to pickup from the dropbox");
        }
        this.isGameFinished();

    }

    @FXML
    private void inventoryButtonHandler(ActionEvent event) {
        String command = "look inventory";
        GUIFacade.sendCommand(command);
        this.isGameFinished();

//        game.play("inventory");
    }

    @FXML
    private void useButtonAction(ActionEvent event) {
        
        String command;
        if (this.useDropDown.getValue() != null) {
            command = "use";
            command = command + " " + this.useDropDown.getValue();
            GUIFacade.sendCommand(command);
//            game.play(command);
        } else {
            System.out.println("choose something to use from the dropbox");
        }
        this.isGameFinished();
//        game.play("use");
    }

    @FXML
    private void activateButtonHandler(ActionEvent event) {
        String command = "activate reactor";
        GUIFacade.sendCommand(command);
        this.isGameFinished();
    }

    @FXML
    private void talkButtonHandler(ActionEvent event) {
        String command = "talk";
        GUIFacade.sendCommand(command);
        this.isGameFinished();
//        game.play("talk");
    }

    @FXML
    private void quitButtonHandler(ActionEvent event) {
        String command = "quit";
        GUIFacade.sendCommand(command);
        this.isGameFinished();
//        game.play("quit");
    }

    @FXML
    private void helpButtonHandler(ActionEvent event) {
        String command = "help";
        GUIFacade.sendCommand(command);
        this.isGameFinished();
//        game.play("help");
    }

    @FXML
    private void stayButtonHandler(ActionEvent event) {
        String command = "stay";
        GUIFacade.sendCommand(command);
        this.isGameFinished();
//        game.play("stay");
    }

    @FXML
    private void lookButtonHandler(ActionEvent event) {
        String command = "look around";
        GUIFacade.sendCommand(command);
        Set<String> itemSet = GUIFacade.getRoomItemSet();
        
        this.pickupDropDown.getItems().clear();
        this.pickupDropDown.getItems().addAll(itemSet);
        
        this.isGameFinished();
        
//        game.play(command);

    }

    @FXML
    private void saveButtonHandler(ActionEvent event) {

//        game.play("save");

//        String command = "save";
//        GUIFacade.sendCommand(command);

        System.out.println("Save stuff and stuff");
        
        this.isGameFinished();

    }

    @FXML
    private void dropButtonHandler(ActionEvent event) {
        String command;
        if (this.useDropDown.getValue() != null) {
            command = "drop";
            command = command + " " + this.useDropDown.getValue();
            GUIFacade.sendCommand(command);
//            game.play(command);
        } else {
            System.out.println("choose something to drop from the dropbox");
        }
        this.isGameFinished();
    }

    @FXML
    private void goButtonHandler(ActionEvent event) {
        
        String command;
        if (this.GoDropDown.getValue() != null) {
            command = "go";
            command = command + " " + this.GoDropDown.getValue();
            GUIFacade.sendCommand(command);
            Set<String> exits = GUIFacade.getExits();
            this.GoDropDown.getItems().clear();
            this.GoDropDown.getItems().addAll(exits);
            
            this.pickupDropDown.getItems().clear();
            if (GUIFacade.isRoomLookedBefore()) {
                Set<String> itemSet = GUIFacade.getRoomItemSet();
                this.pickupDropDown.getItems().addAll(itemSet);
            }
            
//            game.play(command);
        } else {
            System.out.println("choose a direction from the dropbox");
        }
        
        this.isGameFinished();
        
    }

    @FXML
    private void peekButtonHandler(ActionEvent event) {
        
        String command;
        if (this.GoDropDown.getValue() != null) {
            command = "peek";
            command = command + " " + this.GoDropDown.getValue();
            GUIFacade.sendCommand(command);
//            game.play(command);
        } else {
            System.out.println("choose a direction from the dropbox");
        }
        this.isGameFinished();
        
    }

    @FXML
    private void unlockButtonHandler(ActionEvent event) {
        
        String command;
        if (this.GoDropDown.getValue() != null) {
            command = "unlock";
            command = command + " " + this.GoDropDown.getValue();
            GUIFacade.sendCommand(command);
//            game.play(command);
        } else {
            System.out.println("choose a direction from the dropbox");
        }
        
        this.isGameFinished();
    }

    @FXML
    private void lockButtonHandler(ActionEvent event) {
        
        String command;
        if (this.GoDropDown.getValue() != null) {
            command = "lock";
            command = command + " " + this.GoDropDown.getValue();
            GUIFacade.sendCommand(command);
//            game.play(command);
        } else {
            System.out.println("choose a direction from the dropbox");
        }
        this.isGameFinished();
    }
    
    private void isGameFinished(){
        if (GUIFacade.isGameFinished()) {
            this.goButton.setDisable(true);
            this.GoDropDown.setDisable(true);
            this.activateButton.setDisable(true);
            this.dropButton.setDisable(true);
            this.helpButton.setDisable(true);
            this.inventoryButton.setDisable(true);
            this.lookButton.setDisable(true);
            this.peekButton.setDisable(true);
            this.pickupButton.setDisable(true);
            this.pickupDropDown.setDisable(true);
            this.quitButton.setDisable(true);
            this.saveButton.setDisable(true);
            this.stayButton.setDisable(true);
            this.talkButton.setDisable(true);
            this.unlockButton.setDisable(true);
            this.useButton.setDisable(true);
            this.useDropDown.setDisable(true);
            this.lockButton.setDisable(true);
        }
    }

}
