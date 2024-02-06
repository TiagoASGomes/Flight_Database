package academy.mindera.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private String destination;
    private int duration;
    private LocalDateTime dateOfFlight;
    private int availableSeats;
    @ManyToOne(fetch = FetchType.EAGER)
    private Plane plane;
    @ManyToMany
    private Set<Price> prices;
    @OneToMany(mappedBy = "flight")
    private Set<Booking> bookings;
}
