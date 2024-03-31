package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.OptionalDataException;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

}
