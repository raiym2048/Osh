package kg.it_lab.backend.Osh.service.admin;

import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;

public interface AdminSponsorshipService {
    void addSponsorship(SponsorshipRequest sponsorshipRequest);

    void updateSponsorship(Long id, SponsorshipRequest sponsorshipRequest);

    void deleteSponsorship(Long id);
}
