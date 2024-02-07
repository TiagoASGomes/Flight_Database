package academy.mindera.dto.flight;

import academy.mindera.dto.plane.GetPlaneDTO;
import academy.mindera.dto.price.GetPriceDTO;

import java.util.List;

public record GetFlightDto(
        long id,
        String origin,
        String destination,
        int duration,
        String dateOfFlight,
        int availableSeats,
        GetPlaneDTO plane,
        List<GetPriceDTO> price
) {
}
