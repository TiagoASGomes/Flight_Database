package academy.mindera.converters;

import academy.mindera.dto.flight.CreateFlightDTO;
import academy.mindera.dto.flight.GetFlightDto;
import academy.mindera.models.Flight;
import academy.mindera.models.Plane;
import academy.mindera.models.Price;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class FlightConverter {

    @Inject
    private PlaneConverter planeConverter;
    @Inject
    private PriceConverter priceConverter;

    public GetFlightDto fromEntityToGetDto(Flight flight) {
        return new GetFlightDto(
                flight.getId(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDuration(),
                flight.getDateOfFlight().toString(),
                flight.getAvailableSeats(),
                planeConverter.fromEntityToGetDto(flight.getPlane()),
                priceConverter.fromEntityListToGetDtoList(flight.getPrices().stream().toList())

        );
    }

    public List<GetFlightDto> fromEntityListToGetDtoList(List<Flight> flights) {
        return flights.stream()
                .map(this::fromEntityToGetDto)
                .toList();
    }

    public Flight fromCreateDtoToEntity(CreateFlightDTO createFlightDto, Plane plane, Set<Price> prices) {
        return Flight.builder()
                .origin(createFlightDto.origin())
                .destination(createFlightDto.destination())
                .duration(createFlightDto.duration())
                .dateOfFlight(createFlightDto.dateOfFlight())
                .plane(plane)
                .prices(prices)
                .build();
    }
}
