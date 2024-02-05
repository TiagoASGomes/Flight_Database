package academy.mindera.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Planes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int peopleCapacity;
    private int luggageCapacity;
    private String companyOwner;
    private String modelName;

    @OneToMany(mappedBy = "plane")
    List<Flights> flightsList = new ArrayList<>();

}
