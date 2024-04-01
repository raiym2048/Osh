package kg.it_lab.backend.Osh.repository;

import jakarta.transaction.Transactional;
import kg.it_lab.backend.Osh.entities.Event;
import kg.it_lab.backend.Osh.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EventRepository extends JpaRepository<Event ,  Long> {
    Optional<Event> findByName(String name);
    @Transactional
    void deleteByName(String name);
}
