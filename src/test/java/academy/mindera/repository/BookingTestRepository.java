package academy.mindera.repository;

import academy.mindera.repositories.BookingRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;

@Priority(1)
@Alternative
@ApplicationScoped
public class BookingTestRepository extends BookingRepository {
    @PostConstruct
    public void resetAutoIncrement() {
        this.getEntityManager().createNativeQuery("ALTER TABLE bookings ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }
}
