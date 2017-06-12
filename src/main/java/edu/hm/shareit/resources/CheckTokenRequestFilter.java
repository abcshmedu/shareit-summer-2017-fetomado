package edu.hm.shareit.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.ServiceResultContainer;
import edu.hm.shareit.services.UserService;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.nio.charset.Charset;

import static edu.hm.shareit.resources.ResourceHelper.toJson;

/**
 * This filter checks tokens sent to the server and rejects invalid ones.
 */
@Provider
@CheckToken
public class CheckTokenRequestFilter implements ContainerRequestFilter {

    @Inject
    private UserService service;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        JsonNode json = new ObjectMapper().readTree(requestContext.getEntityStream());
        String token = json.path("token").asText("");
        if (service.checkToken(token) == null) {
            requestContext.abortWith(Response
                    .status(ServiceResult.UNAUTHORIZED.getStatus())
                    .entity(toJson(new ServiceResultContainer(ServiceResult.UNAUTHORIZED)))
                    .build());
        } else {
            ((ObjectNode) json).remove("token");
            requestContext.setEntityStream(IOUtils.toInputStream(json.toString(), (Charset) null));
        }
    }
}
