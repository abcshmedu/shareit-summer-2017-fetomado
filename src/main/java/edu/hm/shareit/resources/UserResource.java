package edu.hm.shareit.resources;

import edu.hm.shareit.models.Token;
import edu.hm.shareit.models.User;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.ServiceResultContainer;
import edu.hm.shareit.services.UserService;
import edu.hm.shareit.services.UserServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class is the API-layer for all requests to the resource users.
 */
@Path("/users")
public class UserResource {

    private UserService service;

    /**
     * Constructs a new instance with a given UserService implementation.
     * @param srv the UserService object
     */
    @Inject
    public UserResource(UserService srv) {
        service = srv;
    }

    /**
     * This function handles POST requests to /users/login.
     * @param user the user to login
     * @return response including HTTP-Statuscode, details and token
     */
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
