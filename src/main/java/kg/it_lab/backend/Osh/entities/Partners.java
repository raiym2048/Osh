package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Partners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Image image;
}
