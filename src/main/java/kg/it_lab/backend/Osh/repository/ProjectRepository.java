package kg.it_lab.backend.Osh.repository;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByName(String name);

    boolean existsByImages(List<Image> images);

    boolean existsByImagesContaining(Image image);
}
