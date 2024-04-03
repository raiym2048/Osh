package kg.it_lab.backend.Osh.repository;

import jakarta.transaction.Transactional;
import kg.it_lab.backend.Osh.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByName(String name);


}
