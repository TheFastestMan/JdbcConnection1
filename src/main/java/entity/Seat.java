package entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @Column(name = "aircraft_id")
    private Aircraft aircraftId;
    @Id
    @Column(name = "seat_no")
    private String seatNo;
}
