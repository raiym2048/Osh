package kg.it_lab.backend.Osh.service.admin;

import kg.it_lab.backend.Osh.dto.project.ProjectRequest;

public interface AdminProjectService {
    void addProject(ProjectRequest projectRequest);

    void updateProject(Long id, ProjectRequest projectRequest);

    void deleteProject(Long id);

    void attachImageToProject(Long projectId, Long imageId);
}
