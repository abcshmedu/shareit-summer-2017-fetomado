package edu.hm.shareit.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Copy;
import edu.hm.shareit.models.User;
import edu.hm.shareit.services.CopyService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class CopyResourceTest extends JerseyTest {

    @Mock
    private CopyService CopyServiceMock;
    private Book book = new Book("Die Kaenguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3");
    private Copy[] copies = {
            new Copy("Test User", book),
            new Copy("Egon", book),
    };

    @Mock
    private UserService UserServiceMock;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().register(new CopyResource(CopyServiceMock))
                .register(CheckTokenRequestFilter.class);
    }

    @Test
    public void testCreateCopy() {
        when(CopyServiceMock.addCopy(any(String.class), any(String.class))).thenReturn(ServiceResult.OK);
        when(UserServiceMock.checkToken("abc")).thenReturn(new User("TestU", "Test"));
        ObjectNode root = new ObjectMapper().createObjectNode();
        root.put("medium", "978-3-548-37623-3");
        root.put("owner", "Test User");
        root.put("token", "abc");
        Entity<ObjectNode> json = Entity.entity(root, MediaType.APPLICATION_JSON);
        Response resp = target("copies").request().post(json);
        assertEquals(200, resp.getStatus());
        assertEquals("{\"code\":200,\"detail\":\"Erfolgreich.\"}", resp.readEntity(String.class));
    }

    @Test
    public void testGetCopies() {
        int id1 = copies[0].getId();
        int id2 = copies[1].getId();
        when(CopyServiceMock.getCopies()).thenReturn(copies);
        Response resp = target("copies").request().get();
        assertEquals(200, resp.getStatus());
        assertEquals("[{\"medium\":{\"title\":\"Die Kaenguru-Chroniken\",\"author\":\"Marc-Uwe Kling\",\"isbn\":\"978-3-548-37623-3\"},\"owner\":\"Test User\",\"id\":" + id1 + "}," +
                        "{\"medium\":{\"title\":\"Die Kaenguru-Chroniken\",\"author\":\"Marc-Uwe Kling\",\"isbn\":\"978-3-548-37623-3\"},\"owner\":\"Egon\",\"id\":" + id2 + "}]",
                resp.readEntity(String.class));
    }

    @Test
    public void testGetCopy() {
        int id = copies[1].getId();
        when(CopyServiceMock.getCopy(id)).thenReturn(copies[1]);
        Response resp = target("copies/" + id).request().get();
        assertEquals(200, resp.getStatus());
        assertEquals("{\"medium\":{\"title\":\"Die Kaenguru-Chroniken\",\"author\":\"Marc-Uwe Kling\",\"isbn\":\"978-3-548-37623-3\"},\"owner\":\"Egon\",\"id\":" + id + "}",
                resp.readEntity(String.class));
    }

    @Test
    public void testUpdateCopy() {
        int id = copies[1].getId();
        when(CopyServiceMock.updateCopy(eq(id), any(String.class))).thenReturn(ServiceResult.OK);
        when(UserServiceMock.checkToken("testToken")).thenReturn(new User("TestUser", "testPw"));
        ObjectNode root = new ObjectMapper().createObjectNode();
        root.put("owner", "Test User");
        root.put("token", "testToken");
        Entity<ObjectNode> json = Entity.entity(root, MediaType.APPLICATION_JSON);
        Response resp = target("copies/" + id).request().put(json);
        assertEquals(200, resp.getStatus());
        assertEquals("{\"code\":200,\"detail\":\"Erfolgreich.\"}", resp.readEntity(String.class));
    }

}
