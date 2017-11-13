/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 *
 * @author Aske Wulf
 */
public class Conversation {
    //Techdude conversation
    final String TECHTALK1 = "Is the Zuul gone?";
    final String TECHTALK2 = "The arrival of Zuul has triggered the station's quarantine mechanism,\n"
            + "causing the doors to all the escape pods to be locked.";
    final String TECHTALK3 = "Yes, I can override the mechanism and unlock one of the pods,\n"
            + "but I will have to be in the dock to do this.";
    //Replace (time) with the number of turns after tuning
    final String TECHTALK4 = "I can overload the reactor, causing it to self-destruct along with the whole space station.\n"
            + "This will give you a short time to get off the station before it blows.\n"
            + "But I must be near the reactor to overload it.";
    
    //Player responses
    final String TECHANSWER1 = "For now. What has happened to the station?";
    final String TECHANSWER2 = "Can you help me get off this station?";
    final String TECHANSWER3 = "Anything else you can help with?";
    final String LETSGO = "Let's go!";
    final String FUCKOFF = "Fuck off.";
    
    public void options(String string){
        if (string == null){
            System.out.println("String is null");
        }
        else switch (string) {
            case "TechDude1":
                System.out.println("1: " + TECHANSWER1);
                System.out.println("2: " + FUCKOFF);
                break;
            case "TechDude2":
                System.out.println("1: " + TECHANSWER2);
                System.out.println("2: " + FUCKOFF);
                break;
            case "TechDude3":
                System.out.println("1: " + TECHANSWER3);
                System.out.println("2: " + FUCKOFF);
                System.out.println("3: " + LETSGO);
                break;
            case "TechDude4":
                System.out.println("1: " + LETSGO);
                System.out.println("2: " + FUCKOFF);
                break;
            default:
                System.out.println("I don't know what you mean.");
                break;
        }
    }
    public void talk(String string){
        if (string == null){
            System.out.println("String is null");
        }
        else switch (string){
            case "TechDude1":
                System.out.println(TECHTALK1);
                break;
            case "TechDude2":
                System.out.println(TECHTALK2);
                break;
            case "TechDude3":
                System.out.println(TECHTALK3);
                break;
            case "TechDude4":
                System.out.println(TECHTALK4);
                break;
            default:
                System.out.println("I don't know what you want.");
                break;
        }
    }

    }
