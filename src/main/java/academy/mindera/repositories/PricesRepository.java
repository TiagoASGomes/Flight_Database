package academy.mindera.repositories;

import academy.mindera.models.Prices;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PricesRepository implements PanacheRepository<Prices> {


}
