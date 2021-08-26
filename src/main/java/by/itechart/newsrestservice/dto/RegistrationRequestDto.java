package by.itechart.newsrestservice.dto;

import by.itechart.newsrestservice.entity.Role;
import by.itechart.newsrestservice.entity.Status;
import by.itechart.newsrestservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public User dtoToUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(Role.ROLE_USER);
        user.setStatus(Status.ACTIVE);
        return user;
    }
}
