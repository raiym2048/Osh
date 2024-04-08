package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.project.ProjectResponse;
import kg.it_lab.backend.Osh.entities.Project;
import kg.it_lab.backend.Osh.mapper.ProjectMapper;
import kg.it_lab.backend.Osh.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;

    private final Project project = new Project();
    private final ProjectResponse projectResponse = new ProjectResponse();

    @Test
    void all() {
        when(projectRepository.findAll()).thenReturn(List.of(project));
        when(projectMapper.toDtoS(anyList())).thenReturn(List.of(projectResponse));

        List<ProjectResponse> responseList = projectService.all();

        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertEquals(projectResponse, responseList.get(0));

        verify(projectRepository).findAll();
        verify(projectMapper).toDtoS(anyList());
    }
}