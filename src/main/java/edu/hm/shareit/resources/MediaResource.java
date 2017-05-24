package edu.hm.shareit.resources;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.services.MediaService;
import edu.hm.shareit.services.MediaServiceImpl;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.ServiceResultContainer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static edu.hm.shareit.resources.ResourceHelper.toJson;

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
     * @return response including HTTP-Statuscode and details
     */
    @POST
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @CheckToken
    public Response createBook(Book book) {
        ServiceResult sr = service.addBook(book);
        return Response.status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
                .build();
    }

    /**
     * This function handles POST requests to /media/disc.
     * @param disc the disc to create
     * @return response including HTTP-Statuscode and details
     */
    @POST
    @Path("/discs")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @CheckToken
    public Response createDisc(Disc disc) {
        ServiceResult sr = service.addDisc(disc);
        return Response.status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
                .build();
    }

    /**
     * This function handles GET requests to /media/books/{isbn}.
     * @param isbn unique identifier of the book
     * @return response the book with the requested isbn
     */
    @GET
    @Path("/books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("isbn") String isbn) {
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
     * This function handles GET requests to /media/discs/{barcode}.
     * @param barcode unique identifier of the disc
     * @return response a disc with the requested barcode
     */
    @GET
    @Path("/discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisc(@PathParam("barcode") String barcode) {
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
     * This function edits the values of an existing book.
     * @param isbn unique identifier of the book to be changed
     * @param book the book to be changed
     * @return response including HTTP-Statuscode and details
     */
    @PUT
    @Path("/books/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @CheckToken
    public Response updateBook(@PathParam("isbn") String isbn, Book book) {
        ServiceResult sr = service.updateBook(isbn, book);
        return Response.status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
                .build();
    }

    /**
     * This function edits the values of an existing disc.
     * @param barcode unique identifier of the disc to be changed
     * @param disc    the disc to be changed
     * @return response including HTTP-Statuscode and details
     */
    @PUT
    @Path("/discs/{barcode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @CheckToken
    public Response updateDisc(@PathParam("barcode") String barcode, Disc disc) {
        ServiceResult sr = service.updateDisc(barcode, disc);
        return Response.status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
                .build();
    }
}
