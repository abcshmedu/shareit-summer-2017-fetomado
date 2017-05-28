package edu.hm.shareit.services;

import edu.hm.shareit.models.Token;
import edu.hm.shareit.models.User;

/**
 * Interface for the service managing users.
 */
public interface UserService {

    /**
     * Check the user.
     * @param user the user which should exist
     * @return ServiceResult of the operation
     */
    ServiceResult checkUser(User user);

    /**
     * Check the token.
     * @param token the valid token from an user
     * @return the user with valid token, null otherwise
     */
    User checkToken(String token);

    /**
     * Creates a new token for the user.
     * @param user request a new valid token
     * @return token with a random UUID
     */
    Token getNewToken(User user);
}
