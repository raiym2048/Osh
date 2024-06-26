package kg.it_lab.backend.Osh.repository;

import jakarta.transaction.Transactional;
import kg.it_lab.backend.Osh.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String fileName);

    @Transactional
    void deleteByName(String fileName);
}
