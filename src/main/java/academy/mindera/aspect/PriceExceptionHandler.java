package academy.mindera.aspect;

import academy.mindera.exceptions.price.PriceException;
import academy.mindera.exceptions.price.PriceInUseException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Date;

@Provider
public class PriceExceptionHandler implements ExceptionMapper<PriceException> {
    @Override
    public Response toResponse(PriceException e) {
        if(e instanceof PriceInUseException){
            Error error = Error.builder()
                    .message(e.getMessage())
                    .status(Response.Status.BAD_REQUEST.getStatusCode())
                    .timestamp(new Date())
                    .build();
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        if(e instanceof PriceNotFoundException){
            Error error = Error.builder()
                    .message(e.getMessage())
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .timestamp(new Date())
                    .build();
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected error in price controller").build();
    }
}
