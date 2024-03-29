package by.itechart.newsrestservice.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private String accessToken;
    private String refreshToken;
    private String username;

    public AuthenticationResponseDto(String accessToken, String refreshToken, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
    }
}
