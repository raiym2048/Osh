package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private String name;
    @Column(columnDefinition = "text")
    private String description;

    @OneToOne
    private Image image;
}
