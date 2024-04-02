package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "category_db")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Event> events;
    @OneToMany(mappedBy = "category")
    private List<News> news;
}
