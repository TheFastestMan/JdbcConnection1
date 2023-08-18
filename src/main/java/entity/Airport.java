package entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Airport {
    private String code;
    private String country;
    private String city;
}
