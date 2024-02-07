package academy.mindera.dto.plane;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static academy.mindera.util.Messages.*;

public record CreatePlaneDTO(
        @NotNull(message = INVALID_PLANE_CAPACITY)
        @Min(value = 1, message = INVALID_PLANE_CAPACITY)
        @Max(value = 10000, message = INVALID_PLANE_CAPACITY)
        Integer luggageCapacity,
        @NotNull(message = INVALID_COMPANY_OWNER)
        @Pattern(regexp = "^[A-Za-z_0-9 ]+$", message = INVALID_COMPANY_OWNER)
        String companyOwner,
        @NotNull(message = INVALID_MODEL_NAME)
        @Pattern(regexp = "^[A-Za-z_0-9 ]+$", message = INVALID_MODEL_NAME)
        String modelName,
        @NotNull(message = INVALID_ROWS)
        @Min(value = 1, message = INVALID_ROWS)
        @Max(value = 100, message = INVALID_ROWS)
        Integer planeRows,
        @NotNull(message = INVALID_COLUMNS)
        @Min(value = 1, message = INVALID_COLUMNS)
        @Max(value = 12, message = INVALID_COLUMNS)
        Integer seatsPerRow
) {
}
