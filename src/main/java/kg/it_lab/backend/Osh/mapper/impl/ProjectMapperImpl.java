package kg.it_lab.backend.Osh.mapper.impl;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectResponse;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.Project;
import kg.it_lab.backend.Osh.mapper.ProjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@AllArgsConstructor
public class ProjectMapperImpl implements ProjectMapper {
    @Override
    public ProjectResponse toDto(Project project) {
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(project.getId());
        projectResponse.setName(project.getName());
        projectResponse.setDescription(project.getDescription());
        projectResponse.setSubtopic(project.getSubtopic());
        ArrayList<String> paths = new ArrayList<>();
        for(Image image: project.getImages()){
            paths.add(image.getPath());
        }
        projectResponse.setImagePaths(paths);
        return projectResponse;
    }

    @Override
    public List<ProjectResponse> toDtoS(List<Project> projects) {
        List<ProjectResponse>projectResponses = new ArrayList<>();
        for(Project project : projects){
            projectResponses.add(toDto(project));
        }
        return projectResponses;
    }

    @Override
    public Project toDtoProject(Project project, ProjectRequest projectRequest) {
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setSubtopic(projectRequest.getSubtopic());
        return project;

    }


}
