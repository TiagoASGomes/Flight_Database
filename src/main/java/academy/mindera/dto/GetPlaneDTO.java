package academy.mindera.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPlaneDTO {

    private long id;
    private int peopleCapacity;
    private int luggageCapacity;
    private String companyOwner;
    private String modelName;
}
