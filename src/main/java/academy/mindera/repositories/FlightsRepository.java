package academy.mindera.repositories;

import academy.mindera.models.Flight;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FlightsRepository implements PanacheRepository<Flight> {

    public PanacheQuery<Flight> search(String origin, String destination, String date) {
        //Find flights by origin, destination and after date and price lower than the given
        return find("origin = ?1 and destination = ?2 and date > ?3", origin, destination, date);
    }
}
