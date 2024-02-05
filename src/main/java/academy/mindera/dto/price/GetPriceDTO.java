package academy.mindera.dto.price;


public record GetPriceDTO(
        long id,
        String className,
        int price
) {
}
