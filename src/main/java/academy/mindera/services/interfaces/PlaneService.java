package academy.mindera.services.interfaces;

import academy.mindera.dto.plane.CreatePlaneDTO;
import academy.mindera.dto.plane.GetPlaneDTO;
import academy.mindera.exceptions.ValidationException;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.models.Plane;

import java.util.List;

public interface PlaneService {
    GetPlaneDTO create(CreatePlaneDTO plane) throws ValidationException;

    List<GetPlaneDTO> getAll(int page);

    GetPlaneDTO getById(Long id) throws PlaneNotFoundException;

    void delete(Long id) throws PlaneNotFoundException;

    GetPlaneDTO update(CreatePlaneDTO plane, Long id) throws PlaneNotFoundException;

    Plane findById(Long id) throws PlaneNotFoundException;
}
