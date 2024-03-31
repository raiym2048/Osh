package kg.it_lab.backend.Osh.dto.user;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String password ;
    private String role;
}
