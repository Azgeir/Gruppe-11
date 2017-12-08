package Logic;

// Imports:
import Acquaintance.IGame;
import Acquaintance.IHighscore;
import Acquaintance.IRoom;
import Acquaintance.IScore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
// This class runs the main functionality of the game.
public class Game implements IGame, Serializable{

    // Data fields:
    private Parser parser;
    // ArrayList is chosen because it allows us to know which character is chosen when the initiative is the same.
    private ArrayList<Character> characters = new ArrayList<>(); // This is an arraylist because it offer a simple way to break an initiative tie in a predefined way
    private Character currentCharacter;
    private HashMap<String, Room> rooms;
    private Room winConditionRoom;
    private double maxInititative = Double.MAX_VALUE;
    private boolean reactorActivated = false;
    private boolean finished;
    private double timeSinceSpawn;
    private double spawnTime;
    private String name;
    private String message;

    // This constructor creates a Game object by creating a Parser and calling the createRooms method.
    Game() {
        this(1);
    }
    
    Game(int numberOfZuul) {
        this(numberOfZuul,200);
    }
    
    Game(int numberOfZuul, double spawnTime){
        this(numberOfZuul, spawnTime, "Derp");
    }
    
    Game(int numberOfZuul, double spawnTime, String name){
        this.finished = false;
        this.timeSinceSpawn = 0;
        this.spawnTime = spawnTime;
        this.rooms = new HashMap<>();
        this.name = name;
        this.message = "";
        //Create all rooms by calling the createRooms method
        createRooms();
        // Create the characters by calling the createCharacter() method
        createCharacter(numberOfZuul);
        // Create a parser
        parser = new Parser();
        this.printWelcome();
        // Select current character
        this.currentCharacter = this.chooseCharacter();
    }

    // This method creates the rooms of the game.
    private void createRooms() {
        // Declare the rooms
        Room biologyLaboratory, computerRoom, storage, medicalBay, dormitory,
                physicsLaboratory, dock, controlRoom, reactor, escapePod;

        // Initialize the biology laboratory
        biologyLaboratory = new Room("in the biology laboratory", "Biolab",
                ("You are in the biology laboratory. On the table to your right\n"
                + "there is a row of microscopes, and the shelf above contains a\n"
                + "collection of test tubes with liquids of different colours.\n"
                + "In the corner of the room there is a computer. Maybe you'll be\n"
                + "able to save some of the research.\n"));

        // Initialise the computer room
        computerRoom = new Room("in the computer room", "Computer",
                ("You are in the computer room. This is where you had your working\n"
                + "hours. You see several computers, chairs, USBs, and larger storage\n"
                + "units. You also see your access card lying on the desk.\n"));

        // Initialise the storage room
        storage = new Room("in the storage room", "Storage",
                ("You are in the storage facility. Here you see several drawers and\n"
                + "shelves, containing everything from dried food to different tools,\n"
                + "medkits and reseach compartments.\n"));

        // Initialise the medical bay
        medicalBay = new Room("in the medical bay", "Medbay",
                ("You are in the medical bay. This is where you would get treated and\n"
                + "contained if you fell sick or got minor injuries. There are beds\n"
                + "and several drawers with medkits, pills and syringes.\n"));

        // Initialise the dormitory
        dormitory = new Room("in the dormitory", "Dorm",
                ("You are in the dormitory. This is where you used to sleep and\n"
                + "relax with your colleagues. Sadly, this is also where the monster\n"
                + "arrived. You see the corpses of your beloved colleagues scattered\n"
                + "around the room.\n"));

        // Initialise the physics laboratory
        physicsLaboratory = new Room("in the physics laboratory", "Physicslab",
                "You are in the physics laboratory. The room is filled with various\n"
                + "equipment. On a table nearby, you see a Helmholtz coil, and on your\n"
                + "right, there is a mass spectrometer. In the corner of the room, you\n"
                + "see a computer. Maybe you'll be able to save some of the research.\n");

        // Initialise the dock
        dock = new Room("in the dock", "Dock",
                "You are in the dock. This is where supply ships come and go and also \n"
                + "the only way off the space station via the pod. However, the space station \n"
                + "is currently under quarantine and you don't know how to overwrite\n"
                + "it. Around you, you see tools for repairs, 3D printers and space suits \n");

        // Initialise the control room
        controlRoom = new Room("in the control room", "Control",
                ("You are in the control room. This is where information goes to and\n"
                + "from the space station. This is where you find the tech dude. He was \n"
                + "trying to reestablish the connection to earth, but to no avail.\n"
                + "Maybe you could get surveilliance data back with you."));

        // Initialise the reactor
        reactor = new Room("near the reactor", "Reactor",
                ("You are in the reactor room. The reactor is a very dense nuclear\n"
                + "reactor. If it were to melt down the space station would be annihilated.\n"
                + "You see some basic tools, some Geiger counters, and a coupple of\n"
                + "space suits.\n"));

        // Initialise the escape pod
        escapePod = new Room("in the escape pod", "Pod");

        // Declare hallways between rooms
        Room hallwayStorageComputer, hallwayComputerBiology, hallwayBiologyControl,
                hallwayControlDock, hallwayDockPhysics, hallwayPhysicsDormitory,
                hallwayDormitoryMedical, hallwayMedicalStorage;

        // Initialize the hallways between the outer rooms
        hallwayStorageComputer = new Room("in the hallway between the storage and computer rooms", "StorageComputer");
        hallwayComputerBiology = new Room("in the hallway between the computer room and the biology laboratory","ComputerBiolab");
        hallwayBiologyControl = new Room("in the hallway between the biology laboratory and the control room", "BiolabControl");
        hallwayControlDock = new Room("in the hallway between the control room and the dock", "ControlDock");
        hallwayDockPhysics = new Room("in the hallway between the dock and the physics laboratory", "DockPhysicslab");
        hallwayPhysicsDormitory = new Room("in the hallway between the physics laboratory and the dormitory", "PhysicslabDorm");
        hallwayDormitoryMedical = new Room("in the hallway between the dormitory and the medical bay", "DormMedbay");
        hallwayMedicalStorage = new Room("in the hallway between the medical bay and the storage room", "MedbayStorage");

        // Declare hallways connected to the reactor
        Room hallwayReactorBiology, hallwayReactorControl, hallwayReactorDock,
                hallwayReactorPhysics, hallwayReactorDormitory, hallwayReactorMedical,
                hallwayReactorStorage, hallwayReactorComputer;

        // Initialize the hallways connected to the reactor
        hallwayReactorBiology = new Room("in the hallway between the reactor and the biology laboratory", "ReactorBiolab");
        hallwayReactorControl = new Room("in the hallway between the reactor and the control room", "ReactorControl");
        hallwayReactorDock = new Room("in the hallway between the reactor and the dock", "ReactorDock");
        hallwayReactorPhysics = new Room("in the hallway between the reactor and the physics laboratory", "ReactorPhysicslab");
        hallwayReactorDormitory = new Room("in the hallway between the reactor and the dormitory", "ReactorDorm");
        hallwayReactorMedical = new Room("in the hallway between the reactor and the medical bay", "ReactorMedbay");
        hallwayReactorStorage = new Room("in the hallway between the reactor and the storage room", "ReactorStorage");
        hallwayReactorComputer = new Room("in the hallway between the reactor and the computer room", "ReactorComputer");

        // Set possible exits for hallways between rooms
        hallwayStorageComputer.setExit("Storage", storage, false);
        hallwayStorageComputer.setExit("Computer", computerRoom, false);

        hallwayComputerBiology.setExit("Computer", computerRoom, false);
        hallwayComputerBiology.setExit("Biolab", biologyLaboratory, false);

        hallwayBiologyControl.setExit("Control", controlRoom, true);
        hallwayBiologyControl.setExit("Biolab", biologyLaboratory, false);

        hallwayControlDock.setExit("Control", controlRoom, true);
        hallwayControlDock.setExit("Dock", dock, false);

        hallwayDockPhysics.setExit("Physicslab", physicsLaboratory, false);
        hallwayDockPhysics.setExit("Dock", dock, false);

        hallwayPhysicsDormitory.setExit("Physicslab", physicsLaboratory, false);
        hallwayPhysicsDormitory.setExit("Dorm", dormitory, false);

        hallwayDormitoryMedical.setExit("Medbay", medicalBay, false);
        hallwayDormitoryMedical.setExit("Dorm", dormitory, false);

        hallwayMedicalStorage.setExit("Medbay", medicalBay, false);
        hallwayMedicalStorage.setExit("Storage", storage, false);

        // Set possible exits for hallways from the reactor
        hallwayReactorBiology.setExit("Reactor", reactor, false);
        hallwayReactorBiology.setExit("Biolab", biologyLaboratory, false);

        hallwayReactorControl.setExit("Reactor", reactor, false);
        hallwayReactorControl.setExit("Control", controlRoom, true);

        hallwayReactorDock.setExit("Reactor", reactor, false);
        hallwayReactorDock.setExit("Dock", dock, false);

        hallwayReactorPhysics.setExit("Reactor", reactor, false);
        hallwayReactorPhysics.setExit("Physicslab", physicsLaboratory, false);

        hallwayReactorDormitory.setExit("Reactor", reactor, false);
        hallwayReactorDormitory.setExit("Dorm", dormitory, false);

        hallwayReactorMedical.setExit("Reactor", reactor, false);
        hallwayReactorMedical.setExit("Medbay", medicalBay, false);

        hallwayReactorStorage.setExit("Reactor", reactor, false);
        hallwayReactorStorage.setExit("Storage", storage, false);

        hallwayReactorComputer.setExit("Reactor", reactor, false);
        hallwayReactorComputer.setExit("Computer", computerRoom, false);

        // Set the possible exits for each room
        biologyLaboratory.setExit("Computer", hallwayComputerBiology, false);
        biologyLaboratory.setExit("Control", hallwayBiologyControl, false);
        biologyLaboratory.setExit("Reactor", hallwayReactorBiology, false);

        controlRoom.setExit("Biolab", hallwayBiologyControl, true);
        controlRoom.setExit("Dock", hallwayControlDock, true);
        controlRoom.setExit("Reactor", hallwayReactorControl, true);

        dock.setExit("Control", hallwayControlDock, false);
        dock.setExit("Physicslab", hallwayDockPhysics, false);
        dock.setExit("Reactor", hallwayReactorDock, false);
        dock.setExit("Pod", escapePod, true);

        physicsLaboratory.setExit("Dock", hallwayDockPhysics, false);
        physicsLaboratory.setExit("Dorm", hallwayPhysicsDormitory, false);
        physicsLaboratory.setExit("Reactor", hallwayReactorPhysics, false);

        dormitory.setExit("Physicslab", hallwayPhysicsDormitory, false);
        dormitory.setExit("Medbay", hallwayDormitoryMedical, false);
        dormitory.setExit("Reactor", hallwayReactorDormitory, false);

        medicalBay.setExit("Dorm", hallwayDormitoryMedical, false);
        medicalBay.setExit("Storage", hallwayMedicalStorage, false);
        medicalBay.setExit("Reactor", hallwayReactorMedical, false);

        storage.setExit("Medbay", hallwayMedicalStorage, false);
        storage.setExit("Computer", hallwayStorageComputer, false);
        storage.setExit("Reactor", hallwayReactorStorage, false);

        computerRoom.setExit("Storage", hallwayStorageComputer, false);
        computerRoom.setExit("Biolab", hallwayComputerBiology, false);
        computerRoom.setExit("Reactor", hallwayReactorComputer, false);

        escapePod.setExit("Dock", dock, false);

        // Set the exits for the reactor room
        reactor.setExit("Computer", hallwayReactorComputer, false);
        reactor.setExit("Biolab", hallwayReactorBiology, false);
        reactor.setExit("Control", hallwayReactorControl, false);
        reactor.setExit("Dock", hallwayReactorDock, false);
        reactor.setExit("Physicslab", hallwayReactorPhysics, false);
        reactor.setExit("Dorm", hallwayReactorDormitory, false);
        reactor.setExit("Medbay", hallwayReactorMedical, false);
        reactor.setExit("Storage", hallwayReactorStorage, false);

        // Set the current room to "computer" (Possibly moved to character class)
        rooms.put("Computer", computerRoom);
        rooms.put("Control", controlRoom);
        rooms.put("Dorm", dormitory);
        rooms.put("Biolab", biologyLaboratory);
        rooms.put("Storage", storage);
        rooms.put("Medbay", medicalBay);
        rooms.put("Physicslab", physicsLaboratory);
        rooms.put("Dock", dock);
        rooms.put("Control", controlRoom);
        rooms.put("Reactor", reactor);
        rooms.put("ReactorBiolab", hallwayReactorBiology);
        rooms.put("ReactorControl", hallwayReactorControl);
        rooms.put("ReactorDock", hallwayReactorDock);
        rooms.put("ReactorPhysicslab", hallwayReactorPhysics);
        rooms.put("ReactorDorm", hallwayReactorDormitory);
        rooms.put("ReactorMedbay", hallwayReactorMedical);
        rooms.put("ReactorStorage", hallwayReactorStorage);
        rooms.put("ReactorComputer", hallwayReactorComputer);
        rooms.put("StorageComputer",hallwayStorageComputer);
        rooms.put("ComputerBiolab",hallwayComputerBiology);
        rooms.put("BiolabControl",hallwayBiologyControl);
        rooms.put("ControlDock",hallwayControlDock);
        rooms.put("DockPhysicslab",hallwayDockPhysics);
        rooms.put("PhysicslabDorm",hallwayPhysicsDormitory);
        rooms.put("DormMedBay",hallwayDormitoryMedical);
        rooms.put("MedbayStorage",hallwayMedicalStorage);
        rooms.put("Pod", escapePod);

        // Set the value of the win condition
        winConditionRoom = escapePod;

        // Set the initial positions of Zuul, hero, and tech dude
//        dormitory.setHasCharacter("Zuul", true);
//        computerRoom.setHasCharacter("Hero", true);
//        controlRoom.setHasCharacter("TechDude", true);

        // Add items to the inventory of the rooms
        this.fillRoom(computerRoom);
        this.fillRoom(storage);
        this.fillRoom(medicalBay);
        this.fillRoom(biologyLaboratory);
        this.fillRoom(dock);
        this.fillRoom(reactor);
        this.fillRoom(controlRoom);
        this.fillRoom(physicsLaboratory);
        this.fillRoom(dormitory);
        this.fillRoom(escapePod);
    }

    // This method initialises the inventory of the given room
    private void fillRoom(Room room) {
        switch (room.getName()) {
            case "Biolab":
                room.getInventory().addItem(new Item(10, "notebook", "smash it down on the floor in pure anger."));
                room.getInventory().addItem(new Item(50, "microscope"), 3);
                room.getInventory().addItem(new Item(1000, "DNA-sequencing-machine"));
                room.getInventory().addItem(new Item(150, "incubator"), 2);
                room.getInventory().addItem(new Item(500, "refrigerator"), 2);
                room.getInventory().addItem(new Item(20, "animal-cell-model"));
                room.getInventory().addItem(new Item(20, "plant-cell-model"));
                room.getInventory().addItem(new Item(20, "water-sample", "piss in it to show dominance."));
                room.getInventory().addItem(new Item(20, "air-sample"));
                room.getInventory().addItem(new Item(10, "methylene-blue-stain"), 10);
                room.getInventory().addItem(new Item(10, "Gram's-iodine-solution"), 10);
                room.getInventory().addItem(new Item(5, "pipette"), 20);
                room.getInventory().addItem(new Item(5, "microscope-slide"), 20);
                room.getInventory().addItem(new Item(10, "cell-lysis-solution"), 10);
                room.getInventory().addItem(new Item(10, "DNA-precipitate-solution"), 10);
                room.getInventory().addItem(new Item(5, "test-tube"), 20);
                room.getInventory().addItem(new Item(400, "centrifuge"), 2);
                room.getInventory().addItem(new Item(99, "cat", "pet it. It scratces your hand."));
                room.getInventory().addItem(new AcidVial(4, 25), 10);
                break;
            case "Computer":
                room.getInventory().addItem(new USB(3));
                room.getInventory().addItem(new USB(2));
                room.getInventory().addItem(new USB(1));
                room.getInventory().addItem(new Item(150, "computer-monitor"), 10);
                room.getInventory().addItem(new Item(10, "computer-mouse"), 10);
                room.getInventory().addItem(new Item(20, "keyboard", "write code, but it dosen't work, so you smash it into pieces."), 10);
                room.getInventory().addItem(new AccessCard());
                break;
            case "Storage":
                room.getInventory().addItem(new AcidVial(4, 25), 5);
                room.getInventory().addItem(new MedKit(), 10);
                room.getInventory().addItem(new Item(400, "box"), 30);
                room.getInventory().addItem(new Item(600, "bed"), 15);
                room.getInventory().addItem(new Item(75, "electronics"), 25);
                room.getInventory().addItem(new Item(1350, "solarpanel"), 15);
                room.getInventory().addItem(new Item(20, "hammer","hit yourself in hope of convincing yourself that this is not reality."), 13); 
                room.getInventory().addItem(new Item(20, "saw"), 9);
                room.getInventory().addItem(new Item(20, "crowbar", "smash some stuff and pretend that you are Gordon Freeman. "
                        + "This isn't Half Life 3, you dumbass!"), 7);
                room.getInventory().addItem(new Item(20, "plasma-saw"), 6);
                room.getInventory().addItem(new Item(30, "nailgun", "shoot all over the place like a stormtrooper."), 8);
                room.getInventory().addItem(new AccessCard());
                break;
            case "Medbay":
                room.getInventory().addItem(new AcidVial(4, 25), 7);
                room.getInventory().addItem(new MedKit(), 10);
                room.getInventory().addItem(new Item(5, "syringe", "stick it at yourself to see, if you end up tripping balls."), 17);
                room.getInventory().addItem(new Item(2, "pill", "try to aquire superpowers, but you end up having extreme explosive "
                        + "diaherra being shot out of your ass as a cannon."), 29);
                room.getInventory().addItem(new Item(6, "glass"), 49);
                room.getInventory().addItem(new Item(20, "book", "read up on how to fart like a machine gun."), 50);
                room.getInventory().addItem(new Item(20, "coat"), 19);
                room.getInventory().addItem(new Item(400, "desk"), 8);
                room.getInventory().addItem(new Item(100, "display-skeleton"), 5);
                break;
            case "Dorm":
                room.getInventory().addItem(new Item(20, "cake", "you try to eat the cake but remember that the cake is a lie"));
                room.getInventory().addItem(new Item(10, "dehydrated-food"), 50);
                room.getInventory().addItem(new Item(600, "bed"), 20);
                room.getInventory().addItem(new Item(10, "pillow", "remind yourself that Mommy is not going to save you."), 20);
                room.getInventory().addItem(new Item(500, "duvet", "clean the stain where you pissed yourself at."), 20);
                room.getInventory().addItem(new Item(200, "corpse"), 10);
                room.getInventory().addItem(new Item(600, "couch"));
                room.getInventory().addItem(new Item(200, "table"), 2);
                room.getInventory().addItem(new Item(600, "bookcase"));
                room.getInventory().addItem(new Item(10, "book", "read up on how to fart like a machine gun."), 100);
                room.getInventory().addItem(new Item(600, "desk"), 2);
                room.getInventory().addItem(new Item(75, "chair", "sit down on it to contemplate about your life choices at the moment. "
                        + "Honestly, don't you have better things to do!"), 6);
                break;
            case "Physicslab":
                room.getInventory().addItem(new AcidVial(4, 25), 7);
                room.getInventory().addItem(new MedKit(), 10);
                room.getInventory().addItem(new Item(5, "test-tube"), 20);
                room.getInventory().addItem(new Item(20, "coat"), 10);
                room.getInventory().addItem(new Item(15, "knife"), 10);
                room.getInventory().addItem(new Item(5, "syringe", "stick it at yourself to see, if you become one with the Matrix."), 15);
                room.getInventory().addItem(new Item(400, "desk"), 2);
                room.getInventory().addItem(new Item(75, "chair", "sit down on it to contemplate about your life choices at the moment. "
                        + "Seriosly, don't you not have better things to do!"), 10);
                room.getInventory().addItem(new Item(175, "computer"), 5);
                room.getInventory().addItem(new Item(200, "table"), 4);
                room.getInventory().addItem(new Item(1000, "quantum-equipment"), 29);
                room.getInventory().addItem(new Item(5, "test-tube"), 20);
                room.getInventory().addItem(new Item(10, "funny-chemical", "sniff at it, then lick at it and then scream 'LEEROY JENKINS'."), 25);
                break;
            case "Dock":
                room.getInventory().addItem(new Item(1500, "crate"), 30);
                room.getInventory().addItem(new Item(500, "fuel-station"), 1);
                room.getInventory().addItem(new Item(400, "barrel"), 40);
                room.getInventory().addItem(new Item(50, "baggage"), 10);
                room.getInventory().addItem(new Item(150, "computer-moniter"), 15);
                room.getInventory().addItem(new Item(100, "spacesuit"), 10);
                room.getInventory().addItem(new Item(200, "corpse"), 2);
                room.getInventory().addItem(new Item(500, "3D-printer", "scan your ass and print it for science."), 1);
                break;
            case "Control":
                room.getInventory().addItem(new MedKit(), 10);
                room.getInventory().addItem(new Item(175, "computer"), 5);
                room.getInventory().addItem(new Item(150, "computer-monitor"), 15);
                room.getInventory().addItem(new Item(20, "keyboard", "write code, but it dosen't work, so you smash it into pieces."), 15);
                room.getInventory().addItem(new Item(10, "screwdriver"), 6);
                room.getInventory().addItem(new Item(20, "hammer"), 4);
                room.getInventory().addItem(new Item(1, "paper", "fold a paper plane and make StarWars sounds."), 20);
                break;
            case "Reactor":
                room.getInventory().addItem(new Item(150, "computer-monitor"), 10);
                room.getInventory().addItem(new Item(175, "computer"), 4);
                room.getInventory().addItem(new Item(10, "screwdriver"), 8);
                room.getInventory().addItem(new Item(1500, "crate"), 10);
                room.getInventory().addItem(new Item(20, "Geiger-counter"), 2);
                room.getInventory().addItem(new Item(100, "spacesuit"), 2);
                break;
            case "Pod":
                room.getInventory().addItem(new MedKit());
                room.getInventory().addItem(new Item(10, "dehydrated-food"), 5);
                room.getInventory().addItem(new Item(20, "hammer"), 2);
                room.getInventory().addItem(new Item(10, "screwdriver"), 2);
                break;
            default:
                break;
        }
    }

    // This method creates the hero, monster, and tech dude and adds them to the array list of characters.
    private void createCharacter(int numberOfZuul) {
        this.characters.add(new Hero(rooms.get("Computer"), "Hero"));
        for (int i = 0; i < numberOfZuul; i++) {
            this.characters.add(new Zuul(rooms.get("Dorm"), "Zuul", 1.15));
        }
        this.characters.add(new TechDude(rooms.get("Control"), "TechDude", 0.5));
    }
    
    private void timeAddedZuul(double initiativeBefore){
        if (this.timeSinceSpawn>spawnTime) {
            Room randomRoom = this.randomRoom();
            this.characters.add(new Zuul(randomRoom,"Zuul",1.15,this.currentCharacter.getCharacterInitiative()));
            this.timeSinceSpawn += currentCharacter.getCharacterInitiative()-initiativeBefore;
            this.timeSinceSpawn -= spawnTime;
        }
        else {
            this.timeSinceSpawn += currentCharacter.getCharacterInitiative()-initiativeBefore;
        }
    }

    // This method plays the game
    void play(String GUICommand) {
        // Call the printWelcome method to show a brief introduction to the game
//        printWelcome();
        // Check if the player is still playing
        // As long as game is not finished, get and process user commands
        //        while (!finished) {
        do {
            
            // checks if the TechDude has met the Hero
            //techDudeMeetHero();
            // Get command from parser
            Command command = parser.getCommand(this.currentCharacter, GUICommand);
            // records character initiative before performing the command
            double initiativeBefore = this.currentCharacter.getCharacterInitiative();
            // Process command
            finished = processCommand(command);

            if (currentCharacter.getName().equals("Hero")) {
                this.timeAddedZuul(initiativeBefore);
            }
            
            // Check if player lost game because of reactor
            if (!finished) {
                finished = timerLose();
            }
            // Check if player won game
            if (!finished) {
                finished = winTest();
            }
            if (!finished) {
                finished = healthTest();
            }
            
            currentCharacter = this.chooseCharacter();
        } while(!currentCharacter.getName().equals("Hero") && !finished);
    }

    // This method prints the welcome message.
    private void printWelcome() {
        // Print welcome message
        message += ("\n");
        message += ("Welcome to Escape Pod!\n");
        message += ("\nYou are a software engineer in a space station. \n"
                + "The emergency alarm has just gone off, and the station is under \n"
                + "quarantine. You must find items and other survivors and escape \n"
                + "the station through the escape pod before you are caught by what \n"
                + "is ravaging the station. \n"
                + "\nYou suddenly hear a rumbling voice emanating from everywhere:\n"
                + "\n\"We are Zuul, devourers of worlds.\"\n");
        message += ("\nPress 'Help' for more information about controls and the game.\n");
        message += ("\n");
        // Description of current room of the player, including available exits.
        message += (rooms.get("Computer").getLongDescription() + "\n");
    }

    // This method processes the command of the player (returns true if player wants to quit)
    private boolean processCommand(Command command) {
        // Assume the player wants to continue playing
        boolean wantToQuit = false;

        // Create instance of CommandWord using the command word of the specified command (from Parser)
        CommandWord commandWord = command.getCommandWord();

        // Check if the input equals any of the defined commands and print an "error" if it does not
        if (command.isUnknown()) {
            LogicFacade.appendMessage("I don't know what you mean...\nTyping 'help' will give you the valid commands.");
            return false;
        }

        if (null != commandWord) // Execute the command if the input matches a valid command
        {
            switch (commandWord) {
                // If command is "help", print help message
                case HELP:
                    printHelp();
                    break;
                // If command is "go", call go() method on current character
                case GO:
                    this.currentCharacter.performCommand(command);
                    break;
                // If command is "quit", call quit() method (returns true, if player wants to quit)
                case QUIT:
                    wantToQuit = quit(command);
                    break;
                // If command is "stay", call stay() method on current character
                case STAY:
                    this.currentCharacter.performCommand(command);
                    break;
                // If command is "pickup", call pickup() method on current character
                case PICKUP:
                    this.currentCharacter.performCommand(command);
                    break;
                // If command is "drop", call dropItem() method on current character
                case DROP:
                    this.currentCharacter.performCommand(command);
                    break;
                // If command is "look", call look() method on current character
                case LOOK:
                    this.currentCharacter.performCommand(command);
                    break;
                // If command is "peek", call peek() method on current character
                case PEEK:
                    this.currentCharacter.performCommand(command);
                    break;
                // (?)If command is "use", call use() method on current character and change Zuul's initiative
                case USE:
                    double zuulInitiativeReduction = this.currentCharacter.performCommand(command);
                    
                    for (Character character : characters) {
                        if (character.getName().equals("Zuul") && character.getCurrentRoom().equals(this.currentCharacter.getCurrentRoom())) {
                            character.setCharacterInitiative(character.getCharacterInitiative() + zuulInitiativeReduction);
                        }
                    }
                    break;
                // If command is "lock", call lock() command on current character
                case LOCK:
                    this.currentCharacter.performCommand(command);
                    break;
                // If command is "unlock", call unlock() command on current character
                case UNLOCK:
                    this.currentCharacter.performCommand(command);
                    break;
                // If command is "activate", set MaxInitiative to the return value of the activate() method
                case ACTIVATE:
                    double newInitiative = this.currentCharacter.performCommand(command);
                    if(this.currentCharacter.getMessage() != null) {
                        this.reactorActivated = true;
                        this.currentCharacter.clearMessage();
                        this.maxInititative = newInitiative;
                    }
                    break;
                case TALK:
                    boolean techDudeIsThere = false;
                        if (currentCharacter.getCurrentRoom().hasCharacter("TechDude")) {
                            techDudeIsThere = true;
                            Hero tempHero = null;
                            for (Character character : characters) {
                                if (character instanceof Hero) {
                                    tempHero = (Hero)character;
                                }
                            }
                            
                            for (Character character : characters) {
                                if (character.getName().equals("TechDude")) {
                                    TechDude techDude = (TechDude)character;

                                    Boolean isFollowingBefore = techDude.followsHero();
                                    character.performCommand(command);
                                    boolean isFollowingAfter = techDude.followsHero();
                                    tempHero.setPreviousCommand(command.getCommandWord().toString());
                                    
                                    tempHero.setTalking(techDude.wantsToTalk());
                                    
                                    if (!isFollowingAfter && isFollowingBefore) {
                                        Character hero = null;
                                        
                                        techDude.setFollowsHero(hero, false);
                                        LogicFacade.appendMessage("Tech dude no longer follows you.");
                                        
                                    }
                                    else if (isFollowingAfter && !isFollowingBefore) {
                                        Character hero = null;
                                        for (Character character1 : characters) {
                                            if (character1.getName().equals("Hero")) {
                                                hero = character1;
                                                break;
                                            }
                                        }
                                        
                                        LogicFacade.appendMessage("Tech dude is now following you");
                                        techDude.setFollowsHero(hero, true);
                                    }
                                    else {
                                    }
                                    
                                    break;
                                }
                            }    
                        }
                    
                    
                    if (!techDudeIsThere) {
                        LogicFacade.appendMessage("You talk to yourself as you begin to question your sanity.");
                    }
                    
                    if (currentCharacter instanceof Hero) {
                        this.currentCharacter.setCharacterInitiative(this.currentCharacter.getCharacterInitiative() + 1 * this.currentCharacter.getSpeedFactor());
                    }
                    else {
                        this.currentCharacter.setCharacterInitiative(this.currentCharacter.getCharacterInitiative() + 10 * this.currentCharacter.getSpeedFactor());
                    }
                    
                    break;
                case KILL:
                    this.currentCharacter.performCommand(command);
                    wantToQuit = true;
                    this.printStopMessage(this.currentCharacter.getMessage());
                    this.currentCharacter.clearMessage();
                // If command does not match any of the options, break.
                default:
                    break;
            }
        }
        // Return boolean value (false = continue playing; true = quit game)
        return wantToQuit;
    }

    // This method prints a help message, including available commands
    private void printHelp() {
        LogicFacade.appendMessage("Your goal is to survive and reach the escape"
                + " pod. You may find and rescue other crew members. There will"
                + " also be items that can help you on your way.");
        LogicFacade.appendMessage("");
        LogicFacade.appendMessage("The following command buttons are used to "
                + "direct your actions:");
        LogicFacade.appendMessage("");
                LogicFacade.appendMessage(
                    "Pickup: select an item from the dropdown above and press Pickup to pickup the item (use the dropdown above Activate to specify the number of items to pickup).\n"
                    + "Drop: select an item from the dropdown above and press Drop to drop the item (use the dropdown above Activate to specify the number of items to drop).\n"
                    + "Use: select an item from the dropdown above and press Use to use the item.\n"
                    + "Activate: press to activate specific objects.\n"
                    + "Save: press to save the current game.\n"
                    + "Look: press to get information about the room.\n"
                    + "Quit: press to end the game.\n"
                    + "Talk: press to initiate a conversation.\n"
                    + "Stay: press to skip a turn.\n"
                    + "Help: press to read this message.\n"
                    + "Peek: select a direction from the dropdown to the right and press Peek to peek around you and in the specified direction.\n"
                    + "Go: select a direction from the dropdown below and press Go to go in that direction.\n"
                    + "Lock: select a direction from the dropdown to the left and press Lock to lock the door.\n"
                    + "Unlock: select a direction from the dropdown above and press Unlock to unlock the door.\n");
    }

    // This method iterates over the different characters to determine whose turn it is.
    // If two characters have the same initiative, the breaker is who is defined first in the
    // ArrayList
    private Character chooseCharacter() {
        // Set current character to null
        Character currentCharacter = null;
        // Set minInitiative to maximum integer value
        double minInitiative = Integer.MAX_VALUE;
        // Traverse all characters
        for (Character character : characters) {
            // Select character with the lowest initiative
            if (minInitiative > character.getCharacterInitiative()) {
                minInitiative = character.getCharacterInitiative();
                currentCharacter = character;
            }
        }
        // Return the selected character
        return currentCharacter;
    }

    // This method quits the game
    private boolean quit(Command command) {
        // If the "quit" command has a second word, print error message and exit method.
        if (command.hasSecondWord()) {
            LogicFacade.appendMessage("Quit what?");
            return false;
        } // If the command has no second word, return "true", which causes the game to end.
        else {
            printStopMessage("quit");
            return true;
        }
    }

    // This method prints a stop message depending on the reason string
    private void printStopMessage(String reason) {

        boolean techDudeIsThere = false;
        
        for (Character character : characters) {
            if (character.getName().equals("TechDude")) {
                TechDude temp = (TechDude)character;
                if (temp.followsHero()) {
                    techDudeIsThere = true;
                }
            }
        }
                
        // If the player won the game, print message specifying the total points earned
        switch (reason){
            case "win":
                // Calculate earned points
                double point = pointCalculation();
                if (techDudeIsThere) {
                    LogicFacade.appendMessage("Tech dude: Good job, mate! I knew we would make it!");
                }
                LogicFacade.appendMessage("Congratulations, you escaped the space station. You won.");
                System.out.printf("You got %1.2f points\n", point);
                point = ((int)(point*100))/100.0;
                
                LogicFacade.appendMessage("You got " + point +" points\n");

                break;
            case "lose":    // If the player is killed by Zuul, print message
                if (techDudeIsThere) {
                    LogicFacade.appendMessage("Tech dude: AAAARRGHGHRGHRHGRH (Death Gurgle)");
                }
                LogicFacade.appendMessage("The Zuul mauled you in the back while you were running away like a coward. You lost.");
                break;
            case "lose1": // If the player is killed by Zuul, print message
                if (techDudeIsThere) {
                    LogicFacade.appendMessage("Tech dude: AAAARRGHGHRGHRHGRH (Death Gurgle)");
                }
                LogicFacade.appendMessage("The Zuul rips your head off and kicks it across the room, cheering like it just won the world cup.");
                break;
            case "lose2": // If the player is killed by Zuul, print message
                if (techDudeIsThere) {
                    LogicFacade.appendMessage("Tech dude: AAAARRGHGHRGHRHGRH (Death Gurgle)");
                }
                LogicFacade.appendMessage("The Zuul rips out your throat, sticks its claws up your ass, and prances you around like a ventriloquist puppet "
                        + "saying: \"Look at me! I'm a scared little human! I can code!\"");
                break;
            case "lose3": // If the player is killed by Zuul, print message
                if (techDudeIsThere) {
                    LogicFacade.appendMessage("Tech dude: AAAARRGHGHRGHRHGRH (Death Gurgle)");
                }
                LogicFacade.appendMessage("The Zuul rips you in half and then starts teabagging your face that's frozen in horrer by sight of the Zuul's hairy ass.");
                break;
            case "timer": // If player is killed by reactor, print message
                if (techDudeIsThere) {
                    LogicFacade.appendMessage("Tech dude: Good run, mate! See ya on the other side.");
                }
                LogicFacade.appendMessage("The reactor overloaded and blew up the space station. You lost.");
                break;
            case "health": // if the player dies due to low health
                if (techDudeIsThere) {
                    LogicFacade.appendMessage("Tech dude: Don't go into the light!");
                }
                LogicFacade.appendMessage("You died due to extensive wounds.");
                break;
            case "derp":
                LogicFacade.appendMessage("You committed suicide because you are too weak to kill anything else.\nYou pussed out like a bitch.");
                break;
            default: // If player exits the game without losing or winning.
                LogicFacade.appendMessage("You committed suicide.\nYou pussed out like a bitch.");
        }
    }

    // (£) This method calculates the points earned by the player
    private double pointCalculation() {
        // Set hero to the Hero character
        Hero hero = (Hero) (currentCharacter);
        // Declare usb
        USB usb;
        // Create hash set for points
        HashSet<String> pointSet = new HashSet<>();

        // Check for the 3 different USBs
        for (int i = 1; i < 4; i++) {
            // Set name of USB
            String name = "USB" + i;
            // Set usb to the specified USB item in the player's inventory
            usb = (USB) hero.getInventory().getItem(name);
            // If the specified USB is in the player's inventory...
            if (usb != null) {
                // If the specified USB has data stored on it...
                if (usb.getDataType() != null) {
                    // Print the data type collected
                    LogicFacade.appendMessage("You got the " + usb.getDataType() + " data.");
                    // Add value to pointSet
                    pointSet.add(usb.getDataType());
                }
            }
        }

        // Calculate earned points
        double point = (pointSet.size() * 5 + 5) * (1 + ((reactorActivated) ? 2 : 0) + (150 / (hero.getCharacterInitiative() + 150)));
        
        IScore newScore = new Score(this.name, point);
        IHighscore highscoreData = LogicFacade.getHighscore();
        IScore[] scores = highscoreData.getScores();
        Highscore highscore = new Highscore(scores);
        highscore.updateHighscore(newScore);
        LogicFacade.saveHighscore(highscore);
        return point;
    }

    // This method tests if the game is won
    private boolean winTest() {
        // Set finished to false
        boolean finished = false;
        // If the player is in the escape pod, set finished to true and print win message
        for (Character character : characters) {
            if (character.getCurrentRoom().equals(winConditionRoom)) {
                finished = true;
                printStopMessage("win");
                break;
            }
        }
        
        // Return value of finished (true if player has won)
        return finished;
    }

    // This method tests if the player loses because of the reactor
    boolean timerLose() {
        // If player's initiative is greater than maxInitiative, print lose 
        // message based on "timer" and return true.
        
        for (Character character : characters) {
            if (character.getName().equals("Hero")) {
                if (character.getCharacterInitiative() > maxInititative) {
                    printStopMessage("timer");
                    return true;
                } // Else, return false
                else {
                    return false;
                }
            }
        }
        return false;
    }

    // This method checks whether or not the player has died because of low health.
    private boolean healthTest() {
        // Check if current player is hero
        
        for (Character character : characters) {
            if (character.getName().equals("Hero")) {
                // Set tempCharacter to current character
                Hero tempCharacter = (Hero) character;
                // If player's health is less than or equal to zero, print "health"
                // lose message and return true.
                if (tempCharacter.getHealth() <= 0) {
                    printStopMessage("health");
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    Character getCurrentCharacter() {
        return currentCharacter;
    }

    boolean isFinished() {
        return finished;
    }
    
    private Room randomRoom(){       
        Set<String> allRooms = new HashSet<>(this.rooms.keySet());
        allRooms.remove("Pod");
        
        for (Character character : characters) {
            if (character instanceof Zuul) {
                allRooms.remove(character.getCurrentRoom().getName());
            }
        }
        
        int randomRoomKeyIndex = (int)(Math.random()*allRooms.size());       
        
        List<String> allRoomsList = new ArrayList<>(allRooms);
        String randomRoomKey = allRoomsList.get(randomRoomKeyIndex);
        Room randomRoom = rooms.get(randomRoomKey);
        return randomRoom;
    }

    HashMap<String, Room> getRooms() {
        return rooms;
    }
    
    void appendMessage(String appendMessage){
        message += appendMessage + "\n";
    }
    
    String readAndDeleteMessage(){
        String returnMessage = message;
        message = "";
        return returnMessage;
    }
    
    boolean isTalking(){
        boolean talking;
        if (currentCharacter instanceof Hero) {
            talking = ((Hero)currentCharacter).isTalking();
        }
        else {
            talking = false;
        }
        return talking;
    }
    
    ArrayList<String> charactersInRoom(IRoom room){
        Room tempRoom = rooms.get(room.getName());
        ArrayList<String> charactersInRoom = tempRoom.getCharacterInRoom();
        return charactersInRoom;
    }
    
    String getCurrentRoomName(){
        String roomName = this.currentCharacter.getCurrentRoom().getName();
        return roomName;
    }
    
}