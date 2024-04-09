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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;

    @Override
    public void addSponsorship(SponsorshipRequest sponsorshipRequest) {
        if(sponsorshipRequest.getInn().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("inn.empty", null, LocaleContextHolder.getLocale()));
        }
        if(sponsorshipRequest.getBank().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("bank.empty", null, LocaleContextHolder.getLocale()));
        }
        if(sponsorshipRequest.getAddress().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("address.empty", null, LocaleContextHolder.getLocale()));
        }
        if(sponsorshipRequest.getPaymentAccount().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("payment.empty", null, LocaleContextHolder.getLocale()));
        }
        if(sponsorshipRequest.getCompany().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("company.empty", null, LocaleContextHolder.getLocale()));
        }
        if(sponsorshipRequest.getDirector().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("director.empty", null, LocaleContextHolder.getLocale()));
        }
        Sponsorship sponsorship = new Sponsorship();
        sponsorshipRepository.save(sponsorshipMapper.toDtoSponsorship(sponsorship ,sponsorshipRequest));
    }

    @Override
    public void updateSponsorship(Long id, SponsorshipRequest sponsorshipRequest) {
        Optional<Sponsorship> sponsorship = sponsorshipRepository.findById(id);
        if(sponsorship.isEmpty()){
            throw new NotFoundException(messageSource.getMessage("sponsorship.notfound", null, LocaleContextHolder.getLocale()),HttpStatus.NOT_FOUND);
        }
        if(sponsorshipRequest.getInn().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("inn.empty", null, LocaleContextHolder.getLocale()));
        }
        if(sponsorshipRequest.getBank().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("bank.empty", null, LocaleContextHolder.getLocale()));
        }
        if(sponsorshipRequest.getAddress().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("address.empty", null, LocaleContextHolder.getLocale()));
        }
        if(sponsorshipRequest.getPaymentAccount().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("payment.empty", null, LocaleContextHolder.getLocale()));
        }
        if(sponsorshipRequest.getCompany().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("company.empty", null, LocaleContextHolder.getLocale()));
        }
        if(sponsorshipRequest.getDirector().isEmpty()){
            throw new BadRequestException(messageSource.getMessage("director.empty", null, LocaleContextHolder.getLocale()));
        }
        sponsorshipRepository.save(sponsorshipMapper.toDtoSponsorship(sponsorship.get() , sponsorshipRequest));
    }


    @Override
    public void deleteSponsorship(Long id) {
        Optional<Sponsorship> sponsorship = sponsorshipRepository.findById(id);
        if(sponsorship.isEmpty()){
            throw new NotFoundException(messageSource.getMessage("sponsorship.notfound", null, LocaleContextHolder.getLocale()),HttpStatus.NOT_FOUND);
        }
        sponsorshipRepository.deleteById(id);
    }
}
