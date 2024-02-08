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

import static academy.mindera.util.Messages.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightControllerTest {

    private final String FLIGHT_ENDPOINT = "/api/v1/flights";
    private final String PLANE_ENDPOINT = "/api/v1/planes";
    private final Plane plane = Plane.builder().luggageCapacity(100).companyOwner("TAP").modelName("Airbus A320").planeRows(10).seatsPerRow(4).build();
    private final String flightJson = "{\"origin\":\"LIS\",\"destination\":\"OPO\",\"duration\":2.5,\"dateOfFlight\":\"2030-01-01T12:00\",\"plane\":1,\"prices\":[{\"type\":\"ECONOMY\",\"price\":100},{\"type\":\"BUSINESS\",\"price\":200}]}";
    private final String flightJson2 = "{\"origin\":\"OPO\",\"destination\":\"LIS\",\"duration\":3.5,\"dateOfFlight\":\"2030-01-02T12:00\",\"plane\":1,\"prices\":[{\"type\":\"ECONOMY\",\"price\":100},{\"type\":\"BUSINESS\",\"price\":200}]}";

    @Test
    @Order(1)
    void CreatePlane() {
        given()
                .body(plane)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(PLANE_ENDPOINT)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2)
    void testGetAllGetEmptyFlights() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(FLIGHT_ENDPOINT + "?page=0")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @Order(3)
    void testCreateFlight() {
        given()
                .body(flightJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(FLIGHT_ENDPOINT)
                .then()
                .statusCode(201);


        given()
                .body(flightJson2)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(FLIGHT_ENDPOINT)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(4)
    void testCreateWithEmptyBody() {
        Response response = given()
                .body("{}")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(FLIGHT_ENDPOINT).andReturn();

        assertEquals(400, response.getStatusCode());
        String body = response.getBody().asString();
        assertTrue(body.contains(INVALID_ORIGIN));
        assertTrue(body.contains(INVALID_DESTINATION));
        assertTrue(body.contains(INVALID_DURATION));
        assertTrue(body.contains(INVALID_DATE_OF_FLIGHT));
        assertTrue(body.contains(INVALID_PLANE_ID));
        assertTrue(body.contains(INVALID_PRICES));


    }

    @Test
    @Order(5)
    void testCreateWithWrongValidations() {
        String json = "{\"origin\":\"asd\",\"destination\":\"asd\",\"duration\":25,\"dateOfFlight\":\"2020-01-01T12:00\",\"plane\":0,\"prices\":[{\"type\":\"ECONdOMY\",\"price\":0},{\"type\":\"BUSfINESS\",\"price\":0}]}";

        Response response = given()
                .body(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(FLIGHT_ENDPOINT).andReturn();

        assertEquals(400, response.getStatusCode());
        String body = response.getBody().asString();
        assertTrue(body.contains(INVALID_ORIGIN));
        assertTrue(body.contains(INVALID_DESTINATION));
        assertTrue(body.contains(INVALID_DURATION));
        assertTrue(body.contains(INVALID_DATE_OF_FLIGHT));
        assertTrue(body.contains(INVALID_PLANE_ID));
    }

    @Test
    @Order(6)
    void testGetAllFlights() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(FLIGHT_ENDPOINT + "?page=0")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    @Order(7)
    void testGetFlightById() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(FLIGHT_ENDPOINT + "/1")
                .then()
                .statusCode(200)
                .body("origin", is("LIS"))
                .body("destination", is("OPO"))
                .body("duration", is(2.5f))
                .body("dateOfFlight", is("2030-01-01T12:00"))
                .body("plane.id", is(1))
                .body("price.size()", is(2));
    }

    @Test
    @Order(8)
    void testGetFlightByIdNotFound() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(FLIGHT_ENDPOINT + "/100")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(9)
    void testSearchFlightsLISOPO100() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(FLIGHT_ENDPOINT + "/search/LIS/OPO?date=2030-01-01T12:00&price=100&page=0")
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @Order(10)
    void testSearchFlightsLISOPO1000() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(FLIGHT_ENDPOINT + "/search/LIS/OPO?date=2030-01-01T12:00&price=1&page=0")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @Order(11)
    void updateFlight() {
        String json = "{\"origin\":\"OPO\",\"destination\":\"LIS\",\"duration\":4.5,\"dateOfFlight\":\"2031-01-01T12:00\",\"plane\":1,\"prices\":[{\"type\":\"ECONOMY\",\"price\":100},{\"type\":\"BUSINESS\",\"price\":200}]}";
        given()
                .body(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(FLIGHT_ENDPOINT + "/1")
                .then()
                .statusCode(200)
                .body("origin", is("OPO"))
                .body("destination", is("LIS"))
                .body("duration", is(4.5f))
                .body("dateOfFlight", is("2031-01-01T12:00"))
                .body("plane.id", is(1))
                .body("price.size()", is(2));
    }

    @Test
    @Order(12)
    void deleteFlight() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().delete(FLIGHT_ENDPOINT + "/1")
                .then()
                .statusCode(200);
    }


}
