package edu.hm.shareit.services;

import edu.hm.shareit.models.Token;
import edu.hm.shareit.models.User;

public interface UserService {
    ServiceResult checkUser(User user);
    User checkToken(String token);
    Token getNewToken(User user);
}
