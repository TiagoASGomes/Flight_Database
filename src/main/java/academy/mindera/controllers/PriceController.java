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

@Path("/api/v1/prices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class PriceController {
    @Inject
    PriceService pricesDetailsService;

    @GET
    public Response getAll(@QueryParam("page") int page) {
        return Response.ok(pricesDetailsService.getAll(page)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(pricesDetailsService.getById(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CreatePriceDto price) throws PriceNotFoundException {
        return Response.ok(pricesDetailsService.update(price, id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) throws PriceNotFoundException, PriceInUseException {
        pricesDetailsService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
