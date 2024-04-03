package kg.it_lab.backend.Osh.repository;

import jakarta.transaction.Transactional;
import kg.it_lab.backend.Osh.entities.Project;
import kg.it_lab.backend.Osh.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByName(String name);
    @Transactional
    void deleteByName(String name);
}
