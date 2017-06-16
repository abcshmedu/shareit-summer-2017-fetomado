package edu.hm.shareit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * This class creates a token with a random UUID.
 */
@Entity
@JsonIgnoreProperties({"user"})
public class Token {

    @Id
    private String token;

    @ManyToOne
    private User user;

    /**
     * Constructs a new token object with a random UUID for a given User.
     * @param user the user this token is for
     */
    public Token(User user) {
        token = UUID.randomUUID().toString();
        this.user = user;
    }

    /**
     * Default constructor needed by Hibernate.
     */
    private Token() {
    }

    /**
     * Returns the token.
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns the user with this token.
     * @return the user
     */
    public User getUser() {
        return user;
    }
}
