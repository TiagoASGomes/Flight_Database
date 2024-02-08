package academy.mindera.controllers;

import academy.mindera.dto.flight.CreateFlightDTO;
import academy.mindera.exceptions.flight.FlightNotFoundException;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.services.interfaces.FlightService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/flights")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class FlightController {

    @Inject
    FlightService flightService;

    @GET
    public Response findAll(@QueryParam("page") int page) {
        return Response.ok(flightService.getAll(page)).build();
    }

    @GET
    @Path("/search/{origin}/{destination}/{date}/{price}")
    public Response search(@PathParam("origin") String origin, @PathParam("destination") String destination, @PathParam("date") @DefaultValue("") String date, @PathParam("price") @DefaultValue("9999") int price, @QueryParam("page") int page) {
        return Response.ok(flightService.search(origin, destination, date, price, page)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) throws FlightNotFoundException {
        return Response.ok(flightService.getById(id)).build();
    }

    @POST
    public Response create(@Valid CreateFlightDTO flight) throws PriceNotFoundException, PlaneNotFoundException {
        return Response.ok(flightService.create(flight)).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CreateFlightDTO flight) throws FlightNotFoundException, PriceNotFoundException, PlaneNotFoundException {
        return Response.ok(flightService.update(flight, id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws FlightNotFoundException {
        flightService.delete(id);
        return Response.noContent().build();
    }
}
