package edu.hm.shareit.services;

import edu.hm.shareit.models.Token;
import edu.hm.shareit.models.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceImplTest {

    private UserServiceImpl service;

    private User user = new User("testuser", "Test123");

    @Before
    public void before(){
        service = new UserServiceImpl();
    }

    @Test
    public void testCheckValidUser(){
        ServiceResult sr = service.checkUser(user);
        assertEquals(ServiceResult.OK, sr);
    }

    @Test
    public void testCheckInvalidUser(){
        User invalidUser = new User("invalidUser", "qwertz");
        ServiceResult sr = service.checkUser(invalidUser);
        assertEquals(ServiceResult.UNAUTHORIZED, sr);
    }

    @Test
    public void testGetNewToken() {
        Token testToken = service.getNewToken(user);
        assertNotNull(testToken);
        assertNotNull(service.checkToken(testToken.getToken()));
    }

}
