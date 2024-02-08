package academy.mindera.repositories;

import academy.mindera.models.Flight;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;

@ApplicationScoped
public class FlightsRepository implements PanacheRepository<Flight> {

    public PanacheQuery<Flight> search(String origin, String destination, LocalDateTime dateOfFlight) {
        return find("origin = ?1 and destination = ?2 and dateOfFlight >= ?3", origin, destination, dateOfFlight);
    }
}
