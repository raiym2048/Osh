package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.Services;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.ImageService;
import kg.it_lab.backend.Osh.service.admin.AdminService;
import kg.it_lab.backend.Osh.service.admin.AdminServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServicesServiceImpl implements AdminServicesService {
    private final ImageRepository imageRepository;
    private final ServicesMapper servicesMapper;
    private final ServicesRepository servicesRepository;
    private final ImageService imageService;
    private final AdminService adminService;
    private final MessageSource messageSource;

    @Override
    public void addService(ServicesRequest servicesRequest) {
        if (servicesRequest.getName().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("title.not.empty", null, LocaleContextHolder.getLocale()));
        }
        if (servicesRequest.getDescription().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("content.not.empty", null, LocaleContextHolder.getLocale()));
        }
        if (servicesRepository.findByName(servicesRequest.getName()).isPresent()) {
            throw new BadCredentialsException(messageSource.getMessage("service.exist", null, LocaleContextHolder.getLocale()));
        }

        var service = new Services();
        servicesRepository.save(servicesMapper.toDtoService(service, servicesRequest));

    }

    @Override
    public void updateService(Long id, ServicesRequest servicesRequest) {
        Optional<Services> service = servicesRepository.findById(id);
        if (servicesRequest.getName().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("title.not.empty", null, LocaleContextHolder.getLocale()));
        }
        if (servicesRequest.getDescription().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("content.not.empty", null, LocaleContextHolder.getLocale()));
        }
        if (service.isEmpty()) {
            throw new NotFoundException( messageSource.getMessage("service.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
        }
        if (servicesRepository.findByName(servicesRequest.getName()).isPresent()) {
            throw new BadCredentialsException(messageSource.getMessage("service.exist", null, LocaleContextHolder.getLocale()));
        }
        servicesRepository.save(servicesMapper.toDtoService(service.get(), servicesRequest));
    }

    @Override
    public void deleteService(Long id) {
        Optional<Services> service = servicesRepository.findById(id);
        if (service.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("service.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
        }
        ArrayList<Long> ans = new ArrayList<>();
        if(service.get().getImages() != null)
            for(Image image: service.get().getImages())
                if(adminService.imageChecker(image) == 1)
                    ans.add(image.getId());

        servicesRepository.deleteById(id);
        for (Long a : ans)
            imageService.deleteFile(a);
//            imageRepository.deleteByName(s);
//        servicesRepository.deleteById(id);
    }

    @Override
    public void attachImageToService(Long serviceId, Long imageId) {
        Optional<Services> service = servicesRepository.findById(serviceId);
        if(service.isEmpty()){
            throw new NotFoundException(messageSource.getMessage("service.notfound", null, LocaleContextHolder.getLocale()),HttpStatus.NOT_FOUND );
        }
        Optional<Image>image = imageRepository.findById(imageId);
        if(image.isEmpty()){
            throw new NotFoundException(messageSource.getMessage("image.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException(messageSource.getMessage("image.already.in.use", null, LocaleContextHolder.getLocale()));
        List<Image> images = new ArrayList<>();
        if(service.get().getImages() != null)
            images=service.get().getImages();
        images.add(image.get());
        service.get().setImages(images);
        servicesRepository.save(service.get());

    }
}
