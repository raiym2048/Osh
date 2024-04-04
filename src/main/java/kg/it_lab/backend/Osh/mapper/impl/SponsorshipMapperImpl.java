package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipResponse;
import kg.it_lab.backend.Osh.entities.Services;
import kg.it_lab.backend.Osh.entities.Sponsorship;
import kg.it_lab.backend.Osh.mapper.SponsorshipMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SponsorshipMapperImpl implements SponsorshipMapper {
    @Override
    public SponsorshipResponse toDto(Sponsorship sponsorship) {
        SponsorshipResponse sponsorshipResponse = new SponsorshipResponse();
        sponsorshipResponse.setInn(sponsorship.getInn());
        sponsorshipResponse.setBank(sponsorship.getBank());
        sponsorshipResponse.setBic(sponsorship.getBic());
        sponsorshipResponse.setCompany(sponsorship.getCompany());
        sponsorshipResponse.setAddress(sponsorship.getAddress());
        sponsorshipResponse.setDirector(sponsorship.getDirector());
        return sponsorshipResponse;
    }

    @Override
    public List<SponsorshipResponse> toDtoS(List<Sponsorship> sponsorships) {
        List<SponsorshipResponse> sponsorshipResponses = new ArrayList<>();
        for (Sponsorship sponsorship: sponsorships){
            sponsorshipResponses.add(toDto(sponsorship));
        }
        return sponsorshipResponses;
    }

    @Override
    public Sponsorship toDtoSponsorship(Sponsorship sponsorship, SponsorshipRequest sponsorshipRequest) {
        sponsorship.setAddress(sponsorshipRequest.getAddress());
        sponsorship.setBank(sponsorshipRequest.getBank());
        sponsorship.setInn(sponsorshipRequest.getInn());
        sponsorship.setDirector(sponsorshipRequest.getDirector());
        sponsorship.setCompany(sponsorshipRequest.getCompany());
        sponsorship.setPaymentAccount(sponsorshipRequest.getPaymentAccount());
        return sponsorship;
    }
}
