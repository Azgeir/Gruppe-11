package worldofzuul;
// Imports:

import Acquaintance.IGame;
import Acquaintance.IHighscore;
import Acquaintance.IScore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

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
    private HashMap<String, Room> characterStartRooms;
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
        this.characterStartRooms = new HashMap<>();
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
        biologyLaboratory = new Room("in the biology laboratory", "biolab",
                ("You are in the biology laboratory. On the table to your right\n"
                + "there is a row of microscopes, and the shelf above contains a\n"
                + "collection of test tubes with liquids of different colours.\n"
                + "In the corner of the room there is a computer. Maybe you'll be\n"
                + "able to save some of the research.\n"));

        // Initialise the computer room
        computerRoom = new Room("in the computer room", "computer",
                ("You are in the computer room. This is where you had your working\n"
                + "hours. You see several computers, chairs, USBs, and larger storage\n"
                + "units. You also see your access card lying on the desk.\n"));

        // Initialise the storage room
        storage = new Room("in the storage room", "storage",
                ("You are in the storage facility. Here you see several drawers and\n"
                + "shelves, containing everything from dried food to different tools,\n"
                + "medkits and reseach compartments.\n"));

        // Initialise the medical bay
        medicalBay = new Room("in the medical bay", "medbay",
                ("You are in the medical bay. This is where you would get treated and\n"
                + "contained if you fell sick or got minor injuries. There are beds\n"
                + "and several drawers with medkits, pills and syringes.\n"));

        // Initialise the dormitory
        dormitory = new Room("in the dormitory", "dorm",
                ("You are in the dormitory. This is where you used to sleep and\n"
                + "relax with your colleagues. Sadly, this is also where the monster\n"
                + "arrived. You see the corpses of your beloved colleagues scattered\n"
                + "around the room.\n"));

        // Initialise the physics laboratory
        physicsLaboratory = new Room("in the physics laboratory", "physicslab",
                "You are in the physics laboratory. The room is filled with various\n"
                + "equipment. On a table nearby, you see a Helmholtz coil, and on your\n"
                + "right, there is a mass spectrometer. In the corner of the room, you\n"
                + "see a computer. Maybe you'll be able to save some of the research.\n");

        // Initialise the dock
        dock = new Room("in the dock", "dock",
                "You are in the dock. This is where supply ships come and go and also \n"
                + "the only way off the space station via the pod. However, the space station \n"
                + "is currently under quarantine and you don't know how to overwrite\n"
                + "it. Around you, you see tools for repairs, 3D printers and space suits \n");

        // Initialise the control room
        controlRoom = new Room("in the control room", "control",
                ("You are in the control room. This is where information goes to and\n"
                + "from the space station. This is where you find the tech dude. He was \n"
                + "trying to reestablish the connection to earth, but to no avail.\n"
                + "Maybe you could get surveilliance data back with you."));

        // Initialise the reactor
        reactor = new Room("near the reactor", "reactor",
                ("You are in the reactor room. The reactor is a very dense nuclear\n"
                + "reactor. If it were to melt down the space station would be annihilated.\n"
                + "You see some basic tools, some Geiger counters, and a coupple of\n"
                + "space suits.\n"));

        // Initialise the escape pod
        escapePod = new Room("in the escape pod", "pod");

        // Declare hallways between rooms
        Room hallwayStorageComputer, hallwayComputerBiology, hallwayBiologyControl,
                hallwayControlDock, hallwayDockPhysics, hallwayPhysicsDormitory,
                hallwayDormitoryMedical, hallwayMedicalStorage;

        // Initialize the hallways between the outer rooms
        hallwayStorageComputer = new Room("in the hallway between the storage and computer rooms");
        hallwayComputerBiology = new Room("in the hallway between the storage room and the biology laboratory");
        hallwayBiologyControl = new Room("in the hallway between the biology laboratory and the control room");
        hallwayControlDock = new Room("in the hallway between the control room and the dock");
        hallwayDockPhysics = new Room("in the hallway between the dock and the physics laboratory");
        hallwayPhysicsDormitory = new Room("in the hallway between the physics laboratory and the dormitory");
        hallwayDormitoryMedical = new Room("in the hallway between the dormitory and the medical bay");
        hallwayMedicalStorage = new Room("in the hallway between the medical bay and the storage room");

        // Declare hallways connected to the reactor
        Room hallwayReactorBiology, hallwayReactorControl, hallwayReactorDock,
                hallwayReactorPhysics, hallwayReactorDormitory, hallwayReactorMedical,
                hallwayReactorStorage, hallwayReactorComputer;

        // Initialize the hallways connected to the reactor
        hallwayReactorBiology = new Room("in the hallway between the reactor and the biology laboratory");
        hallwayReactorControl = new Room("in the hallway between the reactor and the control room");
        hallwayReactorDock = new Room("in the hallway between the reactor and the dock");
        hallwayReactorPhysics = new Room("in the hallway between the reactor and the physics laboratory");
        hallwayReactorDormitory = new Room("in the hallway between the reactor and the dormitory");
        hallwayReactorMedical = new Room("in the hallway between the reactor and the medical bay");
        hallwayReactorStorage = new Room("in the hallway between the reactor and the storage room");
        hallwayReactorComputer = new Room("in the hallway between the reactor and the computer room");

        // Set possible exits for hallways between rooms
        hallwayStorageComputer.setExit("storage", storage, false);
        hallwayStorageComputer.setExit("computer", computerRoom, false);

        hallwayComputerBiology.setExit("computer", computerRoom, false);
        hallwayComputerBiology.setExit("biolab", biologyLaboratory, false);

        hallwayBiologyControl.setExit("control", controlRoom, true);
        hallwayBiologyControl.setExit("biolab", biologyLaboratory, false);

        hallwayControlDock.setExit("control", controlRoom, true);
        hallwayControlDock.setExit("dock", dock, false);

        hallwayDockPhysics.setExit("physicslab", physicsLaboratory, false);
        hallwayDockPhysics.setExit("dock", dock, false);

        hallwayPhysicsDormitory.setExit("physicslab", physicsLaboratory, false);
        hallwayPhysicsDormitory.setExit("dorm", dormitory, false);

        hallwayDormitoryMedical.setExit("medbay", medicalBay, false);
        hallwayDormitoryMedical.setExit("dorm", dormitory, false);

        hallwayMedicalStorage.setExit("medbay", medicalBay, false);
        hallwayMedicalStorage.setExit("storage", storage, false);

        // Set possible exits for hallways from the reactor
        hallwayReactorBiology.setExit("reactor", reactor, false);
        hallwayReactorBiology.setExit("biolab", biologyLaboratory, false);

        hallwayReactorControl.setExit("reactor", reactor, false);
        hallwayReactorControl.setExit("control", controlRoom, true);

        hallwayReactorDock.setExit("reactor", reactor, false);
        hallwayReactorDock.setExit("dock", dock, false);

        hallwayReactorPhysics.setExit("reactor", reactor, false);
        hallwayReactorPhysics.setExit("physicslab", physicsLaboratory, false);

        hallwayReactorDormitory.setExit("reactor", reactor, false);
        hallwayReactorDormitory.setExit("dorm", dormitory, false);

        hallwayReactorMedical.setExit("reactor", reactor, false);
        hallwayReactorMedical.setExit("medbay", medicalBay, false);

        hallwayReactorStorage.setExit("reactor", reactor, false);
        hallwayReactorStorage.setExit("storage", storage, false);

        hallwayReactorComputer.setExit("reactor", reactor, false);
        hallwayReactorComputer.setExit("computer", computerRoom, false);

        // Set the possible exits for each room
        biologyLaboratory.setExit("computer", hallwayComputerBiology, false);
        biologyLaboratory.setExit("control", hallwayBiologyControl, false);
        biologyLaboratory.setExit("reactor", hallwayReactorBiology, false);

        controlRoom.setExit("biolab", hallwayBiologyControl, true);
        controlRoom.setExit("dock", hallwayControlDock, true);
        controlRoom.setExit("reactor", hallwayReactorControl, true);

        dock.setExit("control", hallwayControlDock, false);
        dock.setExit("physicslab", hallwayDockPhysics, false);
        dock.setExit("reactor", hallwayReactorDock, false);
        dock.setExit("pod", escapePod, true);

        physicsLaboratory.setExit("dock", hallwayDockPhysics, false);
        physicsLaboratory.setExit("dorm", hallwayPhysicsDormitory, false);
        physicsLaboratory.setExit("reactor", hallwayReactorPhysics, false);

        dormitory.setExit("physicslab", hallwayPhysicsDormitory, false);
        dormitory.setExit("medbay", hallwayDormitoryMedical, false);
        dormitory.setExit("reactor", hallwayReactorDormitory, false);

        medicalBay.setExit("dorm", hallwayDormitoryMedical, false);
        medicalBay.setExit("storage", hallwayMedicalStorage, false);
        medicalBay.setExit("reactor", hallwayReactorMedical, false);

        storage.setExit("medbay", hallwayMedicalStorage, false);
        storage.setExit("computer", hallwayStorageComputer, false);
        storage.setExit("reactor", hallwayReactorStorage, false);

        computerRoom.setExit("storage", hallwayStorageComputer, false);
        computerRoom.setExit("biolab", hallwayComputerBiology, false);
        computerRoom.setExit("reactor", hallwayReactorComputer, false);

        escapePod.setExit("dock", dock, false);

        // Set the exits for the reactor room
        reactor.setExit("computer", hallwayReactorComputer, false);
        reactor.setExit("biolab", hallwayReactorBiology, false);
        reactor.setExit("control", hallwayReactorControl, false);
        reactor.setExit("dock", hallwayReactorDock, false);
        reactor.setExit("physicslab", hallwayReactorPhysics, false);
        reactor.setExit("dorm", hallwayReactorDormitory, false);
        reactor.setExit("medbay", hallwayReactorMedical, false);
        reactor.setExit("storage", hallwayReactorStorage, false);

        // Set the current room to "computer" (Possibly moved to character class)
        characterStartRooms.put("Computer", computerRoom);
        characterStartRooms.put("Control", controlRoom);
        characterStartRooms.put("Dorm", dormitory);
        characterStartRooms.put("Biolab", biologyLaboratory);
        characterStartRooms.put("Storage", storage);
        characterStartRooms.put("Medbay", medicalBay);
        characterStartRooms.put("Physicslab", physicsLaboratory);
        characterStartRooms.put("Dock", dock);
        characterStartRooms.put("Control", controlRoom);
        characterStartRooms.put("Reactor", reactor);
        characterStartRooms.put("ReactorBio", hallwayReactorBiology);
        characterStartRooms.put("ReactorControl", hallwayReactorControl);
        characterStartRooms.put("ReactorDock", hallwayReactorDock);
        characterStartRooms.put("ReactorPhysics", hallwayReactorPhysics);
        characterStartRooms.put("ReactorDorm", hallwayReactorDormitory);
        characterStartRooms.put("ReactorMedBay", hallwayReactorMedical);
        characterStartRooms.put("ReactorStorage", hallwayReactorStorage);
        characterStartRooms.put("ReactorComputer", hallwayReactorComputer);
        characterStartRooms.put("StorageComputer",hallwayStorageComputer);
        characterStartRooms.put("ComputerBiolab",hallwayComputerBiology);
        characterStartRooms.put("BiolabControl",hallwayBiologyControl);
        characterStartRooms.put("ControlDock",hallwayControlDock);
        characterStartRooms.put("DockPhysicslab",hallwayDockPhysics);
        characterStartRooms.put("PhysicslabDorm",hallwayPhysicsDormitory);
        characterStartRooms.put("DormMedBay",hallwayDormitoryMedical);
        characterStartRooms.put("MedBayStorage",hallwayMedicalStorage);

        // Set the value of the win condition
        winConditionRoom = escapePod;

        // Set the initial positions of Zuul, hero, and tech dude
        dormitory.setHasCharacter("Zuul", true);
        computerRoom.setHasCharacter("Hero", true);
        controlRoom.setHasCharacter("TechDude", true);

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
            case "biolab":
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
            case "computer":
                room.getInventory().addItem(new USB(3));
                room.getInventory().addItem(new USB(2));
                room.getInventory().addItem(new USB(1));
                room.getInventory().addItem(new Item(150, "computer-monitor"), 10);
                room.getInventory().addItem(new Item(10, "computer-mouse"), 10);
                room.getInventory().addItem(new Item(20, "keyboard", "write code, but it dosen't work, so you smash it into pieces."), 10);
                room.getInventory().addItem(new AccessCard());
                break;
            case "storage":
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
                room.getInventory().addItem(new Item(20, "plasma-saw", "try to cut a hole in the wall to get away from the Zuul. Though, you end "
                        + "up being sucked out into space, because you cannot think for shit!"), 6);
                room.getInventory().addItem(new Item(30, "nailgun", "shoot all over the place like a stormtrooper."), 8);
                room.getInventory().addItem(new AccessCard());
                break;
            case "medbay":
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
            case "dorm":
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
            case "physicslab":
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
            case "dock":
                room.getInventory().addItem(new Item(1500, "crate"), 30);
                room.getInventory().addItem(new Item(500, "fuel-station"), 1);
                room.getInventory().addItem(new Item(400, "barrel"), 40);
                room.getInventory().addItem(new Item(50, "baggage"), 10);
                room.getInventory().addItem(new Item(150, "computer-moniter"), 15);
                room.getInventory().addItem(new Item(100, "spacesuit"), 10);
                room.getInventory().addItem(new Item(200, "corpse"), 2);
                room.getInventory().addItem(new Item(500, "3D-printer", "scan your ass and print it for science."), 1);
                break;
            case "control":
                room.getInventory().addItem(new MedKit(), 10);
                room.getInventory().addItem(new Item(175, "computer"), 5);
                room.getInventory().addItem(new Item(150, "computer-monitor"), 15);
                room.getInventory().addItem(new Item(20, "keyboard", "write code, but it dosen't work, so you smash it into pieces."), 15);
                room.getInventory().addItem(new Item(10, "screwdriver"), 6);
                room.getInventory().addItem(new Item(20, "hammer"), 4);
                room.getInventory().addItem(new Item(1, "paper", "fold a paper plane and make StarWars sounds."), 20);
                break;
            case "reactor":
                room.getInventory().addItem(new Item(150, "computer-monitor"), 10);
                room.getInventory().addItem(new Item(175, "computer"), 4);
                room.getInventory().addItem(new Item(10, "screwdriver"), 8);
                room.getInventory().addItem(new Item(1500, "crate"), 10);
                room.getInventory().addItem(new Item(20, "Geiger-counter"), 2);
                room.getInventory().addItem(new Item(100, "spacesuit"), 2);
                break;
            case "pod":
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
        this.characters.add(new Hero(characterStartRooms.get("Computer"), "Hero"));
        for (int i = 0; i < numberOfZuul; i++) {
            this.characters.add(new Zuul(characterStartRooms.get("Dorm"), "Zuul", 1.15));
        }
        this.characters.add(new TechDude(characterStartRooms.get("Control"), "TechDude", 0.5));
    }
    
    private void timeAddedZuul(){
        if (this.timeSinceSpawn>spawnTime) {
            Room randomRoom = this.randomRoom();
            this.characters.add(new Zuul(randomRoom,"Zuul",1.15,this.currentCharacter.getCharacterInitiative()));
            this.timeSinceSpawn -= spawnTime;
        }
        else {
            this.timeSinceSpawn += currentCharacter.getCharacterInitiative();
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
            // Process command
            finished = processCommand(command);

            if (currentCharacter.getName().equals("Hero")) {
                this.timeAddedZuul();
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
            
            
//            if (currentCharacter.getName().equals("TechDude")) {
//                TechDude temp = (TechDude)currentCharacter;
//                if (temp.isWantToTalk()) {
//                    break;
//                }
//            }
            
            currentCharacter = this.chooseCharacter();
        } while(!currentCharacter.getName().equals("Hero") && !finished);
    }

    // This method prints the welcome message.
    private void printWelcome() {
        // Print welcome message
        message += ("\n");
        message += ("Welcome to Escape Pod!\n");
        message += ("\nYou are a Software engineer in a space station, and "
                + "the emergency alarm has just gone off and the station is under "
                + "quarantine. You must find items and other survivors and escape "
                + "the station through the escape pod before you are caught by what "
                + "is ravaging the station. \n"
                + "\nYou suddenly hear a rumbling voice emanating from everywhere: \n"
                + "\"We are Zuul, devourers of worlds.\"\n");
        message += ("Type '" + CommandWord.HELP + "' for more information about controls and the game.\n");
        message += ("\n");
        // Description of current room of the player, including available exits.
        message += (characterStartRooms.get("Computer").getLongDescription() + "\n");
    }

    // This method processes the command of the player (returns true if player wants to quit)
    private boolean processCommand(Command command) {
        // Assume the player wants to continue playing
        boolean wantToQuit = false;

        // Create instance of CommandWord using the command word of the specified command (from Parser)
        CommandWord commandWord = command.getCommandWord();

        // Check if the input equals any of the defined commands and print an "error" if it does not
        if (commandWord == CommandWord.UNKNOWN) {
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
                            for (Character character : characters) {
                                if (character.getName().equals("TechDude")) {
                                    TechDude temp = (TechDude)character;

                                    Boolean isFollowingBefore = temp.isFollowsHero();
                                    character.performCommand(command);
                                    boolean isFollowingAfter = temp.isFollowsHero();
                                    boolean conversationIsOver = temp.isWantToTalk();
                                    
                                    if (!isFollowingAfter && isFollowingBefore) {
                                        Character hero = null;
                                        
                                        temp.followsHero(hero, false);
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
                                        temp.followsHero(hero, true);
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
        LogicFacade.appendMessage("You can find other crew members and save them.\n"
                + "You must survive and reach the escape pod. Find items that can\n"
                + "help you on your way.");
        LogicFacade.appendMessage("");
        LogicFacade.appendMessage("The game works by using the command buttons to direct \n"
                + "what you wish to do in the game. you have 3 dropdown menus \n"
                + "the first to the right is the room objects scond is player inventory \n"
                + "the left most one is direction dropdown you use this in \n"
                + "conjunction with the buttons around it.");
        LogicFacade.appendMessage("");
               LogicFacade.appendMessage("functions for the buttons are:\n"
                + "drop: choose an item from the inventory dropdown and press drop \n"
                + "You drop the following item.\n"
                + "pickup: choose an item from the room dropdown and press pickup \n"
                + "You pickup the following item if you can carry it.\n"
                + "unlock: choose direction from the direction dropdown then press unlock \n"
                + "You unlock the specified door.\n"
                + "lock: choose direction from the direction dropdown then press lock \n"
                + "You lock the specified door.\n"
                + "go: choose direction from the direction dropdown then press go. \n"
                + "You go in the specified direction.\n"
                + "peek: choose direction from the direction dropdown then press peek \n"
                + "Checks, the neighboring rooms and perhaps two rooms in the specified direction, for enemies.\n"
                + "use: select an item from the character dropdown menu. \n"
                + "then press the use button.\n"
                + "look: gives information about the character and the room.\n"
                + "activate: is used for activating specefik objects. \n"
                + "quit: terminates the game session. \n"
                + "stay: you skip a turn \n"
                + "help: prints the message you are reading. \n"
                + "talk: you initiate a conversation with a character \n");
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
                if (temp.isFollowsHero()) {
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
        highscore.updataHighscore(newScore);
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

    private void conversation(Character character) {
        
        TechDude temp = (TechDude) character;
        Character hero = null;
        for (Character character1 : characters) {
            if (character1.getName().equals("Hero")) {
                hero = character1;
                break;
            }
        }
        
        Conversation Talk = new Conversation();
        boolean wantToTalk = false;
        int counter = 1;
        if (character.getHostility() != 3) {
            do {
                java.util.Scanner input = new java.util.Scanner(System.in);
                int number = 0;
                String name = character.getName() + counter;
                Talk.talk(name);
                Talk.options(name);

                if (input.hasNextInt()) {
                    number = input.nextInt();
                    if (number != 0) {
                        switch (number) {
                            case 1:
                                if (counter < 4) {
                                    wantToTalk = true;
                                } else {
                                    wantToTalk = false;
                                }
                                counter++;
                                break;
                            case 2:
                                character.setHostility(character.getHostility() + 1);
                                if (character.getHostility() < 3) {
                                    LogicFacade.appendMessage("The tech dude got annoyed at you.");
                                    wantToTalk = false;
                                }
                                if (character.getHostility() == 3) {
                                    LogicFacade.appendMessage("The tech dude hates you and will no longer talk to you.");
                                    if (temp.isFollowsHero()) {
                                        temp.followsHero(hero, false);
                                        LogicFacade.appendMessage("Tech dude no longer follows you.");
                                        counter++;
                                    }
                                }
                                break;
                            case 3:
                                if (counter == 4) {
                                    wantToTalk = false;
                                    counter++;
                                } else {
                                    LogicFacade.appendMessage("You only have 2 options");
                                }
                                break;
                            default:
                                LogicFacade.appendMessage("Wrong input");
                                wantToTalk = false;
                                break;
                        }
                        if (number != 2) {
                            if (counter == 5 || (counter == 4 && character.getHostility() < 3 && wantToTalk == false)) {
                                if (!temp.isFollowsHero()) {
                                    LogicFacade.appendMessage("Tech dude is now following you");
                                    temp.followsHero(hero, true);
                                }
                            }
                        }
                    }
                } else {
                    LogicFacade.appendMessage("The input wasn't a number");
                    wantToTalk = false;
                }

            } while (wantToTalk);
        } else {
            LogicFacade.appendMessage("Fuck you. I hate you");
        }
    }

    public Character getCurrentCharacter() {
        return currentCharacter;
    }

    public boolean isFinished() {
        return finished;
    }
    
    private Room randomRoom(){
        ArrayList<Entry<String,Room>> allRooms = new ArrayList<>(this.characterStartRooms.entrySet());
        int randomRoomKeyIndex = (int)(Math.random()*allRooms.size());
        Room randomRoom = allRooms.get(randomRoomKeyIndex).getValue();
        
        return randomRoom;
    }

    HashMap<String, Room> getCharacterStartRooms() {
        return characterStartRooms;
    }
    
    void appendMessage(String appendMessage){
        message += appendMessage + "\n";
    }
    
    String readAndDeleteMessage(){
        String returnMessage = message;
        message = "";
        return returnMessage;
    }
    
    
}
