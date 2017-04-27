package edu.hm.shareit.resources;

import edu.hm.shareit.models.Copy;
import edu.hm.shareit.services.CopyService;
import edu.hm.shareit.services.CopyServiceImpl;
import edu.hm.shareit.services.ServiceResult;
import edu.hm.shareit.services.ServiceResultContainer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static edu.hm.shareit.resources.ResourceHelper.toJson;


@Path("/copys")
public class CopyResource {
    private CopyService service;

    public CopyResource() {
        service = new CopyServiceImpl();
    }

    CopyResource(CopyService srv) {
        service = srv;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCopy (Copy copy){
        System.out.println(copy);
        ServiceResult sr = service.addCopy(copy);
        return Response
                .status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCopys(){
        return Response.status(Response.Status.OK)
                .entity(toJson(service.getCopys()))
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCopy(@PathParam("id") int id){
        return Response
                .status(Response.Status.OK)
                .entity(toJson(service.getCopy(id)))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCopy(@PathParam("id") int id , Copy copy){
        ServiceResult sr = service.updateCopy(id, copy);
        return Response.status(sr.getStatus())
                .entity(toJson(new ServiceResultContainer(sr)))
                .build();
    }
}
