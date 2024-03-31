package kg.it_lab.backend.Osh.dto.auth;

import lombok.Data;

@Data
public class AuthLoginRequest {
    private String email;
    private String password;
}
