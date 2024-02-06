package academy.mindera.dto.plane;

public record CreatePlaneDTO(

        int luggageCapacity,

        String companyOwner,

        String modelName,
        int planeRows,
        int seatsPerRow
) {
}
