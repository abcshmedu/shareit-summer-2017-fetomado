package edu.hm.shareit.models;

import java.util.UUID;

public class Token {

    private String token;

    public Token() {
        token = UUID.randomUUID().toString();
    }

    public String getToken() {
        return token;
    }
}
