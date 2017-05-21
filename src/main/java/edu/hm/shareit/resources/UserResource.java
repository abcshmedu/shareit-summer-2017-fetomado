package edu.hm.shareit.resources;

import com.fasterxml.jackson.databind.JsonNode;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.UserService;
import edu.hm.shareit.services.UserServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    private UserService service;

    public UserResource() {
        service = new UserServiceImpl();
    }

    UserResource(UserService srv) {
        service = srv;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(JsonNode json) {
        String user = json.path("username").asText();
        String pwd = json.path("password").asText();
        service.getToken(user, pwd);
        return Response.status(200)
                .entity("foo")
                .build();
    }
}
