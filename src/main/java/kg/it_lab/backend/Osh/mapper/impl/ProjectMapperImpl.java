package kg.it_lab.backend.Osh.mapper.impl;
import kg.it_lab.backend.Osh.dto.project.ProjectDetailResponse;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectResponse;
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
        projectResponse.setName(projectResponse.getName());
        projectResponse.setDescription(projectResponse.getDescription());
        projectResponse.setSubtopic(projectResponse.getSubtopic());
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

    @Override
    public ProjectDetailResponse toDetailDto(Project project) {
        return null;
    }
}
