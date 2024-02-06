package academy.mindera.dto.booking;

import academy.mindera.dto.flight.GetFlightDto;
import academy.mindera.dto.price.GetPriceDTO;

public record GetBookingDto(
        Long id,
        String fName,
        String email,
        String phone,
        String seatNumber,
        GetFlightDto flight,
        GetPriceDTO price
) {
}
