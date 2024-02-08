package academy.mindera.dto.booking;

import academy.mindera.dto.flight.GetFlightDto;
import academy.mindera.dto.price.GetPriceDTO;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record GetBookingDto(
        @Schema(description = "The id of the booking", example = "1")
        Long id,
        @Schema(description = "The full name of the passenger", example = "John Doe")
        String fName,
        @Schema(description = "The email of the passenger", example = "email@example.com")
        String email,
        @Schema(description = "The phone number of the passenger", example = "912345678")
        String phone,
        @Schema(description = "The seat number of the passenger", example = "1A")
        String seatNumber,
        @Schema(description = "The flight of the booking")
        GetFlightDto flight,
        @Schema(description = "The price of the booking")
        GetPriceDTO price
) {
}
