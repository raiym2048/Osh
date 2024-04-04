package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class Numbers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer businessProjectNumbers;
    private Integer socialProjectNumbers;
    private Integer projectNumbers;
    private Integer peopleNumbers;
    private Integer hubNumbers;
    private Integer newWorkPlaceNumbers;
}
