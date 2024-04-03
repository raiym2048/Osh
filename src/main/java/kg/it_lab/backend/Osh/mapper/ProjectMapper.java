package kg.it_lab.backend.Osh.mapper;

import kg.it_lab.backend.Osh.dto.project.ProjectDetailResponse;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectResponse;
import kg.it_lab.backend.Osh.entities.Project;

import java.util.List;

public interface ProjectMapper {
    ProjectResponse toDto(Project project);
    List<ProjectResponse> toDtoS(List<Project> projects);
    Project toDtoProject(Project project , ProjectRequest projectRequest);

}
