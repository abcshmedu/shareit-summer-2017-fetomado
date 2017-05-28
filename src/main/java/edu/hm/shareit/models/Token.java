package edu.hm.shareit.models;

import java.util.UUID;

/**
 * This class creates a token with a random UUID.
 */
public class Token {

    private String token;

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
