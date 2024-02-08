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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

@Path("/api/v1/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class BookingController {

    @Inject
    private BookingService bookingService;

    @Operation(summary = "Get all bookings")
    @APIResponse(responseCode = "200", description = "List of all bookings")
    @GET
    public Response getAll(@QueryParam("page") int page) {
        return Response.ok(bookingService.getAll(page)).build();
    }

    @Operation(summary = "Get booking by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Booking found"),
            @APIResponse(responseCode = "404", description = "Booking not found")
    })
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) throws BookingNotFoundException {
        return Response.ok(bookingService.getById(id)).build();
    }

    @Operation(summary = "Create a booking")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Booking created"),
            @APIResponse(responseCode = "404", description = "Flight not found, Price not found"),
            @APIResponse(responseCode = "400", description = "Flight full")})
    @POST
    public Response create(@Valid @RequestBody List<CreateBookingDTO> booking) throws FlightNotFoundException, PriceNotFoundException, FlightFullException {
        return Response.ok(bookingService.createList(booking)).status(Response.Status.CREATED).build();
    }

    @Operation(summary = "Update a booking")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Booking updated"),
            @APIResponse(responseCode = "404", description = "Flight not found, Booking not found, Price not found")})
    @PUT
    @Path("/{id}")
    public Response update(@Valid @RequestBody CreateBookingDTO booking, @PathParam("id") Long id) throws FlightNotFoundException, BookingNotFoundException, PriceNotFoundException {
        return Response.ok(bookingService.update(booking, id)).build();
    }

    @Operation(summary = "Delete a booking")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Booking deleted"),
            @APIResponse(responseCode = "404", description = "Booking not found")})
    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws BookingNotFoundException {
        bookingService.delete(id);
        return Response.ok().build();
    }
}
