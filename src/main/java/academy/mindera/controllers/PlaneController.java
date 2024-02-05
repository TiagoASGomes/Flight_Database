package academy.mindera.controllers;

import academy.mindera.dto.plane.CreatePlaneDTO;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.services.interfaces.PlaneService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("api/v1/planes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlaneController {
    @Inject
    PlaneService planesDetailsService;

    @GET
    public Response findAllPlanes(@QueryParam("page") int page) {
        return Response.ok(planesDetailsService.findAllPlanes(page)).build();
    }

    //TODO get all with filter

    @GET
    @Path("/{id}")
    public Response findPlaneById(@PathParam("id") Long id) throws PlaneNotFoundException {
        return Response.ok(planesDetailsService.findPlaneById(id)).build();
    }

    @POST
    public Response addPlane(@Valid @RequestBody CreatePlaneDTO plane) {
        return Response.ok(planesDetailsService.savePlane(plane)).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePlane(@PathParam("id") Long id, @Valid @RequestBody CreatePlaneDTO plane) throws PlaneNotFoundException {
        return Response.ok(planesDetailsService.updatePlane(plane, id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePlane(@PathParam("id") Long id) throws PlaneNotFoundException {
        planesDetailsService.deletePlane(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
