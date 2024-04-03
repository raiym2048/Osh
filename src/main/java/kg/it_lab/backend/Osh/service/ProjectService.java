package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.project.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> all();
}
