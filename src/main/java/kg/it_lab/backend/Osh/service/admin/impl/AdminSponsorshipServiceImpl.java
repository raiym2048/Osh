package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.Sponsorship;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.admin.AdminService;
import kg.it_lab.backend.Osh.service.admin.AdminSponsorshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminSponsorshipServiceImpl implements AdminSponsorshipService {
    private final ImageRepository imageRepository;
    private final SponsorshipRepository sponsorshipRepository;
    private final SponsorshipMapper sponsorshipMapper;
    private final AdminService adminService;

    @Override
    public void addSponsorship(SponsorshipRequest sponsorshipRequest) {
        if(sponsorshipRequest.getInn().isEmpty()){
            throw new BadRequestException("INN can't be empty ");
        }
        if(sponsorshipRequest.getBank().isEmpty()){
            throw new BadRequestException("Bank can't be empty");
        }
        if(sponsorshipRequest.getAddress().isEmpty()){
            throw new BadRequestException("Address can't be empty ");
        }
        if(sponsorshipRequest.getPaymentAccount().isEmpty()){
            throw new BadRequestException("Payment account can't be empty");
        }
        if(sponsorshipRequest.getCompany().isEmpty()) {
            throw new BadRequestException("Company name can't be empty");
        }
        if(sponsorshipRequest.getDirector().isEmpty()){
            throw new BadRequestException("Director can't be empty");
        }
        Sponsorship sponsorship = new Sponsorship();
        sponsorshipRepository.save(sponsorshipMapper.toDtoSponsorship(sponsorship ,sponsorshipRequest));
    }

    @Override
    public void updateSponsorship(Long id, SponsorshipRequest sponsorshipRequest) {
        Optional<Sponsorship> sponsorship = sponsorshipRepository.findById(id);
        if(sponsorship.isEmpty()){
            throw new NotFoundException("Sponsorship with id" + id + "not found",HttpStatus.NOT_FOUND);
        }
        if(sponsorshipRequest.getInn().isEmpty()){
            throw new BadRequestException("INN can't be empty ");
        }
        if(sponsorshipRequest.getBank().isEmpty()){
            throw new BadRequestException("Bank can't be empty");
        }
        if(sponsorshipRequest.getAddress().isEmpty()){
            throw new BadRequestException("Address can't be empty ");
        }
        if(sponsorshipRequest.getPaymentAccount().isEmpty()){
            throw new BadRequestException("Payment account can't be empty");
        }
        if(sponsorshipRequest.getCompany().isEmpty()) {
            throw new BadRequestException("Company name can't be empty");
        }
        if(sponsorshipRequest.getDirector().isEmpty()){
            throw new BadRequestException("Director can't be empty");
        }
        sponsorshipRepository.save(sponsorshipMapper.toDtoSponsorship(sponsorship.get() , sponsorshipRequest));
    }


    @Override
    public void deleteSponsorship(Long id) {
        Optional<Sponsorship> sponsorship = sponsorshipRepository.findById(id);
        if(sponsorship.isEmpty()){
            throw new NotFoundException("Sponsorship with id" + id + "not found",HttpStatus.NOT_FOUND);
        }
        sponsorshipRepository.deleteById(id);
    }
}
