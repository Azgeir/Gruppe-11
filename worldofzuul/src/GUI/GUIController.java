/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

import java.io.File;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import worldofzuul.Game;

/**
 *
 * @author Aske Wulf
 */
public class GUIController {

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
    @FXML
    private FlowPane characterflowPaneComputer;
    @FXML
    private ImageView TestImageView;
    @FXML
    private BorderPane RoomComputer;
    @FXML
    private StackPane RoomComputerStackPane;
    @FXML
    private GridPane outerSpace;
    @FXML
    private AnchorPane innerSpace;
    @FXML
    private GridPane startScreen;
    @FXML
    private ComboBox<String> testDropDown;

    public void initialize() {
        // TODO

        // create new private method for loading the pictures and layout
        Image buttons = new Image("Pictures/buttons.png");
        Image stars = new Image("Pictures/Stars.jpg");
        BackgroundImage starsBackground = new BackgroundImage(stars, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage buttonBackground = new BackgroundImage(buttons, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage[] starsBackgroundArray = {starsBackground};
        
        BackgroundImage[] buttonBackgroundArray = {buttonBackground};
        this.outerSpace.setBackground(new Background(buttonBackgroundArray));
        this.innerSpace.setBackground(new Background(starsBackgroundArray));
        this.startScreen.setBackground(new Background(starsBackgroundArray));

        Image button = new Image("Pictures/button.png");
        lockButton.setBackground(new Background(buttonBackground));
        lockButton.setTextFill(Color.WHITE);
        
        testDropDown.setBackground(new Background(buttonBackground));
//        pickupDropDown.getValue().
        
        
        // WORKS
        Image herp = new Image("Pictures/Hero.png");
        ImageView derp = new ImageView(herp);
//        this.TestImageView.setImage(herp);
        derp.setFitHeight(200);
        derp.setFitWidth(200);
//////        this.characterflowPaneComputer.getChildren().add(this.ImageViewZuul); // defined in scene builder
//        this.TestImageView.setImage(herp); // defined in scene builder
//        this.RoomComputer.getChildren().add(derp);
//        this.RoomComputerStackPane.getChildren().add(derp);
//
        Image herp1 = new Image("Pictures/Zuul.png");
        ImageView derp1 = new ImageView(herp1);
//        this.RoomComputerStackPane.getChildren().add(derp1);
        this.characterflowPaneComputer.getChildren().add(derp1);


        // WORKS END
        
        Image[] derpArray = {herp};
        BackgroundImage backDerp = new BackgroundImage(herp, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        
        BackgroundImage[] backDerpArray = {backDerp};
        
        this.RoomComputer.setBackground(new Background(backDerpArray));
//        this.characterflowPaneComputer.setStyle("-fx-background-image: derp");
        
        
        
    }

    @FXML
    private void pickupButtonHandler(ActionEvent event) {

        String command;
        if (this.pickupDropDown.getValue() != null) {
            command = "pickup";
            String item = this.pickupDropDown.getValue();
            command = command + " " + item;
            GUIFacade.sendCommand(command);
            
            this.updateAllDropdown();
            
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
            
            this.updateAllDropdown();
            
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
        
        this.updateAllDropdown();
        this.isGameFinished();
        
//        game.play(command);

    }

    @FXML
    private void saveButtonHandler(ActionEvent event) {
        GUIFacade.saveGame();
    
        this.isGameFinished();
    }

    @FXML
    private void dropButtonHandler(ActionEvent event) {
        String command;
        if (this.useDropDown.getValue() != null) {
            command = "drop";
            command = command + " " + this.useDropDown.getValue();
            GUIFacade.sendCommand(command);
            
            this.updateAllDropdown();
            
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
            
            this.updateAllDropdown();
            
            
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

    @FXML
    private void startButtonActionEvent(ActionEvent event) {
        int numberOfZuulAtStart = 3;

        GUIFacade.initializeGame(numberOfZuulAtStart);

        this.updateAllDropdown();
        
        this.switchScreen(startScreen, outerSpace);
    }
    
    private void updateDropdownBackground(){
        Image button = new Image("Pictures/button.png");
        BackgroundSize stuff = new BackgroundSize(pickupDropDown.getLayoutBounds().getWidth(), pickupDropDown.getLayoutBounds().getHeight(), false, false, false, true);
        BackgroundImage buttonBackground = new BackgroundImage(button, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, stuff);
        testDropDown.setBackground(new Background(buttonBackground));
        testDropDown.setButtonCell(new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTextFill(Color.WHITE);
            }            
        });
//        pickupDropDown.getLayoutBounds().getWidth();
//        pickupDropDown.setStyle("-fx-background-repeat: stretch;");
    }

    @FXML
    private void loadButtonActionEvent(ActionEvent event) {
        GUIFacade.loadGame();
        this.switchScreen(startScreen, outerSpace);
        this.updateAllDropdown();
    }
    
    private void switchScreen(Pane from, Pane to){
        from.setDisable(true);
        from.setVisible(false);
        to.setDisable(false);
        to.setVisible(true);
    }
    
    private void updateAllDropdown(){
        
        this.pickupDropDown.getItems().clear();
        if (GUIFacade.isRoomLookedBefore()) {
            Set<String> itemSet = GUIFacade.getRoomItemSet();
            this.pickupDropDown.getItems().addAll(itemSet);
        }
            
        this.useDropDown.getItems().clear();
        Set<String> inventorySet = GUIFacade.getInventorySet();
        this.useDropDown.getItems().addAll(inventorySet);
        
        Set<String> exits = GUIFacade.getExits();
        this.GoDropDown.getItems().clear();
        this.GoDropDown.getItems().addAll(exits);
    }
    
    

}
