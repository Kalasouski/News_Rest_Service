package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static by.itechart.newsrestservice.provider.UserServiceTestDataProvider.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository mockedUserRepository;

    @Test
    public void whenFindByValidId_thenUserShouldBeFound() {
        when(mockedUserRepository.findById(EXISTING_ID)).thenReturn(Optional.of(EXPECTED_USER));

        User found = userService.findById(EXISTING_ID);
        assertEquals(EXPECTED_USER, found);
    }

    @Test
    public void whenFindByInvalidId_thenExceptionShouldBeThrown() {
        when(mockedUserRepository.findById(NOT_EXISTING_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> userService.findById(NOT_EXISTING_ID));
    }

    @Test
    public void whenFindByValidUsername_thenUserShouldBeFound() {
        when(mockedUserRepository.findByUsername(EXISTING_USERNAME)).thenReturn(EXPECTED_USER);

        User found = userService.findByUsername(EXISTING_USERNAME);
        assertEquals(EXPECTED_USER, found);
    }

    @Test
    public void whenFindByInvalidUsername_thenNullShouldBeReturned() {
        when(mockedUserRepository.findByUsername(NOT_EXISTING_USERNAME)).thenReturn(null);

        User found = userService.findByUsername(NOT_EXISTING_USERNAME);
        assertNull(found);
    }

    @Test
    public void whenFindAllUsers_thenListOfUsersShouldBeReturned() {
        when(mockedUserRepository.findAll()).thenReturn(EXPECTED_USER_LIST);

        List<User> found = userService.findAllUsers();
        assertEquals(EXPECTED_USER_LIST, found);
    }

    @Test
    public void whenAddExistingUser_thenFalseShouldBeReturned() {
        when(mockedUserRepository.findByUsername(INVALID_REGISTRATION_DTO.getUsername())).thenReturn(EXPECTED_USER);

        assertFalse(userService.addNewUser(INVALID_REGISTRATION_DTO));
    }

    @Test
    public void whenAddNotExistingUser_thenTrueShouldBeReturned() {
        when(mockedUserRepository.findByUsername(VALID_REGISTRATION_DTO.getUsername())).thenReturn(null);
        when(mockedUserRepository.save(EXPECTED_USER)).thenReturn(EXPECTED_USER);

        assertTrue(userService.addNewUser(VALID_REGISTRATION_DTO));
    }
}
