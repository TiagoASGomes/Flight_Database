package academy.mindera.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fName;
    private String email;
    private String phone;
    private String seatNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    private Flight flight;
    @ManyToOne(fetch = FetchType.EAGER)
    private Price price;
}
