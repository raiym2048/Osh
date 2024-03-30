package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kg.it_lab.backend.Osh.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password ;
    private Role role;

}
