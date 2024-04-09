package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.Project;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.ImageService;
import kg.it_lab.backend.Osh.service.admin.AdminProjectService;
import kg.it_lab.backend.Osh.service.admin.AdminService;
import kg.it_lab.backend.Osh.service.emailSender.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminProjectServiceImpl implements AdminProjectService {

    private final ImageRepository imageRepository;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final ImageService imageService;
    private final AdminService adminService;
    private final MessageSource messageSource;
    @Override
    public void addProject(ProjectRequest projectRequest) {
        if (projectRequest.getName().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("project.notfound", null, LocaleContextHolder.getLocale()));
        }
        if (projectRequest.getDescription().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("content.notfound", null, LocaleContextHolder.getLocale()));
        }
        if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
            throw new BadCredentialsException(messageSource.getMessage("project.exist", null, LocaleContextHolder.getLocale()));
        }
        Project project = new Project();
        projectRepository.save(projectMapper.toDtoProject(project, projectRequest));
    }

    @Override
    public void updateProject(Long id, ProjectRequest projectRequest) {
        Optional<Project> project = projectRepository.findById(id);
        if (projectRequest.getName().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("title.not.emtpy", null, LocaleContextHolder.getLocale()));
        }
        if (projectRequest.getDescription().isEmpty()) {
            throw new BadRequestException(messageSource.getMessage("content.not.empty", null, LocaleContextHolder.getLocale()));
        }
        if (project.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("project.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
        }
        if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
            throw new BadCredentialsException(messageSource.getMessage("project.exist", null, LocaleContextHolder.getLocale()));
        }
        projectRepository.save(projectMapper.toDtoProject(project.get(), projectRequest));
    }

    @Override
    public void deleteProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("project.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
        }
        ArrayList<Long> ans = new ArrayList<>();
        if(project.get().getImages() != null)
            for(Image image: project.get().getImages())
                if(adminService.imageChecker(image) == 1)
                    ans.add(image.getId());

        projectRepository.deleteById(id);
        for (Long a : ans)
            imageService.deleteFile(a);
//            imageRepository.deleteByName(s);
    }

    @Override
    public void attachImageToProject(Long projectId, Long imageId) {
        Optional<Project>project =projectRepository.findById(projectId);
        if(project.isEmpty()){
            throw new NotFoundException(messageSource.getMessage("project.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
        }
        Optional<Image>image =imageRepository.findById(imageId);
        if(image.isEmpty()){
            throw new NotFoundException(messageSource.getMessage("image.notfound", null, LocaleContextHolder.getLocale()) , HttpStatus.NOT_FOUND);
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException(messageSource.getMessage("image.already.in.use", null, LocaleContextHolder.getLocale()));
        List<Image> images = new ArrayList<>();
        if(project.get().getImages() != null) images = project.get().getImages();
        images.add(image.get());
        project.get().setImages(images);
        projectRepository.save(project.get());
    }
}
