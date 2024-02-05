package academy.mindera.dto.flight;

import academy.mindera.dto.plane.GetPlaneDTO;

public record GetFlightDto(
        long id,
        String origin,
        String destination,
        int duration,
        String dateOfFlight,
        GetPlaneDTO plane
) {
}
