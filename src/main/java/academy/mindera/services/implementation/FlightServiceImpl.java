package academy.mindera.services.implementation;


import academy.mindera.converters.FlightConverter;
import academy.mindera.dto.flight.CreateFlightDTO;
import academy.mindera.dto.flight.GetFlightDto;
import academy.mindera.exceptions.flight.FlightNotFoundException;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.models.Flight;
import academy.mindera.models.Plane;
import academy.mindera.models.Price;
import academy.mindera.repositories.FlightsRepository;
import academy.mindera.services.interfaces.FlightService;
import academy.mindera.services.interfaces.PlaneService;
import academy.mindera.services.interfaces.PriceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static academy.mindera.util.Messages.FLIGHT_ID_NOT_FOUND;

@ApplicationScoped
@Transactional
public class FlightServiceImpl implements FlightService {
    private static final int PAGE_SIZE = 10;

    @Inject
    private FlightsRepository flightRepository;
    @Inject
    private FlightConverter flightConverter;
    @Inject
    private PriceService priceService;
    @Inject
    private PlaneService planeService;

    @Override
    public List<GetFlightDto> getAll(int page) {
        return flightConverter.fromEntityListToGetDtoList(flightRepository.findAll().page(page, PAGE_SIZE).list());
    }

    @Override
    public List<GetFlightDto> search(String origin, String destination, String date, int price, int page) {
        LocalDateTime searchDate;
        if (date.isEmpty()) {
            searchDate = LocalDateTime.now();
        } else {
            searchDate = LocalDateTime.parse(date);
        }
        origin = origin.toUpperCase();
        destination = destination.toUpperCase();
        List<Flight> flights = flightRepository.search(origin, destination, searchDate, page);
        if (price < 9999) {
            flights.removeIf(flight -> flight.getPrices().stream().noneMatch(p -> p.getPrice() <= price));
        }
        return flightConverter.fromEntityListToGetDtoList(flights);
    }

    @Override
    public GetFlightDto getById(Long id) throws FlightNotFoundException {
        return flightConverter.fromEntityToGetDto(findById(id));
    }

    @Override
    public GetFlightDto create(CreateFlightDTO flight) throws PlaneNotFoundException, PriceNotFoundException {
        Plane plane = planeService.findById(flight.plane());
        Set<Price> prices = priceService.create(flight.prices());
        Flight flightEntity = flightConverter.fromCreateDtoToEntity(flight, plane, prices);
        flightEntity.setAvailableSeats(plane.getPeopleCapacity());
        flightRepository.persist(flightEntity);
        return flightConverter.fromEntityToGetDto(flightEntity);
    }

    @Override
    public GetFlightDto update(CreateFlightDTO flight, Long id) throws FlightNotFoundException, PlaneNotFoundException, PriceNotFoundException {
        Flight dbFlight = findById(id);
        dbFlight.setOrigin(flight.origin());
        dbFlight.setDestination(flight.destination());
        dbFlight.setDuration(flight.duration());
        dbFlight.setDateOfFlight(flight.dateOfFlight());
        dbFlight.setPlane(planeService.findById(flight.plane()));
        Set<Price> prices = priceService.create(flight.prices());
        dbFlight.setPrices(prices);
        flightRepository.persist(dbFlight);
        return flightConverter.fromEntityToGetDto(dbFlight);
    }

    @Override
    @Transactional
    public void delete(Long id) throws FlightNotFoundException {
        findById(id);
        flightRepository.deleteById(id);
    }

    @Override
    public Flight findById(Long id) throws FlightNotFoundException {
        return flightRepository.findByIdOptional(id).orElseThrow(() -> new FlightNotFoundException(FLIGHT_ID_NOT_FOUND + id));
    }

    @Override
    public boolean checkIfFullCapacity(Long flightId, long occupiedSeats) throws FlightNotFoundException {
        Flight flight = findById(flightId);
        if (occupiedSeats >= flight.getPlane().getPeopleCapacity()) {
            flight.setFullCapacity(true);
            flightRepository.persist(flight);
        }
        return flight.isFullCapacity();
    }

    @Override
    public void updateAvailableSeats(Long flightId, long occupiedSeats) throws FlightNotFoundException {
        Flight flight = findById(flightId);
        flight.setAvailableSeats((int) (flight.getPlane().getPeopleCapacity() - occupiedSeats));
        if (occupiedSeats < flight.getPlane().getPeopleCapacity()) {
            flight.setFullCapacity(false);
        }
        flightRepository.persist(flight);
    }
}
