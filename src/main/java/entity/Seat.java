package entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Seat {
    private Aircraft aircraft_id;
    private String seatNo;
}
