package academy.mindera.controllers;

import academy.mindera.dto.price.CreatePriceDto;
import academy.mindera.exceptions.price.PriceInUseException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.services.interfaces.PriceService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/api/v1/prices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PriceResource {
    @Inject
    PriceService pricesDetailsService;

    @GET
    public Response findAllPrices(@QueryParam("page") int page) {
        return Response.ok(pricesDetailsService.findAllPrices(page)).build();
    }

    @GET
    @Path("/{id}")
    public Response findPriceById(@PathParam("id") Long id) {
        return Response.ok(pricesDetailsService.findPriceById(id)).build();
    }

    @POST
    public Response addPrice(@Valid @RequestBody CreatePriceDto price) {
        return Response.ok(pricesDetailsService.savePrice(price)).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePrice(@PathParam("id") Long id, @Valid @RequestBody CreatePriceDto price) throws PriceNotFoundException {
        return Response.ok(pricesDetailsService.updatePrice(price, id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePrice(@PathParam("id") long id) throws PriceNotFoundException, PriceInUseException {
        pricesDetailsService.deletePrice(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
