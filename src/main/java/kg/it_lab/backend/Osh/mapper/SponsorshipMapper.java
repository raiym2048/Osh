package kg.it_lab.backend.Osh.mapper;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipResponse;
import kg.it_lab.backend.Osh.entities.Services;
import kg.it_lab.backend.Osh.entities.Sponsorship;

import java.util.List;

public interface SponsorshipMapper {
    SponsorshipResponse toDto(Sponsorship sponsorship);
    List<SponsorshipResponse> toDtoS(List<Sponsorship> sponsorships);
    Sponsorship toDtoSponsorship(Sponsorship sponsorship, SponsorshipRequest sponsorshipRequest);
}
