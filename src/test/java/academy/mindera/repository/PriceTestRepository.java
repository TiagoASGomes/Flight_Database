package academy.mindera.repository;

import academy.mindera.repositories.PricesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import javax.annotation.Priority;

@Priority(1)
@Alternative
@ApplicationScoped
public class PriceTestRepository extends PricesRepository {
    public void resetAutoIncrement() {
        this.getEntityManager().createNativeQuery("ALTER TABLE prices ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

}
