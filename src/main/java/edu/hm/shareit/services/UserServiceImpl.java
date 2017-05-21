package edu.hm.shareit.services;

import edu.hm.shareit.models.Token;
import edu.hm.shareit.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    Map<String, User> users, tokens;

    public UserServiceImpl() {
        users = new HashMap<>();
        users.put("testuser", new User("testuser", "Test123"));
        tokens = new HashMap<>();
    }

    @Override
    public ServiceResult checkUser(User user) {
        return null;
    }

    @Override
    public ServiceResult checkToken(String token) {
        return null;
    }

    @Override
    public Token getNewToken(User user) {
        return null;
    }

}
