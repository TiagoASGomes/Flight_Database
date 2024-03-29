package academy.mindera.services.implementation;

import academy.mindera.converters.BookingConverter;
import academy.mindera.dto.booking.CreateBookingDTO;
import academy.mindera.dto.booking.GetBookingDto;
import academy.mindera.exceptions.booking.BookingNotFoundException;
import academy.mindera.exceptions.flight.FlightFullException;
import academy.mindera.exceptions.flight.FlightNotFoundException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.models.Booking;
import academy.mindera.models.Flight;
import academy.mindera.models.Price;
import academy.mindera.repositories.BookingRepository;
import academy.mindera.services.interfaces.BookingService;
import academy.mindera.services.interfaces.FlightService;
import academy.mindera.services.interfaces.PriceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static academy.mindera.util.Messages.BOOKING_ID_NOT_FOUND;
import static academy.mindera.util.Messages.FULL_FLIGHT;

@ApplicationScoped
public class BookingServiceImpl implements BookingService {
    private static final int PAGE_SIZE = 10;
    @Inject
    private BookingRepository bookingRepository;
    @Inject
    private BookingConverter bookingConverter;
    @Inject
    private FlightService flightService;
    @Inject
    private PriceService priceService;


    @Override
    public List<GetBookingDto> getAll(int page) {
        return bookingConverter.fromEntityListToGetDtoList(bookingRepository.findAll().page(page, PAGE_SIZE).list());
    }

    @Override
    public GetBookingDto getById(Long id) throws BookingNotFoundException {
        return bookingConverter.fromEntityToGetDto(findById(id));
    }

    @Override
    public List<GetBookingDto> createList(List<CreateBookingDTO> booking) throws FlightNotFoundException, PriceNotFoundException, FlightFullException {
        List<GetBookingDto> bookingList = new ArrayList<>();
        for (CreateBookingDTO bookingDTO : booking) {
            if (flightService.checkIfFullCapacity(bookingDTO.flightId(), getOccupiedSeats(flightService.findById(bookingDTO.flightId())))) {
                bookingList.forEach(bookingEntity -> bookingRepository.deleteById(bookingEntity.id()));
                updateAllFlights(bookingList);
                throw new FlightFullException(FULL_FLIGHT);
            }
            bookingList.add(create(bookingDTO));
        }
        return bookingList;
    }

    @Override
    public GetBookingDto create(CreateBookingDTO booking) throws FlightNotFoundException, PriceNotFoundException, FlightFullException {
        Price price = priceService.findById(booking.priceId());
        Flight flight = flightService.findById(booking.flightId());
        Booking newBooking = bookingConverter.fromCreateDtoToEntity(booking, flight, price);
        newBooking.setSeatNumber(calcSeatNumber(flight));
        bookingRepository.persistAndFlush(newBooking);
        flightService.updateAvailableSeats(flight.getId(), getOccupiedSeats(flight));
        return bookingConverter.fromEntityToGetDto(newBooking);
    }


    @Override
    @Transactional
    public GetBookingDto update(CreateBookingDTO booking, Long id) throws BookingNotFoundException, FlightNotFoundException, PriceNotFoundException {
        Price price = priceService.findById(booking.priceId());
        Flight flight = flightService.findById(booking.flightId());
        Booking dbBooking = findById(id);
        dbBooking.setFName(booking.fName());
        dbBooking.setEmail(booking.email());
        dbBooking.setPhone(booking.phone());
        dbBooking.setPrice(price);
        dbBooking.setFlight(flight);
        bookingRepository.persist(dbBooking);
        return bookingConverter.fromEntityToGetDto(dbBooking);
    }

    @Override
    @Transactional
    public void delete(Long id) throws BookingNotFoundException {
        findById(id);
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking findById(Long id) throws BookingNotFoundException {
        return bookingRepository.findByIdOptional(id).orElseThrow(() -> new BookingNotFoundException(BOOKING_ID_NOT_FOUND + id));
    }

    private String calcSeatNumber(Flight flight) {
        long nextSeatNumber = getOccupiedSeats(flight) + 1;
        long row = (nextSeatNumber - 1) / flight.getPlane().getSeatsPerRow() + 1;
        long column = (nextSeatNumber - 1) % flight.getPlane().getSeatsPerRow();

        char columnLetter = (char) ('A' + column);

        return row + "" + columnLetter;
    }

    private void updateAllFlights(List<GetBookingDto> bookingList) throws FlightNotFoundException {
        for (GetBookingDto booking : bookingList) {
            flightService.updateAvailableSeats(booking.flight().id(), getOccupiedSeats(flightService.findById(booking.flight().id())));
        }
    }

    private long getOccupiedSeats(Flight flight) {
        return bookingRepository.count("flight", flight);

    }
}
