package kg.it_lab.backend.Osh.dto.sponsorship;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorshipResponse {
    private String company;
    private String inn;
    private String address;
    private String paymentAccount;
    private String bank;
    private String bic;
    private String director;
}
