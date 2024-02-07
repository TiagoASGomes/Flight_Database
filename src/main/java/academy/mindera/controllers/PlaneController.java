package academy.mindera.controllers;

import academy.mindera.dto.plane.CreatePlaneDTO;
import academy.mindera.exceptions.ValidationException;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.services.interfaces.PlaneService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/planes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class PlaneController {
    @Inject
    PlaneService planeService;

    @GET
    public Response getAll(@QueryParam("page") int page) {
        return Response.ok(planeService.getAll(page)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) throws PlaneNotFoundException {
        return Response.ok(planeService.getById(id)).build();
    }

    @POST
    public Response create(@Valid CreatePlaneDTO plane) throws ValidationException {
        return Response.ok(planeService.create(plane)).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CreatePlaneDTO plane) throws PlaneNotFoundException {
        return Response.ok(planeService.update(plane, id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws PlaneNotFoundException {
        planeService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
