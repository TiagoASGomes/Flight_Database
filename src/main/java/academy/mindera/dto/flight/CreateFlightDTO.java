package academy.mindera.dto.flight;

import academy.mindera.dto.price.CreatePriceDto;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;

import static academy.mindera.util.Messages.*;


public record CreateFlightDTO(
        @NotBlank(message = INVALID_ORIGIN)
        @Pattern(regexp = "^[A-Z]{0,6}$", message = INVALID_ORIGIN)
        String origin,
        @NotBlank(message = INVALID_DESTINATION)
        @Pattern(regexp = "^[A-Z]{0,6}$", message = INVALID_DESTINATION)
        String destination,
        @NotNull(message = INVALID_DURATION)
        @Min(value = 0, message = INVALID_DURATION)
        @Max(value = 24, message = INVALID_DURATION)
        Float duration,
        @NotNull(message = INVALID_DATE_OF_FLIGHT)
        @FutureOrPresent(message = INVALID_DATE_OF_FLIGHT)
        @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime dateOfFlight,
        @NotNull(message = INVALID_PLANE_ID)
        @Min(value = 1, message = INVALID_PLANE_ID)
        Long plane,
        @NotNull(message = INVALID_PRICES)
        Set<CreatePriceDto> prices
) {

}
