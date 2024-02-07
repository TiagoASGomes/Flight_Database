package academy.mindera.aspect;

import academy.mindera.exceptions.flight.FlightException;
import academy.mindera.exceptions.flight.FlightFullException;
import academy.mindera.exceptions.flight.FlightNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Date;

@Provider
public class FlightExceptionHandler implements ExceptionMapper<FlightException> {
    @Override
    public Response toResponse(FlightException e) {
        if(e instanceof FlightNotFoundException){
            Error error = Error.builder()
                    .message(e.getMessage())
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .timestamp(new Date())
                    .build();
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        if(e instanceof FlightFullException){
            Error error = Error.builder()
                    .message(e.getMessage())
                    .status(Response.Status.BAD_REQUEST.getStatusCode())
                    .timestamp(new Date())
                    .build();
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected error in flight controller").build();
    }
}
