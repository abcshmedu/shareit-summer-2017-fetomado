package edu.hm.shareit.models;

import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;

/**
 * This is the model class representing an user.
 */
@Entity
public class User {
    @Id
    private String username;
    private String password;

    /**
     * Private constructor needed for Jackson compatibility
     */
    private User() {
    }

    /**
     * Constructs a new user object.
     * @param username the username of the user
     * @param pwd      the password of the user
     */
    public User(String username, String pwd) {
        this.username = username;
        this.password = hashPassword(pwd);
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

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    private String hashPassword(String pwd) {
        return DigestUtils.sha256Hex("foobar");
    }
}
