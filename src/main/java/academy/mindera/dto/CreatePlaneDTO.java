package academy.mindera.dto;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePlaneDTO {

    @NotNull
    @Min(1)
    private int peopleCapacity;

    @NotNull
    @Min(1)
    private int luggageCapacity;

    @NotBlank
    private String companyOwner;

    @NotBlank
    private String modelName;
}
