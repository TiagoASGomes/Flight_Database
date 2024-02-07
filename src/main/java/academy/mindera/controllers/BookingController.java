package academy.mindera.controllers;

import academy.mindera.dto.booking.CreateBookingDTO;
import academy.mindera.exceptions.booking.BookingNotFoundException;
import academy.mindera.exceptions.flight.FlightFullException;
import academy.mindera.exceptions.flight.FlightNotFoundException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.services.interfaces.BookingService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;

@Path("/api/v1/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class BookingController {

    @Inject
    private BookingService bookingService;

    @GET
    public Response getAll(@QueryParam("page") int page) {
        return Response.ok(bookingService.getAll(page)).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) throws BookingNotFoundException {
        return Response.ok(bookingService.getById(id)).build();
    }

    @POST
    public Response create(@Valid @RequestBody List<CreateBookingDTO> booking) throws FlightNotFoundException, PriceNotFoundException, FlightFullException {
        return Response.ok(bookingService.createList(booking)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@Valid @RequestBody CreateBookingDTO booking, @PathParam("id") Long id) throws FlightNotFoundException, BookingNotFoundException, PriceNotFoundException {
        return Response.ok(bookingService.update(booking, id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws BookingNotFoundException {
        bookingService.delete(id);
        return Response.noContent().build();
    }
}
