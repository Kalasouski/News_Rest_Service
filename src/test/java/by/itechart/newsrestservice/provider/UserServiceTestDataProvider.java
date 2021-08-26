package by.itechart.newsrestservice.provider;

import by.itechart.newsrestservice.dto.RegistrationRequestDto;
import by.itechart.newsrestservice.entity.User;

import java.util.List;

public class UserServiceTestDataProvider {

    public static final Long EXISTING_ID = 1L;
    public static final Long NOT_EXISTING_ID = 22L;
    public static final String EXISTING_USERNAME = "Username";
    public static final String NOT_EXISTING_USERNAME = "ExistingUsername";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String DEFAULT_FIRST_NAME = "FirstName";
    private static final String DEFAULT_LAST_NAME = "LastName";

    public static final User EXPECTED_USER = generateUser();
    public static final List<User> EXPECTED_USER_LIST = generateUserList();
    public static final RegistrationRequestDto VALID_REGISTRATION_DTO = generateValidRequestDto();
    public static final RegistrationRequestDto INVALID_REGISTRATION_DTO = generateInvalidRequestDto();

    private static User generateUser() {
        return User
                .builder()
                .id(EXISTING_ID)
                .username(EXISTING_USERNAME)
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .build();
    }

    private static List<User> generateUserList() {
        return List.of(EXPECTED_USER);
    }

    private static RegistrationRequestDto generateValidRequestDto() {
        return RegistrationRequestDto
                .builder()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .username(NOT_EXISTING_USERNAME)
                .password(DEFAULT_PASSWORD)
                .build();
    }

    private static RegistrationRequestDto generateInvalidRequestDto() {
        return RegistrationRequestDto
                .builder()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .username(EXISTING_USERNAME)
                .password(DEFAULT_PASSWORD)
                .build();
    }
}
