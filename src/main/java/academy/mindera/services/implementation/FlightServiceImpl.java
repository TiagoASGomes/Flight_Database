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
    private final int PAGE_SIZE = 10;

    @Inject
    private FlightsRepository flightsDetailsRepository;
    @Inject
    private FlightConverter flightConverter;
    @Inject
    private PriceService priceService;
    @Inject
    private PlaneService planeService;

    @Override
    public List<GetFlightDto> getAll(int page) {
        return flightConverter.fromEntityListToGetDtoList(flightsDetailsRepository.findAll().page(page, PAGE_SIZE).list());
    }

    @Override
    public List<GetFlightDto> search(String origin, String destination, String date, int page, int price) {
        if (date.isEmpty()) {
            date = LocalDateTime.now().toString();
        }
        origin = origin.toUpperCase();
        destination = destination.toUpperCase();
        List<Flight> flights = flightsDetailsRepository.search(origin, destination, date).page(page, PAGE_SIZE).list();
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
        flightsDetailsRepository.persist(flightEntity);
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
        flightsDetailsRepository.persist(dbFlight);
        return flightConverter.fromEntityToGetDto(dbFlight);
    }

    @Override
    public void delete(Long id) throws FlightNotFoundException {
        findById(id);
        flightsDetailsRepository.deleteById(id);
    }

    @Override
    public Flight findById(Long id) throws FlightNotFoundException {
        return flightsDetailsRepository.findByIdOptional(id).orElseThrow(() -> new FlightNotFoundException(FLIGHT_ID_NOT_FOUND + id));
    }

    @Override
    public boolean checkIfFullCapacity(Long flightId) throws FlightNotFoundException {
        Flight flight = findById(flightId);
        if (flight.getBookings().size() >= flight.getPlane().getPeopleCapacity()) {
            flight.setFullCapacity(true);
            flightsDetailsRepository.persist(flight);
        }
        return flight.isFullCapacity();
    }

    @Override
    public void removePassengers(Long flightId) throws FlightNotFoundException {
        Flight flight = findById(flightId);
        flight.setFullCapacity(false);
        flightsDetailsRepository.persist(flight);
    }
}
