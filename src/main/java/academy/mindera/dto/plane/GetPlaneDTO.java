package academy.mindera.dto.plane;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record GetPlaneDTO(
        @Schema(description = "The id of the plane", example = "1")
        long id,
        @Schema(description = "The total number of seats of the plane", example = "100")
        int peopleCapacity,
        @Schema(description = "The luggage capacity of the plane in kg", example = "1000")
        int luggageCapacity,
        @Schema(description = "The company owner of the plane", example = "TAP")
        String companyOwner,
        @Schema(description = "The model name of the plane", example = "A320")
        String modelName,
        @Schema(description = "If the plane is discontinued or not", example = "false")
        boolean discontinued
) {
}
