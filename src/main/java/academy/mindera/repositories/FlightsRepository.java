package academy.mindera.repositories;

import academy.mindera.models.Flight;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped

public class FlightsRepository implements PanacheRepository<Flight> {

    public List<Flight> search(String origin, String destination, LocalDateTime dateOfFlight, int page) {
        return getEntityManager().createQuery("SELECT f FROM flights f WHERE f.origin = :origin AND f.destination = :destination AND f.dateOfFlight >= :dateOfFlight and f.fullCapacity = false", Flight.class)
                .setParameter("origin", origin)
                .setParameter("destination", destination)
                .setParameter("dateOfFlight", dateOfFlight)
                .setFirstResult(page * 20)
                .setMaxResults(20)
                .getResultList();
    }
}
