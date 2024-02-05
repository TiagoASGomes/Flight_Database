package academy.mindera.controllers;

import academy.mindera.dto.flight.CreateFlightDTO;
import academy.mindera.exceptions.flight.FlightNotFoundException;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.services.FlightsDetailsServiceI;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/api/v1/flights")
@Produces(MediaType.APPLICATION_JSON)
public class FlightsResource {

    @Inject
    FlightsDetailsServiceI flightsDetailsService;

    @GET
    public Response findAll(@QueryParam("page") int page) {
        return Response.ok(flightsDetailsService.findAllFlights(page)).build();
    }

    @GET
    @Path("/{id}")
    public Response findFlightById(@PathParam("id") Long id) throws FlightNotFoundException {
        return Response.ok(flightsDetailsService.findFlightById(id)).build();
    }

    @POST
    public Response bookSeat(@Valid @RequestBody CreateFlightDTO flight) throws PriceNotFoundException, PlaneNotFoundException {
        return Response.ok(flightsDetailsService.saveFlight(flight)).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateFlight(@PathParam("id") Long id, @Valid @RequestBody CreateFlightDTO flight) throws FlightNotFoundException, PriceNotFoundException, PlaneNotFoundException {
        return Response.ok(flightsDetailsService.updateFlight(flight, id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFlight(@PathParam("id") Long id) throws FlightNotFoundException {
        flightsDetailsService.deleteFlight(id);
        return Response.noContent().build();
    }
}
