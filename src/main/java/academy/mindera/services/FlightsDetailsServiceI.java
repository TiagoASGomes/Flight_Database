package academy.mindera.services;

import academy.mindera.dto.flight.CreateFlightDTO;
import academy.mindera.dto.flight.GetFlightDto;
import academy.mindera.exceptions.flight.FlightFullException;
import academy.mindera.exceptions.flight.FlightNotFoundException;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.models.Flight;

import java.util.List;

public interface FlightsDetailsServiceI {


    CreateFlightDTO saveFlight(CreateFlightDTO flight) throws PlaneNotFoundException, PriceNotFoundException;

    List<GetFlightDto> findAllFlights(int page);

    GetFlightDto findFlightById(Long id) throws FlightNotFoundException;

    void deleteFlight(Long id) throws FlightNotFoundException;

    GetFlightDto updateFlight(CreateFlightDTO flight, Long id) throws FlightNotFoundException, PlaneNotFoundException, PriceNotFoundException;

    Flight findById(Long id) throws FlightNotFoundException;

    void checkIfFullCapacity(Long flightId, int amountToCheckFor) throws FlightNotFoundException, FlightFullException;
}
