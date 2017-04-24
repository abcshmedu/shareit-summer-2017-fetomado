package edu.hm.shareit.resources;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.services.MediaService;
import edu.hm.shareit.services.MediaServiceResult;
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

public class MediaResourceTest extends JerseyTest {

    @Mock
    private MediaService serviceMock;

    private Book[] books = {
            new Book("Die Känguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3"),
            new Book("what if?", "Randall Munroe", "978-3-8135-0625-5"),
    };
    private Disc[] discs = {
            new Disc("Rennschwein Rudi Rüssel", "123456789", "Peter Timm", 0),
			new Disc("Deadpool", "456789123", "Tim Miller", 16),
			new Disc("Source Code", "101001011", "Duncan Jones", 12),
    };

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().register(new MediaResource(serviceMock));
    }

    @Test
    public void testGetBooks() {
        when(serviceMock.getBooks()).thenReturn(books);
        Response resp = target("media/books").request().get();
        assertEquals(200, resp.getStatus());
        assertEquals("[{\"title\":\"Die Känguru-Chroniken\",\"author\":\"Marc-Uwe Kling\",\"isbn\":\"978-3-548-37623-3\"}," +
                "{\"title\":\"what if?\",\"author\":\"Randall Munroe\",\"isbn\":\"978-3-8135-0625-5\"}]", resp.readEntity(String.class));
    }

    @Test
    public void testGetDiscs() {
        when(serviceMock.getDiscs()).thenReturn(discs);
        Response resp = target("media/discs").request().get();
        assertEquals(200, resp.getStatus());
        assertEquals("[{\"title\":\"Rennschwein Rudi Rüssel\",\"barcode\":\"123456789\",\"director\":\"Peter Timm\",\"fsk\":0}," +
                "{\"title\":\"Deadpool\",\"barcode\":\"456789123\",\"director\":\"Tim Miller\",\"fsk\":16}," +
                "{\"title\":\"Source Code\",\"barcode\":\"101001011\",\"director\":\"Duncan Jones\",\"fsk\":12}]", resp.readEntity(String.class));
    }

    @Test
    public void addBook() {
        when(serviceMock.addBook(any(Book.class))).thenReturn(MediaServiceResult.OK);
        Entity<Book> book = Entity.entity(books[0], MediaType.APPLICATION_JSON);
        Response resp = target("media/books").request().post(book);
        assertEquals(200, resp.getStatus());
        assertEquals("{\"detail\":\"\",\"code\":200}", resp.readEntity(String.class));
    }
}
