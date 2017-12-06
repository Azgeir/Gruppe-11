/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The class is located in the GUI package.
package GUI;

// Imports:
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is used to create the GUI. The class extends the superclass
 * Application.
 * 
 * @author Aske Wulf
 */

public class GUI extends Application {
    
    /**
     * This method is used to start the GUI.
     * 
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is used to launch the application.
     * 
     * @param args String array sent from the main method
     */
    public static void main(String[] args) {
        launch(args);
    }
}
