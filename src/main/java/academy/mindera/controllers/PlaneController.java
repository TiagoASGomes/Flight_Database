package academy.mindera.controllers;

import academy.mindera.dto.plane.CreatePlaneDTO;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.services.interfaces.PlaneService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("api/v1/planes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class PlaneController {
    @Inject
    PlaneService planeService;

    @Operation(summary = "Get all planes")
    @APIResponse(responseCode = "200", description = "List of all planes")
    @GET
    public Response getAll(@QueryParam("page") int page) {
        return Response.ok(planeService.getAll(page)).build();
    }

    @Operation(summary = "Get plane by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Plane found"),
            @APIResponse(responseCode = "404", description = "Plane not found")
    })
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) throws PlaneNotFoundException {
        return Response.ok(planeService.getById(id)).build();
    }

    @Operation(summary = "Create a plane")
    @APIResponse(responseCode = "201", description = "Plane created")
    @POST
    public Response create(@Valid CreatePlaneDTO plane) {
        return Response.ok(planeService.create(plane)).status(Response.Status.CREATED).build();
    }

    @Operation(summary = "Update a plane")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Plane updated"),
            @APIResponse(responseCode = "404", description = "Plane not found")
    })
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CreatePlaneDTO plane) throws PlaneNotFoundException {
        return Response.ok(planeService.update(plane, id)).build();
    }

    @Operation(summary = "Delete a plane")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Plane deleted"),
            @APIResponse(responseCode = "404", description = "Plane not found")
    })
    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws PlaneNotFoundException {
        planeService.delete(id);
        return Response.ok().build();
    }

}
