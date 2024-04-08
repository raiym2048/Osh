package kg.it_lab.backend.Osh.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String role;
}
