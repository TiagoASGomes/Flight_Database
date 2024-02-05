package academy.mindera.repositories;

import academy.mindera.models.Flights;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FlightsRepository implements PanacheRepository<Flights> {

}
