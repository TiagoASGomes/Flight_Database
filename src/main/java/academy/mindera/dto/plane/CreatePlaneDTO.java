package academy.mindera.dto.plane;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static academy.mindera.util.Messages.*;

public record CreatePlaneDTO(
        @NotNull(message = INVALID_PLANE_CAPACITY)
        @Min(value = 1, message = INVALID_PLANE_CAPACITY)
        @Max(value = 10000, message = INVALID_PLANE_CAPACITY)
        @Schema(description = "The luggage capacity of the plane in kg", example = "1000")
        Integer luggageCapacity,
        @NotNull(message = INVALID_COMPANY_OWNER)
        @Pattern(regexp = "^[A-Za-z_0-9 ]+$", message = INVALID_COMPANY_OWNER)
        @Schema(description = "The company owner of the plane", example = "TAP")
        String companyOwner,
        @NotNull(message = INVALID_MODEL_NAME)
        @Pattern(regexp = "^[A-Za-z_0-9 ]+$", message = INVALID_MODEL_NAME)
        @Schema(description = "The model name of the plane", example = "A320")
        String modelName,
        @NotNull(message = INVALID_ROWS)
        @Min(value = 1, message = INVALID_ROWS)
        @Max(value = 100, message = INVALID_ROWS)
        @Schema(description = "The number of rows of the plane", example = "10")
        Integer planeRows,
        @NotNull(message = INVALID_COLUMNS)
        @Min(value = 1, message = INVALID_COLUMNS)
        @Max(value = 12, message = INVALID_COLUMNS)
        @Schema(description = "The number of seats per row of the plane", example = "6")
        Integer seatsPerRow
) {
}
