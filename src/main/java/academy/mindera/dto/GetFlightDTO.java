package academy.mindera.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class GetFlightDTO {

    private long id;
    private String origin;

    private String destination;
    private LocalTime duration;
    private LocalDate dateOfFlight;
    private long planeId;
}
