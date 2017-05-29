package edu.hm.shareit.resources;

import edu.hm.shareit.models.Token;
import edu.hm.shareit.models.User;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.UserService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class UserResourceTest extends JerseyTest {

    @Mock
    private UserService serviceMock;

    private User testuser = new User("testuser", "Test123");

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().register(new UserResource(serviceMock));
    }

    @Test
    public void testValidLogin() {
        when(serviceMock.checkUser(any(User.class))).thenReturn(ServiceResult.OK);
        Token token = new Token();
        when(serviceMock.getNewToken(any(User.class))).thenReturn(token);
        Entity<User> user = Entity.entity(testuser, MediaType.APPLICATION_JSON);
        Response resp = target("users/login").request().post(user);
        assertEquals(200, resp.getStatus());
        assertEquals("{\"token\":\"" + token.getToken() + "\"}", resp.readEntity(String.class));
    }

    @Test
    public void testInvalidLogin() {
        when(serviceMock.checkUser(any(User.class))).thenReturn(ServiceResult.UNAUTHORIZED);
        Entity<User> user = Entity.entity(testuser, MediaType.APPLICATION_JSON);
        Response resp = target("users/login").request().post(user);
        assertEquals(401, resp.getStatus());
        assertEquals("{\"code\":401,\"detail\":\"Keine Berechtigung.\"}", resp.readEntity(String.class));
    }
}
