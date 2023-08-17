package entity;

import java.time.LocalDate;

public class Flight {
    private Long id;
    private String flightNo;
    private LocalDate departureDate;
    private String departureAirportCode;
    private LocalDate arrivalDate;
    private Integer aircraftId;
    private FlightStatus status;

}
