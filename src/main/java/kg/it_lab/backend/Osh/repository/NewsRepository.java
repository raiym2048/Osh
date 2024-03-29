package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
