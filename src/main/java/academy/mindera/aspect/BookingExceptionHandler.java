package academy.mindera.aspect;

import academy.mindera.exceptions.booking.BookingException;
import academy.mindera.exceptions.booking.BookingNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Date;

@Provider
public class BookingExceptionHandler implements ExceptionMapper<BookingException> {
    @Override
    public Response toResponse(BookingException e) {
        if(e instanceof BookingNotFoundException){
            Error error = Error.builder()
                    .message(e.getMessage())
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .timestamp(new Date())
                    .build();
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected error in booking controller").build();
    }
}
