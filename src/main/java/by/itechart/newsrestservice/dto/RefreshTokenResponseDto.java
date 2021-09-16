package by.itechart.newsrestservice.dto;

import lombok.Data;

@Data
public class RefreshTokenResponseDto {

    private String accessToken;
    private String refreshToken;

    public RefreshTokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
