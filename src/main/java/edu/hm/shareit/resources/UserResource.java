package edu.hm.shareit.resources;

import com.fasterxml.jackson.databind.JsonNode;
import edu.hm.shareit.models.Token;
import edu.hm.shareit.models.User;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.ServiceResultContainer;
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
    public Response login(User user) {
        ServiceResult sr = service.checkUser(user);
        if (sr == ServiceResult.OK) {
            Token token = service.getNewToken(user);
            return Response.status(sr.getStatus())
                    .entity(token)
                    .build();
        }
        return Response.status(sr.getStatus())
                .entity(new ServiceResultContainer(sr))
                .build();
    }
}
