package entity;

import lombok.*;
import util.ConnectionManager;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Ticket {
    private Long id;
    private String passportNo;
    private String passengerName;
    private Long flightId;
    private String seatNo;
    private BigDecimal cost;


}
