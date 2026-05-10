package org.example.lab3;

public class User {
    private String username;
    private String password;
    private int failedAttempts; //the number of wrong attempts for this user
    private boolean isblocked; //true if the user is currently blocked

    public User(String username, String password) {

        validateUsername(username);
        validatePassword(password);

        this.username = username;
        this.password = password;
        this.failedAttempts = 0;
        this.isblocked = false;
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public synchronized int getFailedAttempts() { // synchronized because more than one thread can access this value
        return failedAttempts;}

    public synchronized boolean getisBlocked() { // synchronized because more than one thread can check/change blocked
        return isblocked;}


    public synchronized void addFailedAttempt() { //this function adds one failed attempt
        failedAttempts++;}

    // This function blocks the user
    public synchronized void blockUser() {
        isblocked = true;
        failedAttempts = 0;
    }

    // This method unblocks the user after t seconds
    public synchronized void unblockUser() {
        isblocked = false;
        failedAttempts = 0;
    }

    public synchronized void resetFailedAttempts() { //this function resets the attempts after a successful login
        failedAttempts = 0;}

    private void validateUsername(String username) { // this function checks if the email is valid

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        if (username.length() > 50) {
            throw new IllegalArgumentException("Username is too long, try something shorter");
        }

        char[] arr = username.toCharArray();

        int atIndex = -1;

        // Check that there is exactly one @
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '@') {
                if (atIndex != -1) {
                    throw new IllegalArgumentException("Please enter a valid Email as username");
                }
                atIndex = i;
            }
        }

        if (atIndex == -1) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        int dotIndex = -1;

        // Find the last dot after @
        for (int i = atIndex + 1; i < arr.length; i++) {
            if (arr[i] == '.') {
                dotIndex = i;
            }
        }

        if (dotIndex == -1) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        if (atIndex == 0 || atIndex == arr.length - 1) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        if (dotIndex == atIndex + 1 || dotIndex == arr.length - 1) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        // Check valid characters before @
        for (int i = 0; i < atIndex; i++) {
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

        char first = arr[atIndex + 1];

        if (!(
                (first >= 'a' && first <= 'z') ||
                        (first >= 'A' && first <= 'Z') ||
                        (first >= '0' && first <= '9')
        )) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        // Check valid characters between @ and last dot
        for (int i = atIndex + 1; i < dotIndex; i++) {
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

        // Check the part after the last dot
        for (int i = dotIndex + 1; i < arr.length; i++) {
            char c = arr[i];

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                letterCount++;
            } else {
                throw new IllegalArgumentException("Please enter a valid Email as username");
            }
        }

        if (letterCount < 2) {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }
    }

    private void validatePassword(String password) {
        // This function checks if the password is valid

        if (password == null) {
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

        // Check if the password contains valid characters
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                letterCount++;
            } else if (c >= '0' && c <= '9') {
                digitCount++;
            } else if (c == '#' || c == '@' || c == '!' || c == '+' ||
                    c == '$' || c == '&' || c == '*' || c == '(' ||
                    c == ')' || c == '%' || c == '^') {
                symbolCount++;
            } else {
                throw new IllegalArgumentException("Please enter a valid password");
            }
        }

        if (letterCount == 0 || digitCount == 0 || symbolCount == 0) {
            throw new IllegalArgumentException("Please enter a valid password");
        }
    }

    public String toString() {
        return username + " " + password;
    }
}