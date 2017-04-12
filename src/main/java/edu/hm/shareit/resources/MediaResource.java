package edu.hm.shareit.resources;

import edu.hm.shareit.services.MediaService;
import edu.hm.shareit.services.MediaServiceImpl;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/media")
public class MediaResource {

    private MediaService service;

    public MediaResource() {
        service = new MediaServiceImpl();
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        JSONObject json = new JSONObject();
        json.put("data", service.getBooks());
        return Response.status(Response.Status.OK)
                .entity(json.toString())
                .build();
    }

}
