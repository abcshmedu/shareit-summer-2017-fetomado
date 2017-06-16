package edu.hm.shareit.services;

import edu.hm.shareit.models.Token;
import edu.hm.shareit.models.User;
import edu.hm.shareit.persistence.Persistence;

import javax.inject.Inject;

/**
 * Our default implementation of a UserService.
 */
public class UserServiceImpl implements UserService {

    private Persistence persist;

    /**
     * Default constructor (generates stub data).
     */
    @Inject
    public UserServiceImpl(Persistence persist) {
        this.persist = persist;
        if (persist.getAll(User.class).size() == 0) {
            persist.add(new User("testuser", "Test123"));
        }
    }

    @Override
    public ServiceResult checkUser(User user) {
        if (persist.exist(User.class, user.getUsername())) {
            if (user.getPassword().equals(persist.get(User.class, user.getUsername()).getPassword())) {
                return ServiceResult.OK;
            }
        }
        return ServiceResult.UNAUTHORIZED;
    }

    @Override
    public User checkToken(String token) {
        if (persist.exist(Token.class, token)) {
            return persist.get(Token.class, token).getUser();
        }
        return null;
    }

    @Override
    public Token getNewToken(User user) {
        Token token = new Token(persist.get(User.class, user.getUsername()));
        persist.add(token);
        return token;
    }

}
