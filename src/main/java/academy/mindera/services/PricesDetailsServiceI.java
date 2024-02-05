package academy.mindera.services;

import academy.mindera.models.Prices;

import java.util.List;

public interface PricesDetailsServiceI {
    List<Prices> findAllPrices();

    Prices findPriceById(long id);

    void savePrice(Prices price);

    void updatePrice(Prices price);

    void deletePrice(long id);
}
