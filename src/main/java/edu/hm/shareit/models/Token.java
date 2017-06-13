package edu.hm.shareit.models;

import javax.persistence.*;
import java.util.UUID;

/**
 * This class creates a token with a random UUID.
 */
@Entity
public class Token {

    @Id private String token;

    @ManyToOne
    private User user;

    /**
     * Constructs a new token object with a random UUID.
     */
    public Token() {
        token = UUID.randomUUID().toString();
    }

    /**
     * Returns the token.
     * @return the token
     */
    public String getToken() {
        return token;
    }
}
