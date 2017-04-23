package edu.hm.shareit.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.services.MediaService;
import edu.hm.shareit.services.MediaServiceImpl;

/**
 * This class is the API-layer for all requests to the resource media.
 */
@Path("/media")
public class MediaResource {

    private MediaService service;

    /**
     * Constructs a new instance.
     */
    public MediaResource() {
        service = new MediaServiceImpl();
    }
    
    
    @POST
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        service.addBook(book);
        return Response
                .status(Response.Status.OK)
                .entity(book)
                .build();
    }
    
    /**
     * This function handles GET requests to /media/books/{isbn}.
     * @param isbn unique identifier of the book
     * @return response the book with the correct isbn
     */
    @GET
    @Path("/books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("isbn") String isbn){
        return Response.status(Response.Status.OK)
                .entity(toJson(service.getBook(isbn)))
                .build();
    }
    
    /**
     * This function handles GET requests to /media/books/.
     * @return response list of all books
     */
    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        return Response.status(Response.Status.OK)
                .entity(toJson(service.getBooks()))
                .build();
    }
    
    /**
     *  This function handles GET requests to /media/discs/{barcode}.
     * @param barcode
     * @return response a disc with the correct barcode 
     */
    @GET
    @Path("/discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisc(@PathParam("barcode") String barcode){
        return Response.status(Response.Status.OK)
                        .entity(toJson(service.getDisc(barcode)))
                        .build();
    }
    
    /**
     * This function handles GET requests to /media/discs/.
     * @return response with a list of all discs
     */
    @GET
    @Path("/discs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs() {
        return Response.status(Response.Status.OK)
                .entity(toJson(service.getDiscs()))
                .build();
    }
    
    /**
     * This method converts an Object to a string-representation in JSON.
     * @param obj Object to convert
     * @return String representation of the JSON-Object
     */
    private String toJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
