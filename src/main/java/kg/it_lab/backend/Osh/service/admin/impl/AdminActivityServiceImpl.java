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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;

    @Override
    public void addActivity(ActivityRequest activityRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("image.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException(messageSource.getMessage("image.already.in.use", null, LocaleContextHolder.getLocale()));
        if (activityRequest.getName().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("title.not.empty", null, LocaleContextHolder.getLocale()));
        }
        if (activityRequest.getDescription().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("content.not.empty", null, LocaleContextHolder.getLocale()));
        }
        if (activityRepository.findByName(activityRequest.getName()).isPresent()) {
            throw new BadCredentialsException(messageSource.getMessage("activity.already.in.use", null, LocaleContextHolder.getLocale()));
        }
        Activity activity = new Activity();
        activityRepository.save(activityMapper.toDtoActivity(activity, activityRequest,  image.get()));
    }

    @Override
    public void updateActivity(Long id, ActivityRequest activityRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("image.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
        }
        if((adminService.imageChecker(image.get()) == 1 && !activityRepository.existsByImage(image.get())) || adminService.imageChecker(image.get()) > 1)
            throw new BadRequestException(messageSource.getMessage("image.already.in.use", null, LocaleContextHolder.getLocale()));
        Optional<Activity> activity = activityRepository.findById(id);
        if (activityRequest.getName().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("title.not.empty", null, LocaleContextHolder.getLocale()));
        }
        if (activityRequest.getDescription().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("content.not.empty", null, LocaleContextHolder.getLocale()));
        }
        if (activity.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("activity.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
        }
        if (activityRepository.findByName(activityRequest.getName()).isPresent()) {
            throw new BadRequestException(messageSource.getMessage("activity.already.in.use", null, LocaleContextHolder.getLocale()));
        }
        activityRepository.save(activityMapper.toDtoActivity(activity.get(),activityRequest, image.get()));
    }

    @Override
    public void deleteActivity(Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("activity.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
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
