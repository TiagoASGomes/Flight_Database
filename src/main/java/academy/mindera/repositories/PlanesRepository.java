package academy.mindera.repositories;

import academy.mindera.models.Planes;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlanesRepository implements PanacheRepository<Planes> {

    // methods to handle HTTP requests related to planes
}
