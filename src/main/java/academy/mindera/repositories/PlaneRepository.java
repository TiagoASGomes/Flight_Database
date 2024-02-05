package academy.mindera.repositories;

import academy.mindera.models.Plane;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlaneRepository implements PanacheRepository<Plane> {

}
