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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminProjectServiceImpl implements AdminProjectService {

    private final ImageRepository imageRepository;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final ImageService imageService;
    private final AdminService adminService;
    @Override
    public void addProject(ProjectRequest projectRequest) {
        if (projectRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the project can't be empty");
        }
        if (projectRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the project can't be empty");
        }
        if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Project with name: " + projectRequest.getName() + " - already exist!");
        }
        Project project = new Project();
        projectRepository.save(projectMapper.toDtoProject(project, projectRequest));
    }

    @Override
    public void updateProject(Long id, ProjectRequest projectRequest) {
        Optional<Project> project = projectRepository.findById(id);
        if (projectRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the project can't be empty");
        }
        if (projectRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the project can't be empty");
        }
        if (project.isEmpty()) {
            throw new NotFoundException("Title of project with this id wasn't found", HttpStatus.NOT_FOUND);
        }
        if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Project with name: " + projectRequest.getName() + " - already exist!");
        }
        projectRepository.save(projectMapper.toDtoProject(project.get(), projectRequest));
    }

    @Override
    public void deleteProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new NotFoundException("Project with this id wasn't found", HttpStatus.NOT_FOUND);
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
            throw new NotFoundException("project with this id not found ", HttpStatus.NOT_FOUND);
        }
        Optional<Image>image =imageRepository.findById(imageId);
        if(image.isEmpty()){
            throw new NotFoundException("Image with id " + imageId + " not found" , HttpStatus.NOT_FOUND);
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        List<Image> images = new ArrayList<>();
        if(project.get().getImages() != null) images = project.get().getImages();
        images.add(image.get());
        project.get().setImages(images);
        projectRepository.save(project.get());
    }
}
