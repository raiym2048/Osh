package kg.it_lab.backend.Osh.controller;

import io.jsonwebtoken.ExpiredJwtException;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginRequest;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.service.AuthLoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthLoginService authService;

    @PostMapping("/register")
    public AuthLoginResponse register(@RequestBody AuthLoginRequest authLoginRequest) {
        return authService.register(authLoginRequest);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest authLoginRequest) {
        try {
            return new ResponseEntity<>(authService.login(authLoginRequest), HttpStatus.OK);
        } catch (ExpiredJwtException ex) {
            return new ResponseEntity<>("Token expired", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public String test() {
        return "It works";
    }
}
