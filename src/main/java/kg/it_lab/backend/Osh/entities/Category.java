package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany
    private List<Event> events;

    @OneToMany
    private List<News> news;
}
