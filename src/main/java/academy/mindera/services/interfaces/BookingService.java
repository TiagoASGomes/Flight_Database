package academy.mindera.services.interfaces;

import academy.mindera.dto.booking.CreateBookingDTO;
import academy.mindera.dto.booking.GetBookingDto;
import academy.mindera.exceptions.booking.BookingNotFoundException;
import academy.mindera.exceptions.flight.FlightFullException;
import academy.mindera.exceptions.flight.FlightNotFoundException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.models.Booking;

import java.util.List;

public interface BookingService {
    List<GetBookingDto> getAll(int page);

    GetBookingDto getById(Long id) throws BookingNotFoundException;

    List<GetBookingDto> createList(List<CreateBookingDTO> booking) throws FlightNotFoundException, PriceNotFoundException, FlightFullException;

    GetBookingDto create(CreateBookingDTO booking) throws FlightNotFoundException, PriceNotFoundException, FlightFullException;

    GetBookingDto update(CreateBookingDTO booking, Long id) throws BookingNotFoundException, FlightNotFoundException, PriceNotFoundException;

    void delete(Long id) throws BookingNotFoundException;

    Booking findById(Long id) throws BookingNotFoundException;
}
