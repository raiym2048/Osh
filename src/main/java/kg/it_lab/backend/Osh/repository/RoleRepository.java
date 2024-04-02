package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role , Long> {
    Optional<Role> findByName(String name);
}
