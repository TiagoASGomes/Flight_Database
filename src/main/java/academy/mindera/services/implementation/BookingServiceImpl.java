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

import java.util.ArrayList;
import java.util.List;

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
        for (CreateBookingDTO BookingDTO : booking) {
            if (flightService.checkIfFullCapacity(BookingDTO.flightId())) {
                bookingList.forEach(bookingEntity -> bookingRepository.deleteById(bookingEntity.id()));
                throw new FlightFullException("Flight is full");
            }
            bookingList.add(create(BookingDTO));
        }
        return bookingList;
    }

    @Override
    public GetBookingDto create(CreateBookingDTO booking) throws FlightNotFoundException, PriceNotFoundException, FlightFullException {
        Price price = priceService.findById(booking.priceId());
        Flight flight = flightService.findById(booking.flightId());
        Booking newBooking = bookingConverter.fromCreateDtoToEntity(booking, flight, price);
        bookingRepository.persist(newBooking);
        return bookingConverter.fromEntityToGetDto(newBooking);
    }


    @Override
    public GetBookingDto update(CreateBookingDTO booking, Long id) throws BookingNotFoundException, FlightNotFoundException, PriceNotFoundException {
        Price price = priceService.findById(booking.priceId());
        Flight flight = flightService.findById(booking.flightId());
        Booking newBooking = bookingConverter.fromCreateDtoToEntity(booking, flight, price);
        newBooking.setId(id);
        bookingRepository.persist(newBooking);
        return bookingConverter.fromEntityToGetDto(newBooking);
    }

    @Override
    public void delete(Long id) throws BookingNotFoundException {
        findById(id);
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking findById(Long id) throws BookingNotFoundException {
        return bookingRepository.findByIdOptional(id).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
    }

}
