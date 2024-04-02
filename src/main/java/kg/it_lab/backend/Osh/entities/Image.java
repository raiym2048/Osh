package kg.it_lab.backend.Osh.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(name = "path")
    private String path;
    @OneToOne(mappedBy = "image")
    private News news;
    @OneToOne(mappedBy = "image")
    private Event event;
    @OneToOne(mappedBy = "image")
    private Activity activity;
}
