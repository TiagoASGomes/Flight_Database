package academy.mindera.dto.plane;

public record GetPlaneDTO(
        long id,
        int peopleCapacity,
        int luggageCapacity,
        String companyOwner,
        String modelName,
        boolean discontinued
) {
}
