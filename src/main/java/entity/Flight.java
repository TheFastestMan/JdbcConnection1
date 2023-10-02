package entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight")
public class Flight implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flight_no")
    private String flightNo;
    @Column(name = "departure_date")
    private LocalDateTime departureDate;
    @Column(name = "departure_airport_code")
    private String departureAirportCode;
    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;
    @Column(name = "arrival_airport_code")
    private String arrivalAirportCode;
    @Column(name = "aircraft_id")
    private Integer aircraftId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FlightStatus status;

}
