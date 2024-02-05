package academy.mindera.converters;

import academy.mindera.dto.CreateFlightDTO;
import academy.mindera.exceptions.PlaneNotFoundException;
import academy.mindera.models.Flights;
import academy.mindera.models.Planes;
import academy.mindera.repositories.PlanesRepository;

public class FlightConverter {

    public static CreateFlightDTO toDTO(Flights flight){

        CreateFlightDTO dto = new CreateFlightDTO();
        dto.setId((flight.getId()));
        dto.setOrigin(flight.getOrigin());
        dto.setDestination(flight.getDestination());
        dto.setDuration(flight.getDuration());
        dto.setDateOfFlight(flight.getDateOfFlight());
        dto.setPlaneId(flight.getPlane().getId());
        return dto;
    }

    public static Flights fromDTO(CreateFlightDTO dto, PlanesRepository planesRepository) {

        Flights flight = new Flights();
        flight.setOrigin(dto.getOrigin());
        flight.setDestination(dto.getDestination());
        flight.setDuration(dto.getDuration());
        flight.setDateOfFlight(dto.getDateOfFlight());
        Planes plane = planesRepository.findByIdOptional(dto.getPlaneId()).orElseThrow(() -> new PlaneNotFoundException("Plane not found with ID: " + dto.getPlaneId()));
        flight.setPlane(plane);

        return flight;
    }
}
