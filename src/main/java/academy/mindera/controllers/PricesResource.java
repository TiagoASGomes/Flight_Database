package academy.mindera.controllers;

import academy.mindera.models.Prices;
import academy.mindera.services.PricesDetailsServiceI;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/prices")
public class PricesResource {

    @Inject
    PricesDetailsServiceI pricesDetailsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllPrices(){

        List<Prices> prices = pricesDetailsService.findAllPrices();
        return Response.ok(prices).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findPriceById(@PathParam("id") long id){

        Prices price = pricesDetailsService.findPriceById(id);
        if (price != null){
            return Response.ok(price).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPrice(Prices price){
        pricesDetailsService.savePrice(price);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePrice(@PathParam("id") long id, Prices price){
        price.setId(id);
        pricesDetailsService.updatePrice(price);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePrice(@PathParam("id") long id){
        pricesDetailsService.deletePrice(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}
