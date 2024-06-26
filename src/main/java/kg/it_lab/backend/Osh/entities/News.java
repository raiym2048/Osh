package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn
    private Image image;

}
