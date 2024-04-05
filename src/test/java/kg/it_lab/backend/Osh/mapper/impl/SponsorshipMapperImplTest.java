package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipResponse;
import kg.it_lab.backend.Osh.entities.Sponsorship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SponsorshipMapperImplTest {

    @InjectMocks
    private SponsorshipMapperImpl sponsorshipMapper;

    private final Sponsorship sponsorship = new Sponsorship();

    @BeforeEach
    void setUp() {
        sponsorship.setCompany("company");
        sponsorship.setInn("inn");
        sponsorship.setAddress("address");
        sponsorship.setPaymentAccount("payment");
        sponsorship.setBank("bank");
        sponsorship.setBic("bic");
        sponsorship.setDirector("director");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldReturnSponsorShipResponseToDto() {
        SponsorshipResponse responseResult = sponsorshipMapper.toDto(sponsorship);

        SponsorshipResponse expectedResponse = new SponsorshipResponse();
        expectedResponse.setCompany("company");
        expectedResponse.setInn("inn");
        expectedResponse.setAddress("address");
        expectedResponse.setPaymentAccount("payment");
        expectedResponse.setBank("bank");
        expectedResponse.setBic("bic");
        expectedResponse.setDirector("director");

        assertEquals(expectedResponse.getCompany(), responseResult.getCompany());
        assertEquals(expectedResponse.getInn(), responseResult.getInn());
        assertEquals(expectedResponse.getAddress(), responseResult.getAddress());
        assertEquals(expectedResponse.getPaymentAccount(), responseResult.getPaymentAccount());
        assertEquals(expectedResponse.getBank(), responseResult.getBank());
        assertEquals(expectedResponse.getBic(), responseResult.getBic());
        assertEquals(expectedResponse.getDirector(), responseResult.getDirector());
    }

    @Test
    void toDtoS() {
        Sponsorship sponsorship1 = new Sponsorship();
        sponsorship1.setCompany("company1");
        sponsorship1.setInn("inn1");
        sponsorship1.setAddress("address1");
        sponsorship1.setPaymentAccount("payment1");
        sponsorship1.setBank("bank1");
        sponsorship1.setBic("bic1");
        sponsorship1.setDirector("director1");

        List<Sponsorship> sponsorshipList = new ArrayList<>();
        sponsorshipList.add(sponsorship);
        sponsorshipList.add(sponsorship1);

        List<SponsorshipResponse> responseListResult = sponsorshipMapper.toDtoS(sponsorshipList);
        assertEquals(sponsorshipList.size(), responseListResult.size());
        assertEquals(responseListResult.get(1).getCompany(), sponsorshipList.get(1).getCompany());
        assertEquals(sponsorshipList.get(0).getDirector(), responseListResult.get(0).getDirector());
    }

    @Test
    void toDtoSponsorship() {
        SponsorshipRequest sponsorshipRequest = new SponsorshipRequest();
        sponsorshipRequest.setCompany("company");
        sponsorshipRequest.setInn("inn");
        sponsorshipRequest.setAddress("address");
        sponsorshipRequest.setPaymentAccount("payment");
        sponsorshipRequest.setBank("bank");
        sponsorshipRequest.setBic("bic");
        sponsorshipRequest.setDirector("director");

        Sponsorship sponsorshipResult = sponsorshipMapper.toDtoSponsorship(new Sponsorship(), sponsorshipRequest);

        assertEquals(sponsorshipResult.getDirector(), sponsorshipRequest.getDirector());
        assertEquals(sponsorshipResult.getCompany(), sponsorshipRequest.getCompany());
    }
}