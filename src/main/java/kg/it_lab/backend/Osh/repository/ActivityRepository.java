package kg.it_lab.backend.Osh.repository;

import jakarta.transaction.Transactional;
import kg.it_lab.backend.Osh.entities.Activity;
import kg.it_lab.backend.Osh.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByName(String name);
    @Transactional
    void deleteByName(String name);
}
