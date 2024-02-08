package academy.mindera.dto.flight;

import academy.mindera.dto.price.CreatePriceDto;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;

import static academy.mindera.util.Messages.*;


public record CreateFlightDTO(
        @NotBlank(message = INVALID_ORIGIN)
        @Pattern(regexp = "^[A-Z]{0,6}$", message = INVALID_ORIGIN)
        @Schema(description = "The origin of the flight", example = "OPO")
        String origin,
        @NotBlank(message = INVALID_DESTINATION)
        @Pattern(regexp = "^[A-Z]{0,6}$", message = INVALID_DESTINATION)
        @Schema(description = "The destination of the flight", example = "LIS")
        String destination,
        @NotNull(message = INVALID_DURATION)
        @Min(value = 0, message = INVALID_DURATION)
        @Max(value = 24, message = INVALID_DURATION)
        @Schema(description = "The duration of the flight in hours", example = "2.5")
        Float duration,
        @NotNull(message = INVALID_DATE_OF_FLIGHT)
        @FutureOrPresent(message = INVALID_DATE_OF_FLIGHT)
        @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm")
        @Schema(description = "The date of the flight", example = "2021-12-31T23:59")
        LocalDateTime dateOfFlight,
        @NotNull(message = INVALID_PLANE_ID)
        @Min(value = 1, message = INVALID_PLANE_ID)
        @Schema(description = "The id of the plane", example = "1")
        Long plane,
        @NotNull(message = INVALID_PRICES)
        @Schema(description = "The prices of the flight")
        Set<CreatePriceDto> prices
) {

}
