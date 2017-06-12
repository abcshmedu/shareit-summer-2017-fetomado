package edu.hm.shareit.resources;

import com.fasterxml.jackson.databind.JsonNode;
import edu.hm.shareit.services.CopyService;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.ServiceResultContainer;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static edu.hm.shareit.resources.ResourceHelper.toJson;

/**
 * This class is the API-layer for all requests to the resource copies.
 */
@Path("/copies")
public class CopyResource {

    @Inject
    private CopyService service;

    /**
     * This function handles POST requests to /copies.
     * @param json the json object with required arguments
     * @return response including HTTP-Statuscode and details
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @CheckToken
    public Response createCopy(JsonNode json) {
        String medium = json.path("medium").asText();
        String owner = json.path("owner").asText();
        ServiceResult sr = service.addCopy(owner, medium);
        return Response.status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
                .build();
    }

    /**
     * This function handles GET requests to /copies.
     * @return response list of all copies
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCopies() {
        return Response.status(Response.Status.OK)
                .entity(toJson(service.getCopies()))
                .build();
    }

    /**
     * This function handles GET requests to /copies/{id}.
     * @param id unique identifier of the book
     * @return response the copy with the requested id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCopy(@PathParam("id") int id) {
        return Response.status(Response.Status.OK)
                .entity(toJson(service.getCopy(id)))
                .build();
    }

    /**
     * This function edits the values of an existing copy.
     * @param id   unique identifier of the copy to be changed
     * @param json the json object with required arguments
     * @return response including HTTP-Statuscode and details
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @CheckToken
    public Response updateCopy(@PathParam("id") int id, JsonNode json) {
        String owner = json.path("owner").asText();
        ServiceResult sr = service.updateCopy(id, owner);
        return Response.status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
                .build();
    }
}
