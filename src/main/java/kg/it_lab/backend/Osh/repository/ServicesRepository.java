package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {
    Optional<Services> findByName(String name);

}
