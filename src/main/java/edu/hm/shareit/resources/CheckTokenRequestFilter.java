package edu.hm.shareit.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.ServiceResultContainer;
import edu.hm.shareit.services.UserService;
import edu.hm.shareit.services.UserServiceImpl;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static edu.hm.shareit.resources.ResourceHelper.toJson;

@CheckToken
public class CheckTokenRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        JsonNode json = new ObjectMapper().readTree(requestContext.getEntityStream());
        String token = json.path("token").asText("");
        UserService user = new UserServiceImpl();
        if(user.checkToken(token) == null) {
            requestContext.abortWith(Response
                    .status(ServiceResult.UNAUTHORIZED.getStatus())
                    .entity(toJson(new ServiceResultContainer(ServiceResult.UNAUTHORIZED)))
                    .build());
        }

    }

}
