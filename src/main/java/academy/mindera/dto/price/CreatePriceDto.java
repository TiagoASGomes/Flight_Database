package academy.mindera.dto.price;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static academy.mindera.util.Messages.INVALID_CLASS_NAME;
import static academy.mindera.util.Messages.INVALID_PRICE;

public record CreatePriceDto(
        @NotNull(message = INVALID_CLASS_NAME)
        @Pattern(regexp = "^[A-Z]+$", message = INVALID_CLASS_NAME)
        @Schema(description = "The class name of the price", example = "ECONOMY")
        String className,
        @NotNull(message = INVALID_PRICE)
        @Min(value = 1, message = INVALID_PRICE)
        @Max(value = 9999, message = INVALID_PRICE)
        @Schema(description = "The price of the class", example = "100")
        Integer price
) {
}
