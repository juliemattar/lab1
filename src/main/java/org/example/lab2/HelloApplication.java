package org.example.lab2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Load the login.fxml file from the same package
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));

        // Create a new scene from the FXML file
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // Set the title of the window
        stage.setTitle("Users Login");

        // Put the scene inside the window
        stage.setScene(scene);

        // Show the window on the screen
        stage.show();
    }

    // The main method starts the program
    public static void main(String[] args) {
        launch(args);
    }
}
