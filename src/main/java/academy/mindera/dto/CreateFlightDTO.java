package academy.mindera.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class CreateFlightDTO {

    private long id;

    @NotBlank
    private String origin;

    @NotBlank
    private String destination;

    @NotNull
    private LocalTime duration;

    @NotNull
    @FutureOrPresent
    private LocalDate dateOfFlight;

    @NotNull
    private long planeId;

}
