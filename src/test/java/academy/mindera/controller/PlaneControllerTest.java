package academy.mindera.controller;

import academy.mindera.models.Plane;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlaneControllerTest {

    private final String PLANE_ENDPOINT = "/api/v1/planes";
    private final Plane plane1 = Plane.builder().luggageCapacity(100).companyOwner("TAP").modelName("Airbus A320").planeRows(10).seatsPerRow(4).build();
    private final Plane plane2 = Plane.builder().luggageCapacity(200).companyOwner("Ryanair").modelName("Boeing 737").planeRows(8).seatsPerRow(6).build();

    @Test
    @Order(1)
    void testGetAllWithNoPlanes() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PLANE_ENDPOINT + "?page=1")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }


    @Test
    @Order(2)
    void testCreatePlane() {
        given()
                .body(plane1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(PLANE_ENDPOINT)
                .then()
                .statusCode(201);

        given()
                .body(plane2)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(PLANE_ENDPOINT)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(3)
    void testCreateWithInvalidPlane() {
        given()
                .body("{}")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(PLANE_ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    @Order(4)
    void testCreateWithInvalidPlane2() {
        given()
                .body(Plane.builder().luggageCapacity(10001).companyOwner("!#").modelName("Airbus!##320").planeRows(101).seatsPerRow(14).build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post(PLANE_ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    @Order(5)
    void testGetAllPlanes() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PLANE_ENDPOINT + "?page=0")
                .then()
                .statusCode(200)
                .body("size()", is(4));
    }

    @Test
    @Order(6)
    void testGetPlaneById() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PLANE_ENDPOINT + "/1")
                .then()
                .statusCode(200)
                .body("companyOwner", is("TAP"))
                .body("modelName", is("Airbus A320"))
                .body("luggageCapacity", is(100))
                .body("id", is(1))
                .body("peopleCapacity", is(4))
                .body("discontinued", is(false));
    }

    @Test
    @Order(7)
    void testGetPlaneByIdNotFound() {
        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get(PLANE_ENDPOINT + "/100")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(8)
    void testUpdatePlane() {
        given()
                .body(Plane.builder().luggageCapacity(200).companyOwner("Ryanair").modelName("Boeing 737").planeRows(8).seatsPerRow(6).build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(PLANE_ENDPOINT + "/2")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(9)
    void testUpdatePlaneNotFound() {
        given()
                .body(Plane.builder().luggageCapacity(200).companyOwner("Ryanair").modelName("Boeing 737").planeRows(8).seatsPerRow(6).build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(PLANE_ENDPOINT + "/100")
                .then()
                .statusCode(404);
    }


}
