package academy.mindera.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String className;
    private int price;
    @ManyToMany(mappedBy = "prices")
    private Set<Flight> flights;
    @OneToMany(mappedBy = "price")
    private Set<Booking> bookings;
}
