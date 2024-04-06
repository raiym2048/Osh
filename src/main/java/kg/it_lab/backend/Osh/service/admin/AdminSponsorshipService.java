package kg.it_lab.backend.Osh.service.admin;

import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;

public interface AdminSponsorshipService {
    void addSponsorship(SponsorshipRequest sponsorshipRequest, Long imageId);

    void updateSponsorship(Long id, SponsorshipRequest sponsorshipRequest, Long imageId);

    void deleteSponsorship(Long id);
}
