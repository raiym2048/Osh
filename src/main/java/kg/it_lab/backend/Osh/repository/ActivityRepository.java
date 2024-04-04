package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Activity;
import kg.it_lab.backend.Osh.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByName(String name);

    boolean existsByImage(Image image);
}
