package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slogan;

    @Column(columnDefinition = "text")
    private String description;
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(referencedColumnName = "name")
    private Category category;
    @OneToOne
    @JoinColumn
    private Image image;
}
