package academy.mindera.services;


import academy.mindera.exceptions.FlightNotFoundException;
import academy.mindera.exceptions.SeatAlreadyBookedException;
import academy.mindera.models.Booking;
import academy.mindera.models.Flights;
import academy.mindera.models.Planes;
import academy.mindera.repositories.BookingRepository;
import academy.mindera.repositories.FlightsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class FlightsDetailsService implements FlightsDetailsServiceI {

    @Inject
    FlightsRepository flightsDetailsRepository;

    @Override
    public void bookSeat(Long flightId, String seatNumber) throws SeatAlreadyBookedException, FlightNotFoundException {
        Flights flight = flightsDetailsRepository.findByIdOptional(flightId).orElseThrow(() -> new FlightNotFoundException("Flight with ID " + flightId + "not found."));

        List<String> bookedSeats = getBookedSeatsForFlight(flightId);

        if (bookedSeats.contains(seatNumber)){
            throw new SeatAlreadyBookedException("Seat " + seatNumber + "is already booked on flight" + flightId);
        }
    }

    @Override
    public void saveFlight(Flights flight){
        flightsDetailsRepository.persist(flight);
    }

    @Override
    public List<Flights> findAllFlights(){
        return flightsDetailsRepository.listAll();
    }

    @Override
    public Flights findFlightById(long id) throws FlightNotFoundException {
        return flightsDetailsRepository.findByIdOptional(id).orElseThrow(() -> new FlightNotFoundException("Plane with ID " + id + " not found."));
    }

    @Override
    public void deleteFlight(long id){
        flightsDetailsRepository.deleteById(id);
    }

    @Override
    public void updateFlight(Flights flight) {
        Flights existingFlight = flightsDetailsRepository.findById(flight.getId());
        if (existingFlight != null){
            existingFlight.setOrigin(flight.getOrigin());
            existingFlight.setDestination(flight.getDestination());
            existingFlight.setDuration(flight.getDuration());
            existingFlight.setDateOfFlight(flight.getDateOfFlight());
            existingFlight.setId(flight.getId());

            flightsDetailsRepository.persist(existingFlight);
        }
    }

    @Override
    public List<String> getAvailableSeats(long flightId) throws FlightNotFoundException {
        Flights flight = findFlightById(flightId);

        // logic that determines available seats
        if (flight == null){
            return Collections.emptyList();
        }

        int totalSeats = getTotalSeatsForFlight(flight);
        List<String> bookedSeats = getBookedSeatsForFlight(flightId);

        List<String> availableSeats = new ArrayList<>();

        for (int seatNumber = 1; seatNumber <= totalSeats; seatNumber++){

            String seat = "Seat: " + seatNumber;
            if (!bookedSeats.contains(seat)) {
                availableSeats.add(seat);
            }
        }
        return availableSeats;
    }

    @Override
    public int getTotalSeatsForFlight(Flights flight) {

        if (flight == null || flight.getPlane() == null) {
            return 0;
        }

        Planes plane = flight.getPlane();

        return plane.getPeopleCapacity();
    }

    @Inject
    BookingRepository bookingRepository;
    @Override
    public List<String> getBookedSeatsForFlight(long flightId) throws FlightNotFoundException {

        Flights flight = findFlightById(flightId);
        if (flight == null){
            return Collections.emptyList();
        }

        List<Booking> bookings = bookingRepository.findByFlight(flight);
        return bookings.stream()
                .map(Booking::getSeatNumber)
                .collect(Collectors.toList());


    }


}
