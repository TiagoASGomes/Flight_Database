package academy.mindera.services.interfaces;

import academy.mindera.dto.flight.CreateFlightDTO;
import academy.mindera.dto.flight.GetFlightDto;
import academy.mindera.exceptions.flight.FlightNotFoundException;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.models.Flight;

import java.util.List;

public interface FlightService {


    GetFlightDto create(CreateFlightDTO flight) throws PlaneNotFoundException, PriceNotFoundException;

    List<GetFlightDto> getAll(int page);

    GetFlightDto getById(Long id) throws FlightNotFoundException;

    void delete(Long id) throws FlightNotFoundException;

    GetFlightDto update(CreateFlightDTO flight, Long id) throws FlightNotFoundException, PlaneNotFoundException, PriceNotFoundException;

    Flight findById(Long id) throws FlightNotFoundException;

    boolean checkIfFullCapacity(Long flightId) throws FlightNotFoundException;
}
