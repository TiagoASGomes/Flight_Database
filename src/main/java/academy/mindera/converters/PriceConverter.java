package academy.mindera.converters;

import academy.mindera.dto.PriceDTO;
import academy.mindera.models.Prices;

public class PriceConverter {

    public static PriceDTO toDTO(Prices price){

        PriceDTO dto = new PriceDTO();
        dto.setId(price.getId());
        dto.setClassName(price.getClassName());
        dto.setPrice(price.getPrice());
        return dto;
    }

    public static Prices fromDTO(PriceDTO dto){

        Prices price = new Prices();
        price.setId(dto.getId());
        price.setClassName(dto.getClassName());
        price.setPrice(dto.getPrice());
        return price;
    }
}
