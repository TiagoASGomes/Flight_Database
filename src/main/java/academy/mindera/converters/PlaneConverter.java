package academy.mindera.converters;

import academy.mindera.dto.plane.CreatePlaneDTO;
import academy.mindera.dto.plane.GetCreatedPlaneDto;
import academy.mindera.dto.plane.GetPlaneDTO;
import academy.mindera.models.Plane;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PlaneConverter {


    public Plane fromCreateDtoToEntity(CreatePlaneDTO dto) {
        return Plane.builder()
                .peopleCapacity(dto.peopleCapacity())
                .luggageCapacity(dto.luggageCapacity())
                .companyOwner(dto.companyOwner())
                .modelName(dto.modelName())
                .discontinued(false)
                .build();
    }

    public GetPlaneDTO fromEntityToGetDto(Plane plane) {
        return new GetPlaneDTO(
                plane.getId(),
                plane.getPeopleCapacity(),
                plane.getLuggageCapacity(),
                plane.getCompanyOwner(),
                plane.getModelName(),
                plane.isDiscontinued()
        );
    }

    public List<GetPlaneDTO> fromEntityListToGetDtoList(List<Plane> planes) {
        return planes.stream()
                .map(this::fromEntityToGetDto)
                .toList();
    }

    public GetCreatedPlaneDto fromEntityToGetCreatedDto(Plane plane) {
        return new GetCreatedPlaneDto(
                plane.getPeopleCapacity(),
                plane.getLuggageCapacity(),
                plane.getCompanyOwner(),
                plane.getModelName(),
                plane.isDiscontinued()
        );
    }
}
