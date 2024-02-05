package academy.mindera.services.implementation;

import academy.mindera.converters.PlaneConverter;
import academy.mindera.dto.plane.CreatePlaneDTO;
import academy.mindera.dto.plane.GetPlaneDTO;
import academy.mindera.exceptions.plane.PlaneNotFoundException;
import academy.mindera.models.Plane;
import academy.mindera.repositories.PlaneRepository;
import academy.mindera.services.interfaces.PlaneService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class PlaneServiceImpl implements PlaneService {
    private final int PAGE_SIZE = 10;

    @Inject
    private PlaneRepository planeRepository;
    @Inject
    private PlaneConverter planeConverter;

    @Override
    public List<GetPlaneDTO> findAllPlanes(int page) {
        return planeConverter.fromEntityListToGetDtoList(planeRepository.findAll().page(page, PAGE_SIZE).list());
    }

    @Override
    public GetPlaneDTO findPlaneById(Long id) throws PlaneNotFoundException {
        return planeConverter.fromEntityToGetDto(findById(id));
    }

    @Override
    public CreatePlaneDTO savePlane(CreatePlaneDTO plane) {
        planeRepository.persist(planeConverter.fromCreateDtoToEntity(plane));
        return plane;
    }

    @Override
    public GetPlaneDTO updatePlane(CreatePlaneDTO plane, Long id) throws PlaneNotFoundException {
        Plane dbPlane = findById(id);
        dbPlane.setPeopleCapacity(plane.peopleCapacity());
        dbPlane.setLuggageCapacity(plane.luggageCapacity());
        dbPlane.setCompanyOwner(plane.companyOwner());
        dbPlane.setModelName(plane.modelName());
        planeRepository.persist(dbPlane);
        return planeConverter.fromEntityToGetDto(dbPlane);
    }

    @Override
    public void deletePlane(Long id) throws PlaneNotFoundException {
        Plane plane = findById(id);
        plane.setDiscontinued(true);
        planeRepository.persist(plane);
    }

    @Override
    public Plane findById(Long id) throws PlaneNotFoundException {
        return planeRepository.findByIdOptional(id).orElseThrow(() -> new PlaneNotFoundException("Plane with ID " + id + " not found."));
    }
}
