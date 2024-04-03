package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.project.ProjectResponse;
import kg.it_lab.backend.Osh.mapper.ProjectMapper;
import kg.it_lab.backend.Osh.repository.ProjectRepository;
import kg.it_lab.backend.Osh.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    @Override
    public List<ProjectResponse> all() {
        return projectMapper.toDtoS(projectRepository.findAll());
    }
}
