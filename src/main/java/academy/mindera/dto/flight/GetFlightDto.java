package academy.mindera.dto.flight;

import academy.mindera.dto.plane.GetPlaneDTO;
import academy.mindera.dto.price.GetPriceDTO;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

public record GetFlightDto(
        @Schema(description = "The id of the flight", example = "1")
        long id,
        @Schema(description = "The origin of the flight", example = "OPO")
        String origin,
        @Schema(description = "The destination of the flight", example = "LIS")
        String destination,
        @Schema(description = "The duration of the flight in hours", example = "2.5")
        Float duration,
        @Schema(description = "The date of the flight", example = "2021-12-31T23:59")
        String dateOfFlight,
        @Schema(description = "The available seats of the flight", example = "100")
        int availableSeats,
        @Schema(description = "The plane of the flight")
        GetPlaneDTO plane,
        @Schema(description = "The prices of the flight")
        List<GetPriceDTO> price
) {
}
