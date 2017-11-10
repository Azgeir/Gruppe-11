/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zuulgui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import worldofzuul.Game;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class GUIController implements Initializable {

    @FXML
    private Button pickupButtonHandler;
    @FXML
    private Button inventoryButtonHandler;
    @FXML
    private Button useButtonAction;
    @FXML
    private Button activateButtonHandler;
    @FXML
    private Button talkButtonHandler;
    @FXML
    private Button quitButtonHandler;
    @FXML
    private Button helpButtonHandler;
    @FXML
    private Button stayButton;
    @FXML
    private Button lookButtonHandler;
    @FXML
    private Button saveButtonHandler;
    @FXML
    private Button dropButtonHandler;
    @FXML
    private TextArea output;
    @FXML
    private Label testLabel;
    private Game game;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        game = new Game();
    }    

    @FXML
    private void pickupButtonHandler(ActionEvent event) {
    }

    @FXML
    private void inventoryButtonHandler(ActionEvent event) {
    }

    @FXML
    private void useButtonAction(ActionEvent event) {
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
        game.play("stay");
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
