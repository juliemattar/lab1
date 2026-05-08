package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class UsersApp {
    public static void main(String[] args) throws Exception {
        ArrayList<User> users = new ArrayList<>();

        File readFile = new File("users.txt");
        Scanner reader = new Scanner(readFile);

        while (reader.hasNextLine()) { //reading the lines in file "users.txt"
            String line = reader.nextLine();

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.trim().split("\\s+"); //splitting the email from the password

            String username = parts[0]; //this index contains the email
            String password = parts[1]; //this index contains the password

            try {
                User user = new User(username, password); //checking if the email and the password are valid (in User), if they are not then ERROR
                users.add(user);
            } catch (IllegalArgumentException e) {
                System.out.println(line + " -> " + e.getMessage());
            }
        }

        reader.close();

        Collections.sort(users, (u1, u2) -> u1.getName().compareTo(u2.getName())); //sorting the valid emails

        for (User user : users) { //printing the valid emails and passwords sorted
            System.out.println(user);
        }
    }
}