package academy.mindera.dto.price;


import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record GetPriceDTO(
        @Schema(description = "The id of the price", example = "1")
        long id,
        @Schema(description = "The class name of the price", example = "ECONOMY")
        String className,
        @Schema(description = "The price of the class", example = "100")
        int price
) {
}
