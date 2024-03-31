package kg.it_lab.backend.Osh.dto.news.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginRequest {
    private String username;
    private String password;
}
