package academy.mindera.exceptions;

public class SeatAlreadyBookedException extends SeatException {
    public SeatAlreadyBookedException(String message) {
        super(message);
    }
}
