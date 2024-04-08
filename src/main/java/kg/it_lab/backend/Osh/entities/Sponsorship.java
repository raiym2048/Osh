package kg.it_lab.backend.Osh.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Sponsorship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;
    private String inn;
    private String address;
    private String paymentAccount;
    private String bank;
    private String bic;
    private String director;

}
