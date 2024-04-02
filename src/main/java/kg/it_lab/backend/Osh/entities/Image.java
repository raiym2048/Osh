package kg.it_lab.backend.Osh.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @ManyToOne
    private Project project;
    @ManyToOne
    private Service service;
}
