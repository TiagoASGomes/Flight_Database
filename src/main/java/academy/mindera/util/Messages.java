package academy.mindera.util;

public class Messages {
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String INVALID_NAME = "Invalid name, only letters and spaces are allowed";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number";
    public static final String INVALID_FLIGHT_ID = "Invalid flight id, flight id must be greater than 0";
    public static final String INVALID_PRICE_ID = "Invalid price id, price id must be greater than 0";
    public static final String INVALID_ORIGIN = "Invalid origin, only up to 6 uppercase letters are allowed";
    public static final String INVALID_DESTINATION = "Invalid destination, only up to 6 uppercase letters are allowed";
    public static final String INVALID_DURATION = "Invalid duration, duration must be between 0 and 24 hours";
    public static final String INVALID_DATE_OF_FLIGHT = "Invalid date of flight, date of flight must be in the future or present";
    public static final String INVALID_PLANE_ID = "Invalid plane id, plane id must be greater than 0";
    public static final String INVALID_PRICES = "Invalid prices";
    public static final String INVALID_PLANE_CAPACITY = "Invalid plane capacity, plane capacity must be between 1 and 10000";
    public static final String INVALID_COMPANY_OWNER = "Invalid company owner, only letters, numbers and spaces are allowed";
    public static final String INVALID_MODEL_NAME = "Invalid model name, only letters, numbers and spaces are allowed";
    public static final String INVALID_ROWS = "Invalid rows, rows must be between 1 and 100";
    public static final String INVALID_COLUMNS = "Invalid columns, columns must be between 1 and 12";
    public static final String INVALID_CLASS_NAME = "Invalid class name, only letters, numbers and spaces are allowed";
    public static final String INVALID_PRICE = "Invalid price, price must be between 1 and 9999";
    public static final String FULL_FLIGHT = "The flight is full";
    public static final String BOOKING_ID_NOT_FOUND = "No booking found with id: ";
    public static final String FLIGHT_ID_NOT_FOUND = "No flight found with id: ";
    public static final String PLANE_ID_NOT_FOUND = "No plane found with id: ";
    public static final String PRICE_ID_NOT_FOUND = "No price found with id: ";
    public static final String PRICE_IN_USE = "Price in use, cannot delete price with id: ";

    private Messages() {
    }
}
