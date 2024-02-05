package academy.mindera.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "planes")
public class Plane {
    @OneToMany(mappedBy = "plane")
    Set<Flight> flights;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int peopleCapacity;
    private int luggageCapacity;
    private String companyOwner;
    private String modelName;
    private boolean discontinued;
}



