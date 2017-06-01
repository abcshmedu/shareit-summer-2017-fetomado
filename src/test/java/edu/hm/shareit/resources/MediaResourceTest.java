package edu.hm.shareit.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.User;
import edu.hm.shareit.services.MediaService;
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

public class MediaResourceTest extends JerseyTest {

    @Mock
    private MediaService MediaServiceMock;

    private Book[] books = {
            new Book("Die Kaenguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3"),
            new Book("what if?", "Randall Munroe", "978-3-8135-0625-5"),
    };
    private Disc[] discs = {
            new Disc("Rennschwein Rudi Ruessel", "123456789", "Peter Timm", 0),
            new Disc("Deadpool", "456789123", "Tim Miller", 16),
            new Disc("Source Code", "101001011", "Duncan Jones", 12),
    };

    @Mock
    private UserService UserServiceMock;


    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().register(new MediaResource(MediaServiceMock))
                .register(new CheckTokenRequestFilter(UserServiceMock))
                .register(CustomExceptionMapper.class);
    }

    @Test
    public void testGetBooks() {
        when(MediaServiceMock.getBooks()).thenReturn(new Book[0]);
        Response resp = target("media/books").request().get();
        assertEquals(200, resp.getStatus());
        assertEquals("[]", resp.readEntity(String.class));

        when(MediaServiceMock.getBooks()).thenReturn(books);
        resp = target("media/books").request().get();
        assertEquals(200, resp.getStatus());
        assertEquals("[{\"title\":\"Die Kaenguru-Chroniken\",\"author\":\"Marc-Uwe Kling\",\"isbn\":\"978-3-548-37623-3\"}," +
                "{\"title\":\"what if?\",\"author\":\"Randall Munroe\",\"isbn\":\"978-3-8135-0625-5\"}]", resp.readEntity(String.class));
    }

    @Test
    public void testGetDiscs() {
        when(MediaServiceMock.getDiscs()).thenReturn(new Disc[0]);
        Response resp = target("media/discs").request().get();
        assertEquals(200, resp.getStatus());
        assertEquals("[]", resp.readEntity(String.class));

        when(MediaServiceMock.getDiscs()).thenReturn(discs);
        resp = target("media/discs").request().get();
        assertEquals(200, resp.getStatus());
        assertEquals("[{\"title\":\"Rennschwein Rudi Ruessel\",\"barcode\":\"123456789\",\"director\":\"Peter Timm\",\"fsk\":0}," +
                "{\"title\":\"Deadpool\",\"barcode\":\"456789123\",\"director\":\"Tim Miller\",\"fsk\":16}," +
                "{\"title\":\"Source Code\",\"barcode\":\"101001011\",\"director\":\"Duncan Jones\",\"fsk\":12}]", resp.readEntity(String.class));
    }

    @Test
    public void testCreateBook() {
        when(MediaServiceMock.addBook(any(Book.class))).thenReturn(ServiceResult.OK);
        when(UserServiceMock.checkToken("abc")).thenReturn(new User("TestU", "Test"));
        ObjectNode root = new ObjectMapper().createObjectNode();
        root.put("title", "testtitel");
        root.put("author", "testautor");
        root.put("isbn", "978-3-548-37623-3 ");
        root.put("token", "abc");
        Entity<ObjectNode> json = Entity.entity(root, MediaType.APPLICATION_JSON);
        Response resp = target("media/books").request().post(json);
        assertEquals(200, resp.getStatus());
        assertEquals("{\"code\":200,\"detail\":\"Erfolgreich.\"}", resp.readEntity(String.class));
    }

    @Test
    public void testCreateBookInvalidJSON() {
        when(MediaServiceMock.addBook(any(Book.class))).thenReturn(ServiceResult.OK);
        when(UserServiceMock.checkToken("abc")).thenReturn(new User("TestU", "Test"));
        ObjectNode root = new ObjectMapper().createObjectNode();
        // Names are not correct
        root.put("titel", "testtitel");
        root.put("autor", "testautor");
        root.put("isbn", "978-3-548-37623-3 ");
        root.put("token", "abc");
        Entity<ObjectNode> json = Entity.entity(root, MediaType.APPLICATION_JSON);
        Response resp = target("media/books").request().post(json);
        assertEquals(400, resp.getStatus());
        assertEquals("{\"code\":400,\"detail\":\"Fehlerhafte Eingabe.\"}", resp.readEntity(String.class));
    }

    @Test
    public void testCreateDisc() {
        when(MediaServiceMock.addDisc(any(Disc.class))).thenReturn(ServiceResult.OK);
        when(UserServiceMock.checkToken("abc")).thenReturn(new User("TestU", "Test"));
        ObjectNode root = new ObjectMapper().createObjectNode();
        root.put("title", "testTitel");
        root.put("barcode", "123456789");
        root.put("director", "testDirector");
        root.put("fsk", "12");
        root.put("token", "abc");
        Entity<ObjectNode> json = Entity.entity(root, MediaType.APPLICATION_JSON);
        Response resp = target("media/discs").request().post(json);
        assertEquals(200, resp.getStatus());
        assertEquals("{\"code\":200,\"detail\":\"Erfolgreich.\"}", resp.readEntity(String.class));
    }

    @Test
    public void testGetBook() {
        when(MediaServiceMock.getBook("978-3-8135-0625-5")).thenReturn(books[1]);
        Response resp = target("media/books/978-3-8135-0625-5").request().get();
        assertEquals(200, resp.getStatus());
        assertEquals("{\"title\":\"what if?\",\"author\":\"Randall Munroe\",\"isbn\":\"978-3-8135-0625-5\"}", resp.readEntity(String.class));
    }

    @Test
    public void testGetDisc() {
        when(MediaServiceMock.getDisc("456789123")).thenReturn(discs[1]);
        Response resp = target("media/discs/456789123").request().get();
        assertEquals(200, resp.getStatus());
        assertEquals("{\"title\":\"Deadpool\",\"barcode\":\"456789123\",\"director\":\"Tim Miller\",\"fsk\":16}", resp.readEntity(String.class));
    }

    @Test
    public void testUpdateBook() {
        when(MediaServiceMock.updateBook(eq("978-3-8135-0625-5"), any(Book.class))).thenReturn(ServiceResult.OK);
        when(UserServiceMock.checkToken("abc")).thenReturn(new User("TestU", "Test"));
        ObjectNode root = new ObjectMapper().createObjectNode();
        root.put("title", "testtitel");
        root.put("author", "testautor");
        root.put("isbn", "978-3-548-37623-3 ");
        root.put("token", "abc");
        Entity<ObjectNode> json = Entity.entity(root, MediaType.APPLICATION_JSON);
        Response resp = target("media/books/978-3-8135-0625-5").request().put(json);
        assertEquals(200, resp.getStatus());
        assertEquals("{\"code\":200,\"detail\":\"Erfolgreich.\"}", resp.readEntity(String.class));
    }

    @Test
    public void testUpdateDisc() {
        when(MediaServiceMock.updateDisc(eq("456789123"), any(Disc.class))).thenReturn(ServiceResult.OK);
        when(UserServiceMock.checkToken("abc")).thenReturn(new User("TestU", "Test"));
        ObjectNode root = new ObjectMapper().createObjectNode();
        root.put("title", "testTitel");
        root.put("barcode", "123456789");
        root.put("director", "testDirector");
        root.put("fsk", "12");
        root.put("token", "abc");
        Entity<ObjectNode> json = Entity.entity(root, MediaType.APPLICATION_JSON);
        Response resp = target("media/discs/456789123").request().put(json);
        assertEquals(200, resp.getStatus());
        assertEquals("{\"code\":200,\"detail\":\"Erfolgreich.\"}", resp.readEntity(String.class));
    }
}
