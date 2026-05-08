package org.example.lab2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class LoginController {

    // TextField for the username input from the login screen
    @FXML
    private TextField usernameField;

    // PasswordField for the password input from the login screen
    @FXML
    private PasswordField passwordField;

    // Label used to show messages to the user, for example wrong username/password
    @FXML
    private Label messageLabel;

    // ArrayList that stores all users from the users.txt file
    private ArrayList<User> users = new ArrayList<>();

    // Constructor - runs when the LoginController object is created
    public LoginController() {
        try {
            // Open the users.txt file
            File readFile = new File("users.txt");
            Scanner reader = new Scanner(readFile);

            // Read the file line by line
            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split the line into username and password
                String[] parts = line.trim().split("\\s+");

                // If the line does not have exactly 2 parts, skip it
                if (parts.length != 2) {
                    continue;
                }

                String username = parts[0];
                String password = parts[1];

                try {
                    // Create a new User object and add it to the list
                    User user = new User(username, password);
                    users.add(user);

                } catch (IllegalArgumentException e) {
                    // If the user data is invalid, print an error message
                    System.out.println(line + " -> " + e.getMessage());
                }
            }

            // Close the file reader
            reader.close();

            // Sort the users list by username
            Collections.sort(users, (u1, u2) -> u1.getName().compareTo(u2.getName()));

        } catch (Exception e) {
            // If the file was not found, print a message
            System.out.println("File not found.");
        }
    }

    // This method runs when the login button is clicked
    @FXML
    private void handleLogin() {
        // Get the username and password that the user typed
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check if the username and password match one of the users in the list
        for (User user : users) {
            if (user.getName().equals(username) &&
                    user.getPassword().equals(password)) {

                // If the login details are correct, open the welcome screen
                openWelcomeScreen();
                return;
            }
        }

        // If no matching user was found, show an error message
        messageLabel.setText("user or password do not match");
    }

    // This method switches the screen from the login screen to the welcome screen
    private void openWelcomeScreen() {
        try {
            // Load the welcome.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lab2/welcome.fxml"));

            // Get the current window
            Stage stage = (Stage) usernameField.getScene().getWindow();

            // Create a new scene with the welcome screen
            Scene scene = new Scene(root, 600, 400);

            // Set the new scene on the same window
            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.show();

        } catch (Exception e) {
            System.out.println("Error: could not open the welcome screen.");
        }
    }
}