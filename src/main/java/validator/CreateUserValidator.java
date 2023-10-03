package validator;

import dto.UserDto;
import entity.Gender;
import entity.Role;
import lombok.NoArgsConstructor;
import util.LocalDateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<UserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(UserDto userDto) {
        var validationResult = new ValidationResult();

        if (!LocalDateFormatter.isValid(String.valueOf(userDto.getBirthday()))) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if (Role.find(userDto.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        if (Gender.find(userDto.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        return validationResult;
    }

}
