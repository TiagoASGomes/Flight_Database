package academy.mindera.controllers;

import academy.mindera.exceptions.FlightNotFoundException;
import academy.mindera.exceptions.SeatAlreadyBookedException;
import academy.mindera.models.Flights;
import academy.mindera.services.FlightsDetailsService;
import academy.mindera.services.FlightsDetailsServiceI;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.print.attribute.standard.Media;
import java.util.List;

@Path("/api/flights")
public class FlightsResource {

    @Inject
    FlightsDetailsServiceI flightsDetailsService;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        return Response.ok(flightsDetailsService.findAllFlights()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findFlightById(@PathParam("id") long id) throws FlightNotFoundException {
        Flights flight = flightsDetailsService.findFlightById(id);
        if (flight != null){
            return Response.ok(flight).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}/availableseats")
    @Produces(MediaType.APPLICATION_JSON)
        public Response getAvailableSeats(@PathParam("id") long flightId) throws FlightNotFoundException {
            List<String> availableSeats = flightsDetailsService.getAvailableSeats(flightId);
            return Response.ok(availableSeats).build();
        }

    @POST
    @Path("/{id}/book-seat")
    public Response bookSeat(@PathParam("id") Long flightId, @QueryParam("seatNumber") String seatNumber) {
            return Response.status(Response.Status.CREATED).build();
    }
}
