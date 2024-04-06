package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Hub;
import kg.it_lab.backend.Osh.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HubRepository extends JpaRepository<Hub, Long>{
    Optional<Hub> findByName(String name);
}
