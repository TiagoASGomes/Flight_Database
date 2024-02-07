package academy.mindera.repository;

import academy.mindera.repositories.FlightsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;

@Priority(1)
@Alternative
@ApplicationScoped
public class FlightTestRepository extends FlightsRepository {
    @PostConstruct
    public void resetAutoIncrement() {
        this.getEntityManager().createNativeQuery("ALTER TABLE flights ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }
}
