package academy.mindera.services.interfaces;

import academy.mindera.dto.price.CreatePriceDto;
import academy.mindera.dto.price.GetPriceDTO;
import academy.mindera.exceptions.price.PriceInUseException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.models.Price;

import java.util.List;
import java.util.Set;

public interface PriceService {
    List<GetPriceDTO> findAllPrices(int page);

    GetPriceDTO findPriceById(Long id);

    CreatePriceDto savePrice(CreatePriceDto price);

    GetPriceDTO updatePrice(CreatePriceDto price, Long id) throws PriceNotFoundException;

    void deletePrice(Long id) throws PriceNotFoundException, PriceInUseException;

    Price findById(Long id) throws PriceNotFoundException;

    Set<Price> findByIds(List<Long> ids) throws PriceNotFoundException;
}
