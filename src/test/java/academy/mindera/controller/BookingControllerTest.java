package academy.mindera.controller;

import academy.mindera.models.Plane;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static academy.mindera.util.Messages.FULL_FLIGHT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingControllerTest {

    private final String BOOKING_ENDPOINT = "/api/v1/bookings";
    private final String FLIGHT_ENDPOINT = "/api/v1/flights";
    private final String PLANE_ENDPOINT = "/api/v1/planes";
    private final Plane plane = Plane.builder().luggageCapacity(100).companyOwner("TAP").modelName("Airbus A320").planeRows(2).seatsPerRow(2).build();
    private final String flightJson = "{\"origin\":\"LIS\",\"destination\":\"OPO\",\"duration\":2.5,\"dateOfFlight\":\"2030-01-01T12:00\",\"plane\":1,\"prices\":[{\"type\":\"ECONOMY\",\"price\":100},{\"type\":\"BUSINESS\",\"price\":200}]}";

    private final String bookingJson = "[{\"fName\":\"John\",\"email\":\"teste@example.com\",\"phone\":\"912345678\",\"flightId\":1,\"priceId\":1}]";

    @Test
    @Order(1)
    void setUp() {
        given()
                .body(plane)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(PLANE_ENDPOINT)
                .then()
                .statusCode(201);

        given()
                .body(flightJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(FLIGHT_ENDPOINT)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2)
    void getAllEmptyBookings() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(BOOKING_ENDPOINT + "?page=0")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @Order(3)
    void createBooking() {
        given()
                .body(bookingJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(BOOKING_ENDPOINT)
                .then()
                .statusCode(201);
        given()
                .body(bookingJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(BOOKING_ENDPOINT)
                .then()
                .statusCode(201);

        given()
                .body(bookingJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(BOOKING_ENDPOINT)
                .then()
                .statusCode(201);
        given()
                .body(bookingJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(BOOKING_ENDPOINT)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(4)
    void getAllBookings() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(BOOKING_ENDPOINT + "?page=0")
                .then()
                .statusCode(200)
                .body("size()", is(4));
    }

    @Test
    @Order(5)
    void createWithMaxCapacity() {
        Response response = given()
                .body(bookingJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(BOOKING_ENDPOINT).andReturn();

        assertEquals(400, response.getStatusCode());
        String body = response.getBody().asString();
        assertTrue(body.contains(FULL_FLIGHT));
    }

    @Test
    @Order(6)
    void deleteBooking() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().delete(BOOKING_ENDPOINT + "/1")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(7)
    void deleteBookingNotFound() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().delete(BOOKING_ENDPOINT + "/1")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(8)
    void updateBooking() {
        given()
                .body(bookingJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(BOOKING_ENDPOINT + "/2")
                .then()
                .statusCode(200);
    }

}
