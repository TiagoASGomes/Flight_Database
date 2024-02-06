package academy.mindera.dto.flight;

import academy.mindera.dto.price.CreatePriceDto;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;


public record CreateFlightDTO(
        @NotBlank
        String origin,
        @NotBlank
        String destination,
        @NotNull
        int duration,
        @NotNull
        @FutureOrPresent
        @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime dateOfFlight,
        @NotNull
        Long plane,
        Set<CreatePriceDto> prices
) {

}
