/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

import Acquaintance.IHighscore;
import Acquaintance.IScore;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.RotateEvent;
import javafx.scene.input.SwipeEvent;
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
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import worldofzuul.Game;

/**
 *
 * @author Aske Wulf
 */
public class GUIController {

    @FXML
    private ComboBox<String> useDropDown;
    @FXML
    private Button stayButton;
    @FXML
    private Button pickupButton;
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
    private ComboBox<String> pickupDropDown;
    @FXML
    private Button lockButton;
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
    private TextField textfieldPlayerName;
    @FXML
    private ComboBox<String> goDropDown;
    @FXML
    private Label highscoreLabel;
    @FXML
    private Label labelMessageField;
    @FXML
    private GridPane bigGridPane;
    @FXML
    private GridPane smallGridPane;
    @FXML
    private ComboBox<String> numberBox;
    @FXML
    private Label warningLabel;
    @FXML
    private Button backToStartScreenButton;
    @FXML
    private BorderPane roomBiolab;
    @FXML
    private BorderPane roomStorage;
    @FXML
    private BorderPane roomMedbay;
    @FXML
    private BorderPane roomDorm;
    @FXML
    private BorderPane roomPhysicslab;
    @FXML
    private BorderPane roomDock;
    @FXML
    private BorderPane roomReactor;
    @FXML
    private BorderPane roomControl;
    @FXML
    private BorderPane roomPod;
    @FXML
    private BorderPane roomComputerBiolab;
    @FXML
    private BorderPane roomReactorBiolab;
    @FXML
    private BorderPane roomControlDock;
    @FXML
    private BorderPane roomReactorDock;
    @FXML
    private BorderPane roomReactorStorage;
    @FXML
    private BorderPane roomReactorDorm;
    @FXML
    private BorderPane roomBiolabControl;
    @FXML
    private BorderPane roomDockPhysicslab;
    @FXML
    private BorderPane roomPhysicslabDorm;
    @FXML
    private BorderPane roomMedbayStorage;
    @FXML
    private BorderPane roomDormMedbay;
    @FXML
    private BorderPane roomStorageComputer;
    @FXML
    private BorderPane roomReactorControl;
    @FXML
    private BorderPane roomReactorMedbay;
    @FXML
    private BorderPane roomReactorComputer;
    @FXML
    private BorderPane roomReactorPhysicslab;
    @FXML
    private FlowPane characterPaneStorageComputer;
    @FXML
    private FlowPane characterPaneDormMedbay;
    @FXML
    private FlowPane characterPaneDockPhysicslab;
    @FXML
    private FlowPane characterPaneBiolabControl;
    @FXML
    private FlowPane characterPanePhysicslabDorm;
    @FXML
    private FlowPane characterPaneMedbayStorage;
    @FXML
    private FlowPane characterPaneControlDock;
    @FXML
    private FlowPane characterPaneComputerBiolab;
    private FlowPane characterPaneComputer;
    private FlowPane characterPaneBiolab;
    @FXML
    private FlowPane characterPaneStorage;
    @FXML
    private FlowPane characterPaneMedbay;
    @FXML
    private FlowPane characterPaneDorm;
    @FXML
    private FlowPane characterPanePhysicslab;
    @FXML
    private FlowPane characterPaneDock;
    @FXML
    private FlowPane characterPaneControl;
    @FXML
    private FlowPane characterPaneReactorBiolab;
    @FXML
    private FlowPane characterPaneReactorDock;
    @FXML
    private FlowPane characterPaneReactorStorage;
    @FXML
    private FlowPane characterPaneReactorDorm;
    @FXML
    private FlowPane characterPaneReactorControl;
    @FXML
    private FlowPane characterPaneReactorMedbay;
    @FXML
    private FlowPane characterPaneReactorComputer;
    @FXML
    private FlowPane characterPaneReactorPhysicslab;
    @FXML
    private FlowPane characterPanePod;
    
    private HashMap<String,RoomGUI> rooms;
    @FXML
    private FlowPane characterPaneReactor;
    @FXML
    private FlowPane characterflowPaneComputer;
    @FXML
    private FlowPane characterflowPaneBiolab;
    @FXML
    private Slider spawnTimeSlider;
    @FXML
    private Slider numberOfZuulSlider;


    public void initialize() {
        // TODO

        // create new private method for loading the pictures and layout
        Image buttons = new Image("Pictures/buttons.png");
        Image stars = new Image("Pictures/Stars.jpg");
        BackgroundImage starsBackground = new BackgroundImage(stars, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage buttonsBackground = new BackgroundImage(buttons, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage[] starsBackgroundArray = {starsBackground};

        BackgroundImage[] buttonBackgroundArray = {buttonsBackground};
        this.outerSpace.setBackground(new Background(buttonBackgroundArray));
        this.innerSpace.setBackground(new Background(starsBackgroundArray));
        this.startScreen.setBackground(new Background(starsBackgroundArray));

        this.RoomComputer.setRotate(315);

        fillButtons(bigGridPane);
        fillButtons(smallGridPane);
        fillButtons(startScreen);
        fillButton(backToStartScreenButton);
        
//        this.spawnTimeSlider.setLabelFormatter();
        
        this.highscoreLabel.setText("rank: 1\tplayer: derp\tscore: 0\nrank: 2\tplayer: derp\tscore: 0\nrank: 3\tplayer: derp\tscore: 0\nrank: 4\tplayer: derp\tscore: 0\nrank: 5\tplayer: derp\tscore: 0\nrank: 6\tplayer: derp\tscore: 0\nrank: 7\tplayer: derp\tscore: 0\nrank: 8\tplayer: derp\tscore: 0\nrank: 9\tplayer: derp\tscore: 0\nrank: 10\tplayer: derp\tscore: 0\n");
        String highscoreString = this.loadAndFormatHighscore();
        this.highscoreLabel.setText(highscoreString);
      // WORKS
//        Image herp = new Image("Pictures/ComputerRoom.png");
//        ImageView derp = new ImageView(herp);
//        this.TestImageView.setImage(herp);
//        derp.setFitHeight(200);
//        derp.setFitWidth(200);
//////        this.characterflowPaneComputer.getChildren().add(this.ImageViewZuul); // defined in scene builder
//        this.TestImageView.setImage(herp); // defined in scene builder
//        this.RoomComputer.getChildren().add(derp);
//        this.RoomComputerStackPane.getChildren().add(derp);
//
        Image herp1 = new Image("Pictures/Zuul Transparant.png");
        ImageView derp1 = new ImageView(herp1);
        derp1.setFitHeight(60);
        derp1.setFitWidth(60);
//                this.characterflowPaneComputer.getChildren().add(derp1);
                
        Image transdude = new Image("Pictures/TechDude Transparant.png");
        ImageView techdude = new ImageView(transdude);
        techdude.setFitHeight(40);
        techdude.setFitWidth(40);
//                this.characterflowPaneComputer.getChildren().add(techdude);
//        this.RoomComputerStackPane.getChildren().add(derp1);
//        this.characterPaneComputer.getChildren().add(derp1);
        Image transhero = new Image("Pictures/Hero Transparant.png");
        ImageView hero = new ImageView(transhero);
        hero.setFitHeight(40);
        hero.setFitWidth(40);
//                this.characterflowPaneComputer.getChildren().add(hero);
        // WORKS END
//        Image[] derpArray = {herp};
//        BackgroundImage backDerp = new BackgroundImage(herp, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

//        BackgroundImage[] backDerpArray = {backDerp};

//        this.RoomComputer.setBackg round(new Background(backDerpArray));
//        this.characterflowPaneComputer.setStyle("-fx-background-image: derp");
        this.createRooms();
    }
    
    private void setRoomBackgrounds(){
        for (Map.Entry<String, RoomGUI> entry : rooms.entrySet()) {
            this.setRoomBackground(entry.getValue());
        }
    }
    
    private <T extends Pane> void setRoomBackground(T room, String picturePath){
        Image roomImage = new Image(picturePath);
        BackgroundImage roomBackground = new BackgroundImage(roomImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage[] roomBackgroundArray = {roomBackground};
        room.setBackground(new Background(roomBackgroundArray));
    }
    
    private void setRoomBackground(RoomGUI room){
        if (GUIFacade.isRoomKnown(room)) {
            Image roomImage = new Image(room.getKnownRoomFilePath());
            BackgroundImage roomBackground = new BackgroundImage(roomImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            BackgroundImage[] roomBackgroundArray = {roomBackground};
            room.getLocation().setBackground(new Background(roomBackgroundArray));    
        }
        else {
            Image roomImage = new Image(room.getUnknownRoomFilePath());
            BackgroundImage roomBackground = new BackgroundImage(roomImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            BackgroundImage[] roomBackgroundArray = {roomBackground};
            room.getLocation().setBackground(new Background(roomBackgroundArray));
        }
    }

    @FXML
    private void pickupButtonHandler(ActionEvent event) {

        String command;
        if (this.pickupDropDown.getValue() != null) {
            command = "pickup";
            String item = this.pickupDropDown.getValue();
            command = command + " " + item;
            command = command + " " + this.numberBox.getValue();

            GUIFacade.sendCommand(command);

            this.updateAllDropdown();
            
            String message = GUIFacade.readAndDeleteGameMessage();
            this.labelMessageField.setText(message);

        } else {
            this.labelMessageField.setText("choose something to pickup from the dropbox");
        }
        this.isGameFinished();
    }
 
    @FXML
    private void useButtonAction(ActionEvent event) {

        String command;
        if (this.useDropDown.getValue() != null) {
            command = "use";
            command = command + " " + this.useDropDown.getValue();
            GUIFacade.sendCommand(command);

            this.updateAllDropdown();
            
            String message = GUIFacade.readAndDeleteGameMessage();
            this.labelMessageField.setText(message);
            
        } else {
            this.labelMessageField.setText("choose something to use from the dropbox");
        }
        this.isGameFinished();
//        game.play("use");
    }

    @FXML
    private void activateButtonHandler(ActionEvent event) {
        String command = "activate reactor";
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        this.isGameFinished();
    }

    @FXML
    private void talkButtonHandler(ActionEvent event) {
        String command = "talk";
        
        Scanner input = new Scanner(System.in);
        
        command += " " + input.next();
        
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        this.isGameFinished();
//        game.play("talk");
    }

    @FXML
    private void quitButtonHandler(ActionEvent event) {
        String command = "quit";
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        this.isGameFinished();
//        game.play("quit");
    }

    @FXML
    private void helpButtonHandler(ActionEvent event) {
        String command = "help";
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        this.isGameFinished();
//        game.play("help");
    }

    @FXML
    private void stayButtonHandler(ActionEvent event) {
        String command = "stay";
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        this.isGameFinished();
//        game.play("stay");
    }

    @FXML
    private void lookButtonHandler(ActionEvent event) {
        String command = "look around";
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        
        this.updateAllDropdown();
        this.isGameFinished();

//        game.play(command);
    }

    @FXML
    private void saveButtonHandler(ActionEvent event) {
        GUIFacade.saveGame();

        this.labelMessageField.setText("You saved the game");
        this.isGameFinished();
    }

    @FXML
    private void dropButtonHandler(ActionEvent event) {
        String command;
        if (this.useDropDown.getValue() != null) {
            command = "drop";
            command = command + " " + this.useDropDown.getValue();
            command = command + " " + this.numberBox.getValue();
            GUIFacade.sendCommand(command);
            
            String message = GUIFacade.readAndDeleteGameMessage();
            this.labelMessageField.setText(message);
            
            this.updateAllDropdown();

        } else {
            this.labelMessageField.setText("choose something to drop from the dropbox");
        }
        this.isGameFinished();
    }

    @FXML
    private void goButtonHandler(ActionEvent event) {

        String command;
        if (this.goDropDown.getValue() != null) {
            command = "go";
            command = command + " " + this.goDropDown.getValue();
            GUIFacade.sendCommand(command);
            
            String message = GUIFacade.readAndDeleteGameMessage();
            this.labelMessageField.setText(message);
            
            this.setRoomBackgrounds();
            this.updateAllDropdown();

//            game.play(command);
        } else {
            this.labelMessageField.setText("choose a direction from the dropbox");
        }

        this.isGameFinished();

    }

    @FXML
    private void peekButtonHandler(ActionEvent event) {

        String command;
        if (this.goDropDown.getValue() != null) {
            command = "peek";
            command = command + " " + this.goDropDown.getValue();
            GUIFacade.sendCommand(command);
            
            String message = GUIFacade.readAndDeleteGameMessage();
            this.labelMessageField.setText(message);
            this.setRoomBackgrounds();
//            game.play(command);
        } else {
            this.labelMessageField.setText("choose a direction from the dropbox");
        }
        this.isGameFinished();

    }

    @FXML
    private void unlockButtonHandler(ActionEvent event) {

        String command;
        if (this.goDropDown.getValue() != null) {
            command = "unlock";
            command = command + " " + this.goDropDown.getValue();
            GUIFacade.sendCommand(command);
            
            String message = GUIFacade.readAndDeleteGameMessage();
            this.labelMessageField.setText(message);
//            game.play(command);
        } else {
            this.labelMessageField.setText("choose a direction from the dropbox");
        }

        this.isGameFinished();
    }

    @FXML
    private void lockButtonHandler(ActionEvent event) {

        String command;
        if (this.goDropDown.getValue() != null) {
            command = "lock";
            command = command + " " + this.goDropDown.getValue();
            GUIFacade.sendCommand(command);
            
            String message = GUIFacade.readAndDeleteGameMessage();
            this.labelMessageField.setText(message);
//            game.play(command);
        } else {
            this.labelMessageField.setText("choose a direction from the dropbox");
        }
        this.isGameFinished();
    }

    private void isGameFinished() {
        if (GUIFacade.isGameFinished()) {
//            this.goButton.setDisable(true);
//            this.goDropDown.setDisable(true);
//            this.activateButton.setDisable(true);
//            this.dropButton.setDisable(true);
//            this.helpButton.setDisable(true);
//            this.lookButton.setDisable(true);
//            this.peekButton.setDisable(true);
//            this.pickupButton.setDisable(true);
//            this.pickupDropDown.setDisable(true);
//            this.quitButton.setDisable(true);
//            this.saveButton.setDisable(true);
//            this.stayButton.setDisable(true);
//            this.talkButton.setDisable(true);
//            this.unlockButton.setDisable(true);
//            this.useButton.setDisable(true);
//            this.useDropDown.setDisable(true);
//            this.lockButton.setDisable(true);
//            this.numberBox.setDisable(true);

            this.bigGridPane.setDisable(true);
            
            this.backToStartScreenButton.setVisible(true);
            this.backToStartScreenButton.setDisable(false);
        }
    }

    @FXML
    private void startButtonActionEvent(ActionEvent event) {
        int numberOfZuulAtStart = (int)this.numberOfZuulSlider.getValue();
        double spawnTime = (int)this.spawnTimeSlider.getValue();
        String name = this.textfieldPlayerName.getText();
        
        if (name.length()>0) {
            GUIFacade.initializeGame(numberOfZuulAtStart,spawnTime,name);
        }
        else {
            GUIFacade.initializeGame(numberOfZuulAtStart, spawnTime, "Derp");
        }
        
        this.updateAllDropdown();

        this.switchScreen(startScreen, outerSpace);
        this.setRoomBackgrounds();

        
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        
        this.updateAllDropdown();
    }

    private void updateDropdownBackground(ComboBox<String> box) {
        Image buttonImage = new Image("Pictures/button.png");
        
        BackgroundSize buttonBackgroundSize = new BackgroundSize(box.getLayoutBounds().getWidth(), box.getLayoutBounds().getHeight(), false, false, false, true);
        BackgroundImage buttonBackground = new BackgroundImage(buttonImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, buttonBackgroundSize);
       
        box.setBackground(new Background(buttonBackground));
        box.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setTextFill(Color.WHITE);
                    setAlignment(Pos.CENTER);
                }

            }
        });
    }

    @FXML
    private void loadButtonActionEvent(ActionEvent event) {
        GUIFacade.loadGame();
        this.switchScreen(startScreen, outerSpace);
        
        this.labelMessageField.setText("You loaded the game");
        
        this.updateAllDropdown();
    }

    private void switchScreen(Pane from, Pane to) {
        from.setDisable(true);
        from.setVisible(false);
        to.setDisable(false);
        to.setVisible(true);
    }

    private void updateAllDropdown() {
        
        String selectedPickup = this.pickupDropDown.getValue();
        this.pickupDropDown.getItems().clear();
        if (GUIFacade.isRoomLookedBefore()) {
            Set<String> itemSet = GUIFacade.getRoomItemSet();
            this.pickupDropDown.getItems().addAll(itemSet);
            if (itemSet.contains(selectedPickup)) {
                this.pickupDropDown.setValue(selectedPickup);
            }
        }
        this.updateDropdownBackground(pickupDropDown);

        String selectedUse = this.useDropDown.getValue();
        this.useDropDown.getItems().clear();
        Set<String> inventorySet = GUIFacade.getInventorySet();
        this.useDropDown.getItems().addAll(inventorySet);
        if (inventorySet.contains(selectedUse)) {
            this.useDropDown.setValue(selectedUse);
        }
        this.updateDropdownBackground(useDropDown);

        String selectedGo = this.goDropDown.getValue();
        Set<String> exits = GUIFacade.getExits();
        this.goDropDown.getItems().clear();
        this.goDropDown.getItems().addAll(exits);
        if (exits.contains(selectedGo)) {
            this.goDropDown.setValue(selectedGo);
        }
        this.updateDropdownBackground(goDropDown);

        
        this.updateDropdownBackground(numberBox);
    }

    private String loadAndFormatHighscore() {
        IHighscore highscore = GUIFacade.loadHighscore();
        IScore[] scores = highscore.getScores();

        String highscoreString = "";

        for (int i = 0; i < scores.length; i++) {
            IScore score = scores[i];

            if (score != null) {
                highscoreString += "Rank: " + (i + 1) + "\t";
                highscoreString += "Player: " + score.getName() + "\t";
                highscoreString += "Score: " + ((int) (score.getScore() * 100)) / 100.0 + "\n";
            } else {
                break;
            }
        }
        return highscoreString;
    }

    private void fillButtons(GridPane pane) {
        for (Node node : pane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                this.fillButton(button);
            }
        }
    }
    
    private void fillButton(Button button){
        Image buttonImage = new Image("Pictures/button.png");
        BackgroundImage buttonBackground = new BackgroundImage(buttonImage,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        button.setBackground(new Background(buttonBackground));
        button.setTextFill(Color.WHITE);
    }

    private void updateNumberBox(ComboBox<String> box) {
        int number = 0;
        numberBox.getItems().clear();
        if (box.equals(useDropDown)) {
            number = GUIFacade.getNumberOfItems("Character", useDropDown.getValue());
        } else if (box.equals(pickupDropDown)) {
            number = GUIFacade.getNumberOfItems("Room", pickupDropDown.getValue());
        }
        if (number != 0) {
            for (int i = number; i > 0; i--) {
                numberBox.getItems().add(Integer.toString(i));
            }
        }
        this.numberBox.setValue("1");
    }

    @FXML
    private void pickupDropDownEventHandler(ActionEvent event) {
        updateNumberBox(pickupDropDown);
            if (useDropDown.getValue() != null){
                    useDropDown.setValue(null);
            }
    }

    @FXML
    private void useDropDownEventHandler(ActionEvent event) {
        updateNumberBox(useDropDown);
            if (pickupDropDown.getValue() != null){
                    pickupDropDown.setValue(null);
            }
    }

    @FXML
    private void backToStartScreenButtonEventHandler(ActionEvent event) {
        this.switchScreen(outerSpace, startScreen);
        this.bigGridPane.setDisable(false);
        this.backToStartScreenButton.setDisable(true);
        this.backToStartScreenButton.setVisible(false);
        
        String highscoreString = this.loadAndFormatHighscore();
        this.highscoreLabel.setText(highscoreString);
    }
    
    private void createRooms(){
        rooms = new HashMap<>();
        rooms.put("Biolab", new RoomGUI(roomBiolab,characterPaneBiolab,"Pictures/Biolab.png","Pictures/Biolab FoW.png","Biolab"));
        rooms.put("Computer", new RoomGUI(RoomComputer,characterPaneComputer,"Pictures/computerRoom.png","Pictures/computerRoom FoW.png","Computer"));
        rooms.put("Dock", new RoomGUI(roomDock,characterPaneDock,"Pictures/Dock.png","Pictures/Dock FoW.png","Dock"));
        rooms.put("BiolabControl", new RoomGUI(roomBiolabControl,characterPaneBiolabControl,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","BiolabControl"));
        rooms.put("ComputerBiolab", new RoomGUI(roomComputerBiolab,characterPaneComputerBiolab,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","ComputerBiolab"));
        rooms.put("Control", new RoomGUI(roomControl,characterPaneControl,"Pictures/ControlRoom.png","Pictures/ControlRoom FoW.png","Control"));
        rooms.put("ControlDock", new RoomGUI(roomControlDock,characterPaneControlDock,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","ControlDock"));
        rooms.put("DockPhysicslab", new RoomGUI(roomDockPhysicslab,characterPaneDockPhysicslab,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","DockPhysicslab"));
        rooms.put("Dorm", new RoomGUI(roomDorm,characterPaneDorm,"Pictures/Dorm.png","Pictures/Dorm FoW.png","Dorm"));
        rooms.put("DormMedbay", new RoomGUI(roomDormMedbay,characterPaneDormMedbay,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","DormMedbay"));
        rooms.put("Medbay", new RoomGUI(roomMedbay,characterPaneMedbay,"Pictures/Medbay.png","Pictures/Medbay FoW.png","Medbay"));
        rooms.put("MedbayStorage", new RoomGUI(roomMedbayStorage,characterPaneMedbayStorage,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","MedbayStorage"));
        rooms.put("Physicslab", new RoomGUI(roomPhysicslab,characterPanePhysicslab,"Pictures/Physicslab.png","Pictures/Physicslab FoW.png","Physicslab"));
        rooms.put("PhysicslabDorm", new RoomGUI(roomPhysicslabDorm,characterPanePhysicslabDorm,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","PhysicslabDorm"));
        rooms.put("Pod", new RoomGUI(roomPod,characterPanePod,"Pictures/Pod.png","Pictures/Pod.png","Pod"));
        rooms.put("Reactor", new RoomGUI(roomReactor,characterPaneReactor,"Pictures/Reactor.png","Pictures/Reactor FoW.png","Reactor"));
        rooms.put("ReactorBiolab", new RoomGUI(roomReactorBiolab,characterPaneReactorBiolab,"Pictures/HallwayVertical.png","Pictures/HallwayVertical.png","ReactorBiolab"));
        rooms.put("ReactorComputer", new RoomGUI(roomReactorComputer,characterPaneReactorComputer,"Pictures/HallwayVertical.png","Pictures/HallwayVertical.png","ReactorComputer"));
        rooms.put("ReactorControl", new RoomGUI(roomReactorControl,characterPaneReactorControl,"Pictures/HallwayVertical.png","Pictures/HallwayVertical.png","ReactorControl"));
        rooms.put("ReactorDock", new RoomGUI(roomReactorDock,characterPaneReactorDock,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","ReactorDock"));
        rooms.put("ReactorDorm", new RoomGUI(roomReactorDorm,characterPaneReactorDorm,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","ReactorDorm"));
        rooms.put("ReactorMedbay", new RoomGUI(roomReactorMedbay,characterPaneReactorMedbay,"Pictures/HallwayVertical.png","Pictures/HallwayVertical.png","ReactorMedbay"));
        rooms.put("ReactorPhysicslab", new RoomGUI(roomReactorPhysicslab,characterPaneReactorPhysicslab,"Pictures/HallwayVertical.png","Pictures/HallwayVertical.png","ReactorPhysicslab"));
        rooms.put("ReactorStorage", new RoomGUI(roomReactorStorage,characterPaneReactorStorage,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","ReactorStorage"));
        rooms.put("Storage", new RoomGUI(roomStorage,characterPaneStorage,"Pictures/Storage.png","Pictures/Storage FoW.png","Storage"));
        rooms.put("StorageComputer", new RoomGUI(roomStorageComputer,characterPaneStorageComputer,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal.png","StorageComputer"));
    }

    @FXML
    private void dropButtonHandler(RotateEvent event) {
    }

    @FXML
    private void dropButtonHandler(SwipeEvent event) {
    }

    
}
    



