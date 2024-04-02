package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String subtopic;
    @Column(columnDefinition = "text")
    private String description;
    @OneToMany(mappedBy = "service")
    List<Image> images;
}
