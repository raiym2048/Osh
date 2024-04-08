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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminPartnersServiceImpl implements AdminPartnersService {
    private final ImageRepository imageRepository;
    private final AdminService adminService;
    private final PartnersRepository partnersRepository;

    @Override
    public void addPartners(Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");

        Partners partners = new Partners();
        partners.setImage(image.get());
        partnersRepository.save(partners);
    }


    @Override
    public void deletePartners(Long id) {
        Optional<Partners > partners = partnersRepository.findById(id);
        if(partners.isEmpty()){
            throw new BadRequestException("Partners with this id wasn't found");
        }
        partnersRepository.deleteById(id);
    }
}
