/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 *
 * @author Simon
 */
public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room pub1, office1;
    private Monster monster;
    private MainCharacter character;
    

        
    public Game() 
    {
        createRooms();
        parser = new Parser();
        monster = new Monster(office1);
        character = new MainCharacter();
    }

    private void createRooms()
    {
        Room outside, theatre, pub, lab, office, bathroom;
      
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        bathroom = new Room("In the pub's bathroom");
                
        bathroom.setExit("east", pub);
        
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);
        pub.setExit("west", bathroom);
        
        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;
        pub1 = pub; 
        office1 = office;
    }

    public void play() 
    {            
        printWelcome();

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == CommandWord.STAY) {
            System.out.println("You're a lazy one aren't you?");
        }
        else if (commandWord == CommandWord.DRINK) {
            if (currentRoom == pub1){
                System.out.println("Beer is good for you");
            }
            else if (character.getInventory()[0]>0){
                System.out.println("That was a good beer");
                character.setInventory(0,-1);
            }
            else {
                System.out.println("Although beer is great, this is not the place");
            }
        }
        else if (commandWord == CommandWord.PICKUP){
            if (currentRoom == pub1){
                System.out.println("Beer is picked up");
                character.setInventory(0,1);
            }
            else {
                System.out.println("There is nothing to pick up");
            }
        }
        monster.turn();
        if (currentRoom == monster.getCurrentRoom() && character.getInventory()[0] == 0){
            System.out.println("Monster is in this room");
            wantToQuit = true;
            System.out.println("You were eaten by the monster");
        }
        else if (currentRoom == monster.getCurrentRoom() && character.getInventory()[0] > 0){
            System.out.println("Monster is in this room");
            System.out.println("You throw a beer at it");
            character.setInventory(0,-1);
            System.out.println("The monster drinks the beer \n Now fly you fools");
        }
        
        
        return wantToQuit;
    }

    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }
}