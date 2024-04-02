package kg.it_lab.backend.Osh.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginRequest {
    private String email;
    private String password;
}
