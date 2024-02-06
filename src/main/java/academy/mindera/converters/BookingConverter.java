package academy.mindera.converters;

import academy.mindera.dto.booking.CreateBookingDTO;
import academy.mindera.dto.booking.GetBookingDto;
import academy.mindera.models.Booking;
import academy.mindera.models.Flight;
import academy.mindera.models.Price;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class BookingConverter {
    @Inject
    private FlightConverter flightConverter;
    @Inject
    private PriceConverter priceConverter;

    public GetBookingDto fromEntityToGetDto(Booking booking) {
        //TODO seat number
        return new GetBookingDto(
                booking.getId(),
                booking.getFName(),
                booking.getEmail(),
                booking.getPhone(),
                "1",
                flightConverter.fromEntityToGetDto(booking.getFlight()),
                priceConverter.fromEntityToGetDto(booking.getPrice())
        );
    }

    public List<GetBookingDto> fromEntityListToGetDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::fromEntityToGetDto)
                .toList();
    }

    public Booking fromCreateDtoToEntity(CreateBookingDTO booking, Flight flight, Price price) {
        return Booking.builder()
                .fName(booking.fName())
                .email(booking.email())
                .phone(booking.phone())
                .flight(flight)
                .price(price)
                .build();

    }
}
