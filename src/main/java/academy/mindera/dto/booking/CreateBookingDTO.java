package academy.mindera.dto.booking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static academy.mindera.util.Messages.*;

public record CreateBookingDTO(
        @NotNull(message = INVALID_NAME)
        @Pattern(regexp = "^[A-Za-z ]+$", message = INVALID_NAME)
        @Schema(description = "The full name of the passenger", example = "John Doe")
        String fName,
        @NotNull(message = INVALID_EMAIL)
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = INVALID_EMAIL)
        @Schema(description = "The email of the passenger", example = "email@example.com")
        String email,
        @NotNull(message = INVALID_PHONE_NUMBER)
        @Pattern(regexp = "^((\\+351|00351|351)?) ?(9[3621])\\d{7}$", message = INVALID_PHONE_NUMBER)
        @Schema(description = "The phone number of the passenger", example = "912345678")
        String phone,
        @NotNull(message = INVALID_FLIGHT_ID)
        @Min(value = 1, message = INVALID_FLIGHT_ID)
        @Schema(description = "The id of the flight", example = "1")
        Long flightId,
        @NotNull(message = INVALID_PRICE_ID)
        @Min(value = 1, message = INVALID_PRICE_ID)
        @Schema(description = "The id of the price", example = "1")
        Long priceId
) {
}
