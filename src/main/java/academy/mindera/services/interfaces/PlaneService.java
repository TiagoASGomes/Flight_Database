package academy.mindera.services.interfaces;

import academy.mindera.dto.plane.CreatePlaneDTO;
import academy.mindera.dto.plane.GetPlaneDTO;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.models.Plane;

import java.util.List;

public interface PlaneService {
    CreatePlaneDTO savePlane(CreatePlaneDTO plane);

    List<GetPlaneDTO> findAllPlanes(int page);

    GetPlaneDTO findPlaneById(Long id) throws PlaneNotFoundException;

    void deletePlane(Long id) throws PlaneNotFoundException;

    GetPlaneDTO updatePlane(CreatePlaneDTO plane, Long id) throws PlaneNotFoundException;

    Plane findById(Long id) throws PlaneNotFoundException;
}
