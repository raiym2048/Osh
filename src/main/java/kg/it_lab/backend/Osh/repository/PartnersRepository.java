package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Partners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnersRepository extends JpaRepository<Partners, Long>{
}
