package academy.mindera.services.implementation;

import academy.mindera.converters.PriceConverter;
import academy.mindera.dto.price.CreatePriceDto;
import academy.mindera.dto.price.GetPriceDTO;
import academy.mindera.exceptions.price.PriceInUseException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.models.Price;
import academy.mindera.repositories.PricesRepository;
import academy.mindera.services.interfaces.PriceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
@Transactional
public class PriceServiceImpl implements PriceService {
    private final int PAGE_SIZE = 10;
    @Inject
    private PricesRepository pricesRepository;
    @Inject
    private PriceConverter priceConverter;

    @Override
    public List<GetPriceDTO> findAllPrices(int page) {
        return priceConverter.fromEntityListToGetDtoList(pricesRepository.findAll().page(page, PAGE_SIZE).list());
    }

    @Override
    public GetPriceDTO findPriceById(Long id) {
        return priceConverter.fromEntityToGetDto(pricesRepository.findById(id));
    }

    @Override
    public CreatePriceDto savePrice(CreatePriceDto price) {
        pricesRepository.persist(priceConverter.fromCreateDtoToEntity(price));
        return price;
    }

    @Override
    public GetPriceDTO updatePrice(CreatePriceDto price, Long id) throws PriceNotFoundException {
        Price existingPrice = findById(id);
        existingPrice.setClassName(price.className());
        existingPrice.setPrice(price.price());
        pricesRepository.persist(existingPrice);
        return priceConverter.fromEntityToGetDto(existingPrice);
    }

    @Override
    public void deletePrice(Long id) throws PriceNotFoundException, PriceInUseException {
        Price price = findById(id);
        checkIfPriceIsInUse(price);
        pricesRepository.deleteById(id);
    }

    @Override
    public Price findById(Long id) throws PriceNotFoundException {
        return pricesRepository.findByIdOptional(id).orElseThrow(() -> new PriceNotFoundException("message"));
    }

    @Override
    public Set<Price> findByIds(List<Long> ids) throws PriceNotFoundException {
        Set<Price> result = new HashSet<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }

    private void checkIfPriceIsInUse(Price price) throws PriceInUseException {
        if (price.getFlights() != null && !price.getFlights().isEmpty()) {
            throw new PriceInUseException("Price is in use");
        }
    }
}
