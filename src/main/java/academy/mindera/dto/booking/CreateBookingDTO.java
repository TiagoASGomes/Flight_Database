package academy.mindera.dto.booking;

public record CreateBookingDTO(
        String fName,
        String email,
        String phone,
        Long flightId,
        Long priceId
) {
}
