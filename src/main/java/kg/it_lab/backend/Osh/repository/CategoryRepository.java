package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Category;
import kg.it_lab.backend.Osh.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Long> {
    Optional<Category> findById(Long id);
}