package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.*;
import kg.it_lab.backend.Osh.enums.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dateOfBirth;
    private String town;
    private String comment;
    private String contacts;
    @Transient
    private int age;

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
