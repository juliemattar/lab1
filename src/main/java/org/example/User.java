package org.example;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        validateUsername(username);
        validatePassword(password);
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return username;
    }

    private void validateUsername(String username) { //this function checks if the email is valid
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        if (username.length() > 50) {//checking if the email is max 50 chars, if not then ERROR
            throw new IllegalArgumentException("Username is too long, try something shorter");
        }

        char[] arr = username.toCharArray();

        int atIndex = -1;


        for (int i = 0; i < arr.length; i++) { //checking if the first part of the email (before @) is not empty, if it is then ERROR
            if (arr[i] == '@') {
                if (atIndex != -1) {
                    throw new IllegalArgumentException("Please enter a valid Email as username");
                }
                atIndex = i;
            }
        }

        if (atIndex == -1) { //if its complitily empty (there is not even @) then ERROR
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        int dotIndex = -1;

        for (int i = atIndex + 1; i < arr.length; i++) { //checking if the second part of the email (from @ to last '.') is empty
            if (arr[i] == '.') {
                dotIndex = i;
            }
        }

        if (dotIndex == -1) { //if its empty then ERROR
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }


        if (atIndex == 0 || atIndex == arr.length - 1) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        if (dotIndex == atIndex + 1 || dotIndex == arr.length - 1) { //if the second or third part of the email is empty then ERROR
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }


        for (int i = 0; i < atIndex; i++) {//checking if the first part has valid chars
            char c = arr[i];

            if (!(
                    (c >= 'a' && c <= 'z') ||
                            (c >= 'A' && c <= 'Z') ||
                            (c >= '0' && c <= '9') ||
                            c == '.' || c == '_' || c == '-' || c == '+' || c == '%'
            )) {
                throw new IllegalArgumentException("Please enter a valid Email as username");
            }
        }

        char first = arr[atIndex + 1]; //checking if the first char of second part is valid
        if (!(
                (first >= 'a' && first <= 'z') ||
                        (first >= 'A' && first <= 'Z') ||
                        (first >= '0' && first <= '9')
        )) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }


        for (int i = atIndex + 1; i < dotIndex; i++) {//checking if the second part has valid chars
            char c = arr[i];

            if (!(
                    (c >= 'a' && c <= 'z') ||
                            (c >= 'A' && c <= 'Z') ||
                            (c >= '0' && c <= '9') ||
                            c == '.' || c == '-'
            )) {
                throw new IllegalArgumentException("Please enter a valid Email as username");
            }
        }


        int letterCount = 0;

        for (int i = dotIndex + 1; i < arr.length; i++) {//chicking if the third part has valid chars and if it contains two letters at least
            char c = arr[i];

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                letterCount++;
            }
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                throw new IllegalArgumentException("Please enter a valid Email as username");
            }
        }
        if (letterCount < 2) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }
    }
    private void validatePassword(String password) {if (password == null) { //this functions checks if the password is valid
        throw new IllegalArgumentException("Please enter a valid password");
    }

        if (password.length() < 8) {
            throw new IllegalArgumentException("Your password is too short, add more characters");
        }

        if (password.length() > 12) {
            throw new IllegalArgumentException("Your password is too long, try a shorter one");
        }

        char[] arr = password.toCharArray();

        int letterCount = 0;
        int digitCount = 0;
        int symbolCount = 0;

        for (int i = 0; i < arr.length; i++) {//checking if the password contains valid chars
            char c = arr[i];

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                letterCount++;
            } else if (c >= '0' && c <= '9') {
                digitCount++;
            } else if (c == '#' || c == '@' || c == '!' || c == '+' || c == '$' ||c == '&' || c == '*'||
                    c == '(' || c == ')' || c == '%' || c == '^') {
                symbolCount++;
            } else {
                throw new IllegalArgumentException("Please enter a valid password");
            }
        }


        if (letterCount == 0 || digitCount == 0 || symbolCount == 0) {// if it doesn't have at least one letter, one number and one symbol then ERROR
            throw new IllegalArgumentException("Please enter a valid password");
        }
    }

    public String toString() {
        return username + " " + password;
    }


}
