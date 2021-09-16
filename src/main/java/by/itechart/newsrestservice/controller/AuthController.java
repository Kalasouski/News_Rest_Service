package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.AuthenticationRequestDto;
import by.itechart.newsrestservice.dto.AuthenticationResponseDto;
import by.itechart.newsrestservice.dto.RefreshTokenRequestDto;
import by.itechart.newsrestservice.dto.RefreshTokenResponseDto;
import by.itechart.newsrestservice.entity.RefreshToken;
import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.security.JwtTokenProvider;
import by.itechart.newsrestservice.security.UserDetailsImpl;
import by.itechart.newsrestservice.security.exception.RefreshTokenException;
import by.itechart.newsrestservice.service.RefreshTokenService;
import by.itechart.newsrestservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.findByUsername(username);
            String token = jwtTokenProvider.createToken(username);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
            return new ResponseEntity<>(new AuthenticationResponseDto(token, refreshToken.getToken(), username), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<Object> refreshToken(@RequestBody RefreshTokenRequestDto request) {
        try {
            String requestRefreshToken = request.getRefreshToken();
            RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken);
            refreshTokenService.verifyExpiration(refreshToken);
            String accessToken = jwtTokenProvider.createToken(refreshToken.getUser().getUsername());
            return ResponseEntity.ok(new RefreshTokenResponseDto(accessToken, requestRefreshToken));
        } catch (Exception e) {
            throw new RefreshTokenException(request.getRefreshToken(), "Refresh token has expired or invalid!");
        }
    }

    @ExceptionHandler(RefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleRefreshTokenException(RefreshTokenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
