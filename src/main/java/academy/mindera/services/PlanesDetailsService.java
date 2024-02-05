package academy.mindera.services;

import academy.mindera.exceptions.PlaneNotFoundException;
import academy.mindera.models.Planes;
import academy.mindera.repositories.PlanesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class PlanesDetailsService implements PlanesDetailsServiceI {

    @Inject
    PlanesRepository planesDetailsRepository;

    @Override
    public void savePlane(Planes plane){
        planesDetailsRepository.persist(plane);
    }

    @Override
    public List<Planes> findAllPlanes(){
        return planesDetailsRepository.listAll();
    }

    @Override
    public Planes findPlaneById(long id) throws PlaneNotFoundException {
        return planesDetailsRepository.findByIdOptional(id).orElseThrow(() -> new PlaneNotFoundException("Plane with ID " + id + " not found."));
    }

    @Override
    public void deletePlane(long id){
        planesDetailsRepository.deleteById(id);
    }

    @Override
    public void updatePlane(Planes plane) {

        Planes existingPlane = planesDetailsRepository.findById(plane.getId());
        if (existingPlane != null){
            existingPlane.setPeopleCapacity(plane.getPeopleCapacity());
            existingPlane.setLuggageCapacity(plane.getLuggageCapacity());
            existingPlane.setCompanyOwner(plane.getCompanyOwner());
            existingPlane.setModelName(plane.getModelName());

            planesDetailsRepository.persist(existingPlane);
        }
    }
}
