package kg.it_lab.backend.Osh.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditorPasswordRequest {
    private String password1;
    private String password2;
}
