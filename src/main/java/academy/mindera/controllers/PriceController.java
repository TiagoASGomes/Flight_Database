package academy.mindera.controllers;

import academy.mindera.dto.price.CreatePriceDto;
import academy.mindera.exceptions.price.PriceInUseException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.services.interfaces.PriceService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/api/v1/prices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class PriceController {
    @Inject
    PriceService pricesDetailsService;

    @Operation(summary = "Get all prices")
    @APIResponse(responseCode = "200", description = "List of all prices")
    @GET
    public Response getAll(@QueryParam("page") int page) {
        return Response.ok(pricesDetailsService.getAll(page)).build();
    }

    @Operation(summary = "Create a price")
    @APIResponse(responseCode = "201", description = "Price created")
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(pricesDetailsService.getById(id)).build();
    }

    @Operation(summary = "Create a price")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Price created"),
            @APIResponse(responseCode = "404", description = "Flight not found, Plane not found")
    })
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CreatePriceDto price) throws PriceNotFoundException {
        return Response.ok(pricesDetailsService.update(price, id)).build();
    }

    @Operation(summary = "Create a price")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Price created"),
            @APIResponse(responseCode = "404", description = "Flight not found, Plane not found")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) throws PriceNotFoundException, PriceInUseException {
        pricesDetailsService.delete(id);
        return Response.ok().build();
    }
}
