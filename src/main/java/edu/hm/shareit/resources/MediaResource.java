package edu.hm.shareit.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.shareit.services.MediaService;
import edu.hm.shareit.services.MediaServiceImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
     * This function handles GET request to /media/books/ .
     * @return response with a list of all books
     */
    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(service.getBooks());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK)
                .entity(json)
                .build();
    }
    
    /**
     *  This function handles GET request to  /media/discs/{barcode} .
     * @param barcode
     * @return response a disc with the correct barcode 
     */
    @GET
    @Path("/discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisc(@PathParam("barcode") String barcode){
    	 ObjectMapper mapper = new ObjectMapper();
         String json = "";
         try {
             json = mapper.writeValueAsString(service.getDisc(barcode));
         } catch (JsonProcessingException e) {
             e.printStackTrace();
         }
    	return Response.status(Response.Status.OK)
    					.entity(json)
    					.build();
    }
    
    /**
     * This function handles GET request to /media/discs/ .
     * @return response with a list of all discs
     */
    @GET
    @Path("/discs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs() {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(service.getDiscs());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK)
                .entity(json)
                .build();
    }
    
}
