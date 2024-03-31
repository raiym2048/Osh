package kg.it_lab.backend.Osh.dto.user;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String role;
    private String password;
}
