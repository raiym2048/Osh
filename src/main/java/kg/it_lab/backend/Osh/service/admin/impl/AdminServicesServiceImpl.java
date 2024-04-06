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

    @Override
    public void addService(ServicesRequest servicesRequest) {
        if (servicesRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the service can't be empty");
        }
        if (servicesRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the service can't be empty");
        }
        if (servicesRepository.findByName(servicesRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Service with name " + servicesRequest.getName() + " already exist!");
        }

        var service = new Services();
        servicesRepository.save(servicesMapper.toDtoService(service, servicesRequest));

    }

    @Override
    public void updateService(Long id, ServicesRequest servicesRequest) {
        Optional<Services> service = servicesRepository.findById(id);
        if (servicesRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the service can't be empty");
        }
        if (servicesRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the service can't be empty");
        }
        if (service.isEmpty()) {
            throw new NotFoundException( "Service with this id wasn't found", HttpStatus.NOT_FOUND);
        }
        if (servicesRepository.findByName(servicesRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Service with name " + servicesRequest.getName() + " already exist!");
        }
        servicesRepository.save(servicesMapper.toDtoService(service.get(), servicesRequest));
    }

    @Override
    public void deleteService(Long id) {
        Optional<Services> service = servicesRepository.findById(id);
        if (service.isEmpty()) {
            throw new NotFoundException("Service with this id wasn't found", HttpStatus.NOT_FOUND);
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
            throw new NotFoundException("Services with this id" + serviceId + "not found" ,HttpStatus.NOT_FOUND );
        }
        Optional<Image>image = imageRepository.findById(imageId);
        if(image.isEmpty()){
            throw new NotFoundException("Image with id " + imageId + " not found" , HttpStatus.NOT_FOUND);
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        List<Image> images = new ArrayList<>();
        if(service.get().getImages() != null)
            images=service.get().getImages();
        images.add(image.get());
        service.get().setImages(images);
        servicesRepository.save(service.get());

    }
}
