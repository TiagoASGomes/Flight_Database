package academy.mindera.services.interfaces;

import academy.mindera.dto.price.CreatePriceDto;
import academy.mindera.dto.price.GetPriceDTO;
import academy.mindera.exceptions.price.PriceInUseException;
import academy.mindera.exceptions.price.PriceNotFoundException;
import academy.mindera.models.Price;

import java.util.List;
import java.util.Set;

public interface PriceService {
    List<GetPriceDTO> getAll(int page);

    GetPriceDTO getById(Long id);

    GetPriceDTO create(CreatePriceDto price);

    Set<Price> create(Set<CreatePriceDto> price);

    GetPriceDTO update(CreatePriceDto price, Long id) throws PriceNotFoundException;

    void delete(Long id) throws PriceNotFoundException, PriceInUseException;

    Price findById(Long id) throws PriceNotFoundException;

}
