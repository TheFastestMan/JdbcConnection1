package dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private LocalDate birthday;
    private String email;
    private String password;
    private String role;
    private String gender;
}
