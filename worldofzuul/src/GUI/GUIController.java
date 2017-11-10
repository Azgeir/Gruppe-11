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
    
    
    

    public void initialize() {
        // TODO
    }    

    @FXML
    private void pickupButtonHandler(ActionEvent event) {
    }

    @FXML
    private void inventoryButtonHandler(ActionEvent event) {
    }

    @FXML
    private void useButtonAction(ActionEvent event) {
        output.setText("Stuff");
    }

    @FXML
    private void activateButtonHandler(ActionEvent event) {
    }

    @FXML
    private void talkButtonHandler(ActionEvent event) {
       
    }

    @FXML
    private void quitButtonHandler(ActionEvent event) {
    }

    @FXML
    private void helpButtonHandler(ActionEvent event) {
    }

    @FXML
    private void stayButtonHandler(ActionEvent event) {
    }

    @FXML
    private void lookButtonHandler(ActionEvent event) {
    }

    @FXML
    private void saveButtonHandler(ActionEvent event) {
    }

    @FXML
    private void dropButtonHandler(ActionEvent event) {
    }
    
}
