package edu.hm.shareit.models;

/**
 * This is the model class representing an user.
 */
public class User {

    private String username, password;

    /**
     * Private constructor needed for Jackson compatibility
     */
    private User() { };

    /**
     * Constructs a new user object.
     * @param username the username of the user
     * @param pwd the password of the user
     */
    public User(String username, String pwd) {
        this.username = username;
        this.password = pwd;
    }

    /**
     * Returns the username of the user.
     * @return username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }
}
