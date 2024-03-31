package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
