package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.entities.Activity;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.ImageService;
import kg.it_lab.backend.Osh.service.admin.AdminActivityService;
import kg.it_lab.backend.Osh.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminActivityServiceImpl implements AdminActivityService {
    private final ImageRepository imageRepository;
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final ImageService imageService;
    private final AdminService adminService;

    @Override
    public void addActivity(ActivityRequest activityRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        if (activityRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the activity can't be empty");
        }
        if (activityRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the activity can't be empty");
        }
        if (activityRepository.findByName(activityRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Activity with name " + activityRequest.getName() + " already exist!");
        }
        Activity activity = new Activity();
        activityRepository.save(activityMapper.toDtoActivity(activity, activityRequest,  image.get()));
    }

    @Override
    public void updateActivity(Long id, ActivityRequest activityRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this name not found", HttpStatus.NOT_FOUND);
        }
        if((adminService.imageChecker(image.get()) == 1 && !activityRepository.existsByImage(image.get())) || adminService.imageChecker(image.get()) > 1)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        Optional<Activity> activity = activityRepository.findById(id);
        if (activityRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the activity can't be empty");
        }
        if (activityRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the activity can't be empty");
        }
        if (activity.isEmpty()) {
            throw new NotFoundException("Activity with this ID wasn't found", HttpStatus.NOT_FOUND);
        }
        if (activityRepository.findByName(activityRequest.getName()).isPresent()) {
            throw new BadRequestException("Title of activity with this name already exist");
        }
        activityRepository.save(activityMapper.toDtoActivity(activity.get(),activityRequest, image.get()));
    }

    @Override
    public void deleteActivity(Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new NotFoundException("Activity with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        int cnt = 0;
        if(activity.get().getImage() != null){
            cnt = adminService.imageChecker(activity.get().getImage());
        }

        activityRepository.deleteById(id);
        if(cnt == 1)imageService.deleteFile(activity.get().getImage().getId());
//        imageRepository.deleteByName(activity.get().getImage().getName());


    }
}
