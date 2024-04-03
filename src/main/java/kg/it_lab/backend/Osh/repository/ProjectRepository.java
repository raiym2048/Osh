package kg.it_lab.backend.Osh.repository;

import jakarta.transaction.Transactional;
import kg.it_lab.backend.Osh.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasAuthenticationCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByName(String name);
    @Transactional
    void deleteByName(String name);
}
