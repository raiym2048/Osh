package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipResponse;
import kg.it_lab.backend.Osh.mapper.SponsorshipMapper;
import kg.it_lab.backend.Osh.repository.SponsorshipRepository;
import kg.it_lab.backend.Osh.service.SponsorshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SponsorshipServiceImpl implements SponsorshipService {

    private final SponsorshipMapper sponsorshipMapper;
    private final SponsorshipRepository sponsorshipRepository;

    @Override
    public List<SponsorshipResponse> all() {
        return sponsorshipMapper.toDtoS(sponsorshipRepository.findAll());
    }
}
