package academy.mindera.services;

import academy.mindera.models.Prices;
import academy.mindera.repositories.PricesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class PricesDetailsService implements PricesDetailsServiceI {

    @Inject
    PricesRepository pricesRepository;

    @Override
    public List<Prices> findAllPrices() {
        return pricesRepository.listAll();
    }

    @Override
    public Prices findPriceById(long id) {
        return pricesRepository.findByIdOptional(id).orElse(null);
    }

    @Override
    public void savePrice(Prices price) {
        pricesRepository.persist(price);
    }

    @Override
    public void updatePrice(Prices price) {
        Prices existingPrice = pricesRepository.findById(price.getId());
        if (existingPrice != null){
            existingPrice.setClassName(price.getClassName());
            existingPrice.setPrice(price.getPrice());
            pricesRepository.persist(existingPrice);
        }
    }

    @Override
    public void deletePrice(long id) {
        pricesRepository.deleteById(id);
    }
}
