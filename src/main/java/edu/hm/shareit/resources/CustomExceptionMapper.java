package edu.hm.shareit.resources;

import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.ServiceResultContainer;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static edu.hm.shareit.resources.ResourceHelper.toJson;

/**
 * The CustomExceptionMapper translates Jackson errors into proper responses.
 */
@Provider
public class CustomExceptionMapper implements ExceptionMapper<PropertyBindingException> {

    @Override
    public Response toResponse(PropertyBindingException e) {
        ServiceResult sr = ServiceResult.BAD_REQUEST;
        return Response.status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
                .build();
    }
}
