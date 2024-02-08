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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/api/v1/flights")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class FlightController {

    @Inject
    FlightService flightService;

    @Operation(summary = "Get all flights")
    @APIResponse(responseCode = "200", description = "List of all flights")
    @GET
    public Response findAll(@QueryParam("page") int page) {
        return Response.ok(flightService.getAll(page)).build();
    }

    @Operation(summary = "Search for flights")
    @APIResponse(responseCode = "200", description = "List of flights found")
    @GET
    @Path("/search/{origin}/{destination}")
    public Response search(@PathParam("origin") String origin, @PathParam("destination") String destination, @QueryParam("date") @DefaultValue("") String date, @QueryParam("price") @DefaultValue("9999") int price, @QueryParam("page") int page) {
        return Response.ok(flightService.search(origin, destination, date, price, page)).build();
    }

    @Operation(summary = "Get flight by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Flight found"),
            @APIResponse(responseCode = "404", description = "Flight not found")
    })
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) throws FlightNotFoundException {
        return Response.ok(flightService.getById(id)).build();
    }

    @Operation(summary = "Create a flight")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Flight created"),
            @APIResponse(responseCode = "404", description = "Price not found, Plane not found")
    })
    @POST
    public Response create(@Valid CreateFlightDTO flight) throws PriceNotFoundException, PlaneNotFoundException {
        return Response.ok(flightService.create(flight)).status(Response.Status.CREATED).build();
    }

    @Operation(summary = "Update a flight")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Flight updated"),
            @APIResponse(responseCode = "404", description = "Flight not found, Price not found, Plane not found")
    })
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CreateFlightDTO flight) throws FlightNotFoundException, PriceNotFoundException, PlaneNotFoundException {
        return Response.ok(flightService.update(flight, id)).build();
    }

    @Operation(summary = "Delete a flight")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Flight deleted"),
            @APIResponse(responseCode = "404", description = "Flight not found")
    })
    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws FlightNotFoundException {
        flightService.delete(id);
        return Response.ok().build();
    }
}
