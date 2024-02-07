package academy.mindera.repository;

import academy.mindera.repositories.PlaneRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import javax.annotation.Priority;

@Priority(1)
@Alternative
@ApplicationScoped
public class PlaneTestRepository extends PlaneRepository {

    public void resetAutoIncrement() {
        this.getEntityManager().createNativeQuery("ALTER TABLE planes ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }
}
