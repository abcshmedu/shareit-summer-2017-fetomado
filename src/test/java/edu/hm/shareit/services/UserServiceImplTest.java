package edu.hm.shareit.services;

import edu.hm.shareit.GuiceInjectionTestFeature;
import edu.hm.shareit.models.Token;
import edu.hm.shareit.models.User;
import edu.hm.shareit.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private UserService service;
    private Persistence persistenceMock;
    private User user = new User("testuser", "Test123");

    @Before
    public void before() {
        service = GuiceInjectionTestFeature.getInjectorInstance().getInstance(UserServiceImpl.class);
        persistenceMock = GuiceInjectionTestFeature.getInjectorInstance().getInstance(Persistence.class);
    }

    @Test
    public void testCheckValidUser() {
        when(persistenceMock.exist(User.class, user.getUsername())).thenReturn(true);
        when(persistenceMock.get(User.class, user.getUsername())).thenReturn(user);
        ServiceResult sr = service.checkUser(user);
        assertEquals(ServiceResult.OK, sr);
    }

    @Test
    public void testCheckInvalidUser() {
        User invalidUser = new User("invalidUser", "qwertz");
        when(persistenceMock.exist(User.class, invalidUser.getUsername())).thenReturn(false);
        ServiceResult sr = service.checkUser(invalidUser);
        assertEquals(ServiceResult.UNAUTHORIZED, sr);
    }

    @Test
    public void testGetNewToken() {
        when(persistenceMock.get(User.class, user.getUsername())).thenReturn(user);
        Token testToken = service.getNewToken(user);
        assertNotNull(testToken);
    }

    @Test
    public void testValidToken() {
        Token token = new Token(user);
        when(persistenceMock.exist(Token.class, token.getToken())).thenReturn(true);
        when(persistenceMock.get(Token.class, token.getToken())).thenReturn(token);
        assertEquals(user, service.checkToken(token.getToken()));
    }

    @Test
    public void testInvalidToken() {
        Token token = new Token(user);
        when(persistenceMock.exist(Token.class, token.getToken())).thenReturn(false);
        assertNull(service.checkToken(token.getToken()));
    }

}
