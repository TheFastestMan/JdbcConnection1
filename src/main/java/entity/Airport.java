package entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airport")
public class Airport {
    @Id
    private String code;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
}
