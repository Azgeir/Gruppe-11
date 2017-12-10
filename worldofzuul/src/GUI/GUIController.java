/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

import Acquaintance.IHighscore;
import Acquaintance.IScore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
    @FXML
    private FlowPane characterPaneComputer;
    @FXML
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
    private Slider spawnTimeSlider;
    @FXML
    private Slider numberOfZuulSlider;
    @FXML
    private Label StartScreenMessageLabel;


    public void initialize() {
        // TODO
        
        this.setRoomBackground(this.outerSpace, "Pictures/buttons.png");
        this.setRoomBackground(this.innerSpace, "Pictures/Stars.jpg");
        this.setRoomBackground(this.startScreen, "Pictures/Stars.jpg");

        this.RoomComputer.setRotate(315);

        fillButtons(bigGridPane);
        fillButtons(smallGridPane);
        fillButtons(startScreen);
        fillButton(backToStartScreenButton);
        
        this.numberOfZuulSlider.getStylesheets().add("GUI/Slider.css");
        this.numberOfZuulSlider.getStyleClass().add("Slider");
        this.spawnTimeSlider.getStylesheets().add("GUI/Slider.css");
        this.spawnTimeSlider.getStyleClass().add("Slider"); 
        

        String highscoreString = this.loadAndFormatHighscore();
        this.highscoreLabel.setText(highscoreString);
      
        this.createRooms();
    }
    
    private void setCharactersPeek(){
        
        for (RoomGUI room : rooms.values()) {
            ArrayList<ImageView> characterImages = new ArrayList<>();
            
            int i = 0;
            room.getCharacterLocation().getChildren().clear();
            if (GUIFacade.isRoomKnown(room)) {
                Collection<String> characters = GUIFacade.charactersInRoom(room);
                if (characters.size() != 0) {
                    for (String character : characters) {
                        if (character.equals("Zuul")) {
                            characterImages.add(new ImageView(new Image("Pictures/Zuul Transparant.png")));
                            characterImages.get(i).setFitHeight(35);
                            characterImages.get(i).setFitWidth(30);
                            
                            room.getCharacterLocation().getChildren().add(characterImages.get(i));
                            i++;
                        }
                        else if (character.equals("Hero")) {
                            characterImages.add(new ImageView(new Image("Pictures/Hero Transparant.png")));
                            characterImages.get(i).setFitHeight(35);
                            characterImages.get(i).setFitWidth(30);
                            
                            room.getCharacterLocation().getChildren().add(characterImages.get(i));
                            i++;
                        }
                        else {
                            characterImages.add(new ImageView(new Image("Pictures/TechDude Transparant.png")));
                            characterImages.get(i).setFitHeight(35);
                            characterImages.get(i).setFitWidth(30);
                            
                            room.getCharacterLocation().getChildren().add(characterImages.get(i));
                            i++;
                            
                        }
                    }
                }
            }
        }
    }
    
    private void setCharacterCurrentRoom(){
        String roomName = GUIFacade.getCurrentRoomName();
        RoomGUI room = this.rooms.get(roomName);
        
        ArrayList<ImageView> characterImages = new ArrayList<>();
        int i = 0;
        room.getCharacterLocation().getChildren().clear();
        if (GUIFacade.isRoomKnown(room)) {
            Collection<String> characters = GUIFacade.charactersInRoom(room);
            if (!characters.isEmpty()) {
                for (String character : characters) {
                    if (character.equals("Zuul")) {
                        characterImages.add(new ImageView(new Image("Pictures/Zuul Transparant.png")));
                        characterImages.get(i).setFitHeight(35);
                        characterImages.get(i).setFitWidth(30);
                        
                        room.getCharacterLocation().getChildren().add(characterImages.get(i));
                        i++;
                    }
                    else if (character.equals("Hero")) {
                        characterImages.add(new ImageView(new Image("Pictures/Hero Transparant.png")));
                        characterImages.get(i).setFitHeight(35);
                        characterImages.get(i).setFitWidth(30);
                        
                        room.getCharacterLocation().getChildren().add(characterImages.get(i));
                        i++;
                    }
                    else {
                        characterImages.add(new ImageView(new Image("Pictures/TechDude Transparant.png")));
                        characterImages.get(i).setFitHeight(35);
                        characterImages.get(i).setFitWidth(30);
                        
                        room.getCharacterLocation().getChildren().add(characterImages.get(i));
                        i++;
                    }
                }
            }
        }
        
    }
    
    private void setRoomBackgrounds(){
        for (Map.Entry<String, RoomGUI> entry : rooms.entrySet()) {
            this.setRoomBackground(entry.getValue());
        }
    }
    
    private <T extends Pane> void setRoomBackground(T pane, String picturePath){
        Image roomImage = new Image(picturePath);
        BackgroundImage roomBackground = new BackgroundImage(roomImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        BackgroundImage[] roomBackgroundArray = {roomBackground};
        pane.setBackground(new Background(roomBackgroundArray));
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
        this.setCharacterCurrentRoom();
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
        this.setCharacterCurrentRoom();
        this.isGameFinished();
    }

    @FXML
    private void activateButtonHandler(ActionEvent event) {
        String command = "activate Reactor";
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        this.setCharacterCurrentRoom();
        this.isGameFinished();
    }

    @FXML
    private void talkButtonHandler(ActionEvent event) {
        String command = "talk";
        
        command += " " + this.numberBox.getValue();
        
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        this.updateAllDropdown();
        this.setCharacterCurrentRoom();
        this.isGameFinished();
    }

    @FXML
    private void quitButtonHandler(ActionEvent event) {
        String command = "quit";
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        this.setCharacterCurrentRoom();
        this.isGameFinished();
    }

    @FXML
    private void helpButtonHandler(ActionEvent event) {
        String command = "help";
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        this.setCharacterCurrentRoom();
        this.isGameFinished();
    }

    @FXML
    private void stayButtonHandler(ActionEvent event) {
        String command = "stay";
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        this.setCharacterCurrentRoom();
        this.isGameFinished();
    }

    @FXML
    private void lookButtonHandler(ActionEvent event) {
        String command = "look around";
        GUIFacade.sendCommand(command);
        String message = GUIFacade.readAndDeleteGameMessage();
        this.labelMessageField.setText(message);
        
        this.updateAllDropdown();
        this.setCharacterCurrentRoom();
        this.isGameFinished();
    }

    @FXML
    private void saveButtonHandler(ActionEvent event) {
        GUIFacade.saveGame();

        this.labelMessageField.setText("You saved the game");
        this.setCharacterCurrentRoom();
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
        this.setCharacterCurrentRoom();
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
            this.setCharactersPeek();
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
            this.setCharactersPeek();
        } else {
            this.labelMessageField.setText("choose a direction from the dropbox");
            this.setCharacterCurrentRoom();
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
        } else {
            this.labelMessageField.setText("choose a direction from the dropbox");
        }
        this.setCharacterCurrentRoom();
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
        } else {
            this.labelMessageField.setText("choose a direction from the dropbox");
        }
        this.setCharacterCurrentRoom();
        this.isGameFinished();
    }

    private void isGameFinished() {
        if (GUIFacade.isGameFinished()) {
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

        this.StartScreenMessageLabel.setText("");
        this.switchScreen(startScreen, outerSpace);
        this.setRoomBackgrounds();
        this.setCharactersPeek();

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
        boolean success = GUIFacade.loadGame();
        
        if (success) {
            this.switchScreen(startScreen, outerSpace);
            
            this.labelMessageField.setText("You loaded the game");
            
            this.updateAllDropdown();
            this.setRoomBackgrounds();
            this.setCharactersPeek();
        }
        else {
            this.StartScreenMessageLabel.setText("There is no save file");
            
        }
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

        if (GUIFacade.isTalking()) {
            this.useDropDown.setDisable(true);
            this.pickupDropDown.setDisable(true);
            this.useDropDown.setValue(null);
            this.pickupDropDown.setValue(null);
            this.numberBox.getItems().clear();
            this.numberBox.getItems().add("3");
            this.numberBox.getItems().add("2");
            this.numberBox.getItems().add("1");
            this.numberBox.setValue("1");
        }
        else {
            this.useDropDown.setDisable(false);
            this.pickupDropDown.setDisable(false);
            this.numberBox.setValue(null);
        }
        this.updateDropdownBackground(numberBox);
    }

    private String loadAndFormatHighscore() {
        IHighscore highscore = GUIFacade.loadHighscore();
        IScore[] scores = highscore.getScores();
        boolean hasScores = false;
        
        String highscoreString = "";

        for (int i = 0; i < scores.length; i++) {
            IScore score = scores[i];

            if (score != null) {
                highscoreString += "Rank: " + (i + 1) + "\t";
                highscoreString += "Player: " + score.getName() + "\t";
                highscoreString += "Score: " + ((int) (score.getScore() * 100)) / 100.0 + "\n";
                hasScores = true;
            } else {
                break;
            }
        }
        
        if (!hasScores) {
            for (int i = 1; i <= 10; i++) {
                highscoreString += "rank: "+ i +"\tplayer: derp\tscore: 0\n";
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
        rooms.put("BiolabControl", new RoomGUI(roomBiolabControl,characterPaneBiolabControl,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","BiolabControl"));
        rooms.put("ComputerBiolab", new RoomGUI(roomComputerBiolab,characterPaneComputerBiolab,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","ComputerBiolab"));
        rooms.put("Control", new RoomGUI(roomControl,characterPaneControl,"Pictures/ControlRoom.png","Pictures/ControlRoom FoW.png","Control"));
        rooms.put("ControlDock", new RoomGUI(roomControlDock,characterPaneControlDock,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","ControlDock"));
        rooms.put("DockPhysicslab", new RoomGUI(roomDockPhysicslab,characterPaneDockPhysicslab,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","DockPhysicslab"));
        rooms.put("Dorm", new RoomGUI(roomDorm,characterPaneDorm,"Pictures/Dorm.png","Pictures/Dorm FoW.png","Dorm"));
        rooms.put("DormMedbay", new RoomGUI(roomDormMedbay,characterPaneDormMedbay,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","DormMedbay"));
        rooms.put("Medbay", new RoomGUI(roomMedbay,characterPaneMedbay,"Pictures/Medbay.png","Pictures/Medbay FoW.png","Medbay"));
        rooms.put("MedbayStorage", new RoomGUI(roomMedbayStorage,characterPaneMedbayStorage,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","MedbayStorage"));
        rooms.put("Physicslab", new RoomGUI(roomPhysicslab,characterPanePhysicslab,"Pictures/Physicslab.png","Pictures/Physicslab FoW.png","Physicslab"));
        rooms.put("PhysicslabDorm", new RoomGUI(roomPhysicslabDorm,characterPanePhysicslabDorm,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","PhysicslabDorm"));
        rooms.put("Pod", new RoomGUI(roomPod,characterPanePod,"Pictures/Pod.png","Pictures/Pod FoW.png","Pod"));
        rooms.put("Reactor", new RoomGUI(roomReactor,characterPaneReactor,"Pictures/Reactor.png","Pictures/Reactor FoW.png","Reactor"));
        rooms.put("ReactorBiolab", new RoomGUI(roomReactorBiolab,characterPaneReactorBiolab,"Pictures/HallwayVertical.png","Pictures/HallwayVertical FoW.png","ReactorBiolab"));
        rooms.put("ReactorComputer", new RoomGUI(roomReactorComputer,characterPaneReactorComputer,"Pictures/HallwayVertical.png","Pictures/HallwayVertical FoW.png","ReactorComputer"));
        rooms.put("ReactorControl", new RoomGUI(roomReactorControl,characterPaneReactorControl,"Pictures/HallwayVertical.png","Pictures/HallwayVertical FoW.png","ReactorControl"));
        rooms.put("ReactorDock", new RoomGUI(roomReactorDock,characterPaneReactorDock,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","ReactorDock"));
        rooms.put("ReactorDorm", new RoomGUI(roomReactorDorm,characterPaneReactorDorm,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","ReactorDorm"));
        rooms.put("ReactorMedbay", new RoomGUI(roomReactorMedbay,characterPaneReactorMedbay,"Pictures/HallwayVertical.png","Pictures/HallwayVertical FoW.png","ReactorMedbay"));
        rooms.put("ReactorPhysicslab", new RoomGUI(roomReactorPhysicslab,characterPaneReactorPhysicslab,"Pictures/HallwayVertical.png","Pictures/HallwayVertical FoW.png","ReactorPhysicslab"));
        rooms.put("ReactorStorage", new RoomGUI(roomReactorStorage,characterPaneReactorStorage,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","ReactorStorage"));
        rooms.put("Storage", new RoomGUI(roomStorage,characterPaneStorage,"Pictures/Storage.png","Pictures/Storage FoW.png","Storage"));
        rooms.put("StorageComputer", new RoomGUI(roomStorageComputer,characterPaneStorageComputer,"Pictures/HallwayHorizontal.png","Pictures/HallwayHorizontal FoW.png","StorageComputer"));
    }
}
    



