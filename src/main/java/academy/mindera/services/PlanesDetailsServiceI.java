package academy.mindera.services;

import academy.mindera.exceptions.PlaneNotFoundException;
import academy.mindera.models.Planes;

import java.util.List;

public interface PlanesDetailsServiceI {
    void savePlane(Planes plane);

    List<Planes> findAllPlanes();

    Planes findPlaneById(long id) throws PlaneNotFoundException;

    void deletePlane(long id);

    void updatePlane(Planes plane);
}
