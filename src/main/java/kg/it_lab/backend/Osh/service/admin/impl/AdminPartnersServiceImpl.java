package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.Partners;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.repository.ImageRepository;
import kg.it_lab.backend.Osh.repository.PartnersRepository;
import kg.it_lab.backend.Osh.service.admin.AdminPartnersService;
import kg.it_lab.backend.Osh.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminPartnersServiceImpl implements AdminPartnersService {
    private final ImageRepository imageRepository;
    private final AdminService adminService;
    private final PartnersRepository partnersRepository;
    private final MessageSource messageSource;

    @Override
    public void addPartners(Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("image.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException(messageSource.getMessage("image.already.in.use", null, LocaleContextHolder.getLocale()));

        Partners partners = new Partners();
        partners.setImage(image.get());
        partnersRepository.save(partners);
    }


    @Override
    public void deletePartners(Long id) {
        Optional<Partners > partners = partnersRepository.findById(id);
        if(partners.isEmpty()){
            throw new BadRequestException(messageSource.getMessage("partners.notfound", null, LocaleContextHolder.getLocale()));
        }
        partnersRepository.deleteById(id);
    }
}
