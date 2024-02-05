package academy.mindera.converters;

import academy.mindera.dto.CreatePlaneDTO;
import academy.mindera.models.Planes;

public class PlaneConverter {

    public static CreatePlaneDTO toDTO(Planes plane){

        CreatePlaneDTO dto = new CreatePlaneDTO();
        dto.setId(plane.getId());
        dto.setPeopleCapacity(plane.getPeopleCapacity());
        dto.setLuggageCapacity(plane.getLuggageCapacity());
        dto.setCompanyOwner(plane.getCompanyOwner());
        dto.setModelName(plane.getModelName());
        return dto;
    }

    public static Planes fromDTO(CreatePlaneDTO dto){

        Planes plane = new Planes();
        plane.setId(dto.getId());
        plane.setPeopleCapacity(dto.getPeopleCapacity());
        plane.setLuggageCapacity(dto.getLuggageCapacity());
        plane.setCompanyOwner(dto.getCompanyOwner());
        plane.setModelName(dto.getModelName());
        return plane;
    }
}
