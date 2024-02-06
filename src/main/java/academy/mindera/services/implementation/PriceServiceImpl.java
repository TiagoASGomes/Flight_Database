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
    public List<GetPriceDTO> getAll(int page) {
        return priceConverter.fromEntityListToGetDtoList(pricesRepository.findAll().page(page, PAGE_SIZE).list());
    }

    @Override
    public GetPriceDTO getById(Long id) {
        return priceConverter.fromEntityToGetDto(pricesRepository.findById(id));
    }

    @Override
    public GetPriceDTO create(CreatePriceDto price) {
        Price priceEntity = priceConverter.fromCreateDtoToEntity(price);
        pricesRepository.persist(priceEntity);
        return priceConverter.fromEntityToGetDto(priceEntity);
    }

    @Override
    public Set<Price> create(Set<CreatePriceDto> price) {
        Set<Price> prices = priceConverter.fromCreateDtoListToEntityList(price);
        pricesRepository.persist(prices);
        return prices;
    }

    @Override
    public GetPriceDTO update(CreatePriceDto price, Long id) throws PriceNotFoundException {
        Price existingPrice = findById(id);
        existingPrice.setClassName(price.className());
        existingPrice.setPrice(price.price());
        pricesRepository.persist(existingPrice);
        return priceConverter.fromEntityToGetDto(existingPrice);
    }

    @Override
    public void delete(Long id) throws PriceNotFoundException, PriceInUseException {
        Price price = findById(id);
        checkIfPriceIsInUse(price);
        pricesRepository.deleteById(id);
    }

    @Override
    public Price findById(Long id) throws PriceNotFoundException {
        return pricesRepository.findByIdOptional(id).orElseThrow(() -> new PriceNotFoundException("message"));
    }


    private void checkIfPriceIsInUse(Price price) throws PriceInUseException {
        if (price.getFlights() != null && !price.getFlights().isEmpty()) {
            throw new PriceInUseException("Price is in use");
        }
    }
}
