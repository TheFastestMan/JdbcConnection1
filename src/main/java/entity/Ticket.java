package entity;

import lombok.*;

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
    private Flight flight;
    private String seatNo;
    private BigDecimal cost;


}
