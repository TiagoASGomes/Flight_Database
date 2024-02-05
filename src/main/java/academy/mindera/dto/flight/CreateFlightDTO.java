package academy.mindera.dto.flight;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;


public record CreateFlightDTO(
        @NotBlank
        String origin,
        @NotBlank
        String destination,
        @NotNull
        int duration,
        @NotNull
        @FutureOrPresent
        LocalDateTime dateOfFlight,
        @NotNull
        Long plane,
        List<Long> prices
) {

}
