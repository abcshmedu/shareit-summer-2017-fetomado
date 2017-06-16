package edu.hm.shareit.models;

import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

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

    /**
     * Hashes and updates the password of the user.
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    /**
     * Hash a string using SHA256
     * @param pwd the string to hash
     * @return the hashed string
     */
    private String hashPassword(String pwd) {
        return DigestUtils.sha256Hex("foobar");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) {
            return false;
        }
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
