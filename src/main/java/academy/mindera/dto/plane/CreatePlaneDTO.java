package academy.mindera.dto.plane;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


public record CreatePlaneDTO(
        @NotNull
        @Min(1)
        int peopleCapacity,
        @NotNull
        @Min(1)
        int luggageCapacity,
        @NotBlank
        String companyOwner,
        @NotBlank
        String modelName
) {
}
