package academy.mindera.converters;

import academy.mindera.dto.price.CreatePriceDto;
import academy.mindera.dto.price.GetPriceDTO;
import academy.mindera.models.Price;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PriceConverter {

    public Price fromCreateDtoToEntity(CreatePriceDto createPriceDto) {
        return Price.builder()
                .className(createPriceDto.className())
                .price(createPriceDto.price())
                .build();
    }

    public GetPriceDTO fromEntityToGetDto(Price price) {
        return new GetPriceDTO(price.getId(),
                price.getClassName(),
                price.getPrice());
    }

    public List<GetPriceDTO> fromEntityListToGetDtoList(List<Price> prices) {
        return prices.stream()
                .map(this::fromEntityToGetDto)
                .toList();
    }
}
