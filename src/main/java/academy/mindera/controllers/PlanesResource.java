package academy.mindera.controllers;

import academy.mindera.models.Planes;
import academy.mindera.services.PlanesDetailsServiceI;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("api/planes")
public class PlanesResource {

    @Inject
    PlanesDetailsServiceI planesDetailsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllPlanes(){
        List<Planes> planes = planesDetailsService.findAllPlanes();
        return Response.ok(planes).build();
    }

    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findPlaneById(@PathParam("id") long id){

        Planes plane = planesDetailsService.findPlaneById(id);
        if (plane != null){
            return Response.ok(plane).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlane(Planes plane){
        planesDetailsService.savePlane(plane);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlane(@PathParam("id") long id, Planes plane){
        plane.setId(id);
        planesDetailsService.updatePlane(plane);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlane(@PathParam("id") long id){
        planesDetailsService.deletePlane(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}
