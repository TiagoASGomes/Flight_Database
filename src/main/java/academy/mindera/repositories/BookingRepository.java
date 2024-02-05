package academy.mindera.repositories;

import academy.mindera.models.Booking;
import academy.mindera.models.Flight;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class BookingRepository implements PanacheRepository<Booking> {

    public List<Booking> findByFlight(Flight flight) {
        return listAll();
    }
}
