package edu.hm.shareit.models;

public class User {

    private String username, password;

    public User(String username, String pwd) {
        this.username = username;
        this.password = pwd;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private User() {};
}
