package mapper;

import dto.UserDto;
import entity.Gender;
import entity.Role;
import entity.User;
import util.LocalDateFormatter;

public class CreateUserMapper implements Mapper<User, UserDto> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    private CreateUserMapper() {
    }

    @Override
    public User mapFrom(UserDto object) {
        User.UserBuilder userBuilder = User.builder()
                .name(object.getName())
                .birthday(LocalDateFormatter.format(String.valueOf(object.getBirthday()))) // Directly using getBirthday()
                .email(object.getEmail())
                .password(object.getPassword());

        try {
            userBuilder.gender(Gender.valueOf(object.getGender()));
        } catch (IllegalArgumentException e) {
            throw new MappingException("Invalid gender value: " + object.getGender(), e);
        }

        try {
            userBuilder.role(Role.valueOf(object.getRole()));
        } catch (IllegalArgumentException e) {
            throw new MappingException("Invalid role value: " + object.getRole(), e);
        }

        return userBuilder.build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }

    public static class MappingException extends RuntimeException {
        public MappingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
