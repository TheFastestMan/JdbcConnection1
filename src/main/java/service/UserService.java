package service;

import dao.UserDao;

import dto.UserDto;
import exception.ValidationException;
import lombok.NoArgsConstructor;
import mapper.CreateUserMapper;

import validator.CreateUserValidator;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    public Long create(UserDto userDto) throws ValidationException {
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var user = createUserMapper.mapFrom(userDto);
        var result = userDao.save(user);
        return result.getId();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public boolean registerUser(UserDto userDto) {
        try {
            Long userId = create(userDto);
            return userId != null;
        } catch (ValidationException e) {
            return false;
        }
    }

}
