package academy.mindera.services;

import academy.mindera.exceptions.FlightNotFoundException;
import academy.mindera.exceptions.SeatAlreadyBookedException;
import academy.mindera.models.Flights;

import java.util.List;

public interface FlightsDetailsServiceI {

    void bookSeat(Long flightId, String seatNumber) throws SeatAlreadyBookedException, FlightNotFoundException;
    void saveFlight(Flights flight);

    List<Flights> findAllFlights();

    Flights findFlightById(long id) throws FlightNotFoundException;

    void deleteFlight(long id);

    void updateFlight(Flights flight);

    List<String> getAvailableSeats(long flightId) throws FlightNotFoundException;

    int getTotalSeatsForFlight(Flights flight);

    List<String> getBookedSeatsForFlight(long flightId) throws FlightNotFoundException;
}
