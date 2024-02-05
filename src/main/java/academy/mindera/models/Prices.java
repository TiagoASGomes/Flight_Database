package academy.mindera.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prices {

   @Id
   private long id;
   private String className;
   private int price;

   @ManyToMany
   @JoinTable(
           name="Flight_Prices",
           joinColumns = @JoinColumn(name = "pricesId"),
           inverseJoinColumns = @JoinColumn(name = "flightId")
   )
    private Set<Flights> flights;
}
