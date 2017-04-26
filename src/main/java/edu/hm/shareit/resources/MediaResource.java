package edu.hm.shareit.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.services.MediaService;
import edu.hm.shareit.services.MediaServiceImpl;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.ServiceResultContainer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    /**
     * Constructs a new instance with a given MediaService implementation.
     * @param srv the MediaService object
     */
    MediaResource(MediaService srv) {
        service = srv;
    }
    
    /**
     * This function handles POST requests to /media/book.
     * @param book the book to create
     * @return response 
     */
    @POST
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        ServiceResult sr = service.addBook(book);
        return Response
                .status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
                .build();
    }
    
    /**
     * This function handles POST requests to /media/disc.
     * @param disc the disc to create
     * @return response 
     */
    @POST
    @Path("/discs")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc) {
        ServiceResult sr = service.addDisc(disc);
        return Response
                .status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
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
    
    @PUT
    @Path("/books/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("isbn") String isbn,Book book){
        return Response.status(Response.Status.OK)
                       .entity(toJson(service.updateBook(isbn,book))) 
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
    
    @PUT
    @Path("/discs/{barcode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDisc(@PathParam("barcode") String barcode, Disc disc){
        return Response.status(Response.Status.OK)
                       .entity(toJson(service.updateDisc(barcode,disc))) 
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
            String value = mapper.writeValueAsString(obj);
            return value;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
