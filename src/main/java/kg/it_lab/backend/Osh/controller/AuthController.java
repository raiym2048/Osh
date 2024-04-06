package kg.it_lab.backend.Osh.controller;

import io.jsonwebtoken.ExpiredJwtException;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginRequest;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.service.AuthLoginService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthLoginService authService;
    private final MessageSource messageSource;

    @PostMapping("/register")
    public AuthLoginResponse register(@RequestBody AuthLoginRequest authLoginRequest) {
        return authService.register(authLoginRequest);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest authLoginRequest,
                                   @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        try {
            return new ResponseEntity<>(authService.login(authLoginRequest), HttpStatus.OK);
        } catch (ExpiredJwtException ex) {
            return new ResponseEntity<>(messageSource.getMessage("token.expired", null, LocaleContextHolder.getLocale()), HttpStatus.UNAUTHORIZED);
        }
    }
}
