package org.example.lab3;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class LoginController {

    @FXML
    private TextField usernameField;// TextField for the username input from the login screen

    @FXML
    private PasswordField passwordField;// PasswordField for the password input from the login screen

    @FXML
    private Label messageLabel;// Label used to show messages to the user

    private ArrayList<User> users = new ArrayList<>();// ArrayList that stores all users from the users.txt file

    private static int maxAttempts; // n - maximum number of wrong login attempts
    private static int blockTime; // t - blocking time in seconds

    public static void setLoginSettings(int n, int t) { //this function gets n and t from LoginMain
        maxAttempts = n;
        blockTime = t;
    }


    public LoginController() {
        try {

            File readFile = new File("users.txt");// Open the users.txt file
            Scanner reader = new Scanner(readFile);

            while (reader.hasNextLine()) { // Read the file line by line

                String line = reader.nextLine();

                if (line.trim().isEmpty()) { // Skip empty lines
                    continue;}

                String[] parts = line.trim().split("\\s+"); // Split the line into username and password


                if (parts.length != 2) { // If the line does not have exactly 2 parts, skip it
                    continue;}

                String username = parts[0];
                String password = parts[1];

                try {

                    User user = new User(username, password); //create a new User object and add it to the list
                    users.add(user);}

                catch (IllegalArgumentException e) {System.out.println(line + " -> " + e.getMessage());}
            }

            reader.close();


            Collections.sort(users, (u1, u2) -> u1.getName().compareTo(u2.getName())); //sort the users list by username

        } catch (Exception e) {
            System.out.println("File not found.");
        }
    }

    // This method runs when the login button is clicked
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = findUser(username);

        if (user == null) { //if we didn't find the email in the list
            messageLabel.setText("user does not exist.");
            return;
        }

        if (!user.getPassword().equals(password)) { //if the password is wrong, start the failed attempt thread
            FailedAttemptThread thread = new FailedAttemptThread(user);
            thread.setName("Failed Attempt Thread");
            thread.start();
            return;
        }

        // If username and password are correct, check if the user is blocked
        CheckBlockedThread thread = new CheckBlockedThread(user);
        thread.setName("Check Blocked Thread");
        thread.start();
    }

    private User findUser(String username) {
        for (User user : users) {
            if (user.getName().equals(username)) {
                return user;
            }
        }

        return null;
    }

    // First thread:
    // This thread updates the number of wrong attempts.
    // If the user reaches n wrong attempts, it blocks the user for t seconds.
    private class FailedAttemptThread extends Thread {

        private User user;

        public FailedAttemptThread(User user) {
            this.user = user;
        }

        @Override
        public void run() {

            if (user.getisBlocked()) { //if the user is already blocked
                Platform.runLater(() -> {messageLabel.setText("User is blocked. Please wait.");});
                return;
            }

            // Add one failed attempt
            user.addFailedAttempt();

            // If the user reached the maximum number of attempts
            if (user.getFailedAttempts() >= maxAttempts) {
                user.blockUser();

                Platform.runLater(() -> {messageLabel.setText("User is blocked for " + blockTime + " seconds.");});

                try {
                    // The thread sleeps for t seconds
                    Thread.sleep(blockTime * 1000L);

                    // After t seconds, the user can try again
                    user.unblockUser();

                    Platform.runLater(() -> {messageLabel.setText("You can try again now.");});

                } catch (InterruptedException e) {System.out.println("The blocking thread was interrupted.");
                }

            } else {
                Platform.runLater(() -> {messageLabel.setText("Wrong password. Attempt " + user.getFailedAttempts() + " of " + maxAttempts);});
            }
        }
    }

    // Second thread:
    // This thread checks if the user is blocked after entering correct username and password.
    private class CheckBlockedThread extends Thread {
        private User user;

        public CheckBlockedThread(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            if (user.getisBlocked()) {Platform.runLater(() -> {messageLabel.setText("User is blocked. Please wait.");});

            } else {
                user.resetFailedAttempts();

                Platform.runLater(() -> {
                    openWelcomeScreen();
                });
            }
        }
    }

    // This method switches the screen from the login screen to the welcome screen
    private void openWelcomeScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lab3/welcome.fxml"));

            Stage stage = (Stage) usernameField.getScene().getWindow();

            Scene scene = new Scene(root, 320, 240);

            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.show();

        } catch (Exception e) {
            System.out.println("Error: could not open the welcome screen.");
        }
    }
}