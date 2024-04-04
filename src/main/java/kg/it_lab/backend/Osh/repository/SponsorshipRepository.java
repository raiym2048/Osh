package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Sponsorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Long> {
}
