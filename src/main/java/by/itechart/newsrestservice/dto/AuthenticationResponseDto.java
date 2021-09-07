package by.itechart.newsrestservice.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private String accessToken;
    private String username;

    public AuthenticationResponseDto(String accessToken, String username) {
        this.accessToken = accessToken;
        this.username = username;
    }
}
