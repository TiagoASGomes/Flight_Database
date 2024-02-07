package academy.mindera.aspect;

import academy.mindera.exceptions.plane.PlaneException;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Date;

@Provider
public class PlaneExceptionHandler implements ExceptionMapper<PlaneException> {
    @Override
    public Response toResponse(PlaneException e) {
        if(e instanceof PlaneNotFoundException){
            Error error = Error.builder()
                    .message(e.getMessage())
                    .status(Response.Status.NOT_FOUND.getStatusCode())
                    .timestamp(new Date())
                    .build();
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected error in plane controller").build();
    }
}
