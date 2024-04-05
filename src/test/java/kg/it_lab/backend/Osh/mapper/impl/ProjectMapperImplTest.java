package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectResponse;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.entities.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectMapperImplTest {

    @InjectMocks
    private ProjectMapperImpl projectMapper;


    private final Project project = new Project();
    private final List<Image> imageList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        Image image = new Image();
        image.setId(1L);
        image.setName("name");
        image.setPath("path");

        Image image1 = new Image();
        image1.setId(2L);
        image1.setName("name1");
        image1.setPath("path1");

        imageList.add(image);
        imageList.add(image1);

        project.setId(1L);
        project.setName("name");
        project.setSubtopic("subtopic");
        project.setDescription("description");
        project.setImages(imageList);
    }

    @Test
    void toDto() {
        ProjectResponse responseResult = projectMapper.toDto(project);

        assertEquals(project.getId(), responseResult.getId());
        assertEquals(project.getImages().get(0).getPath(), responseResult.getImagePaths().get(0));
        assertEquals(project.getName(), responseResult.getName());
    }

    @Test
    void toDtoS() {
        Project project1 = new Project();
        project1.setId(2L);
        project1.setName("name1");
        project1.setSubtopic("subtopic1");
        project1.setDescription("description1");
        project1.setImages(imageList);

        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        projectList.add(project1);

        List<ProjectResponse> responseResultList = projectMapper.toDtoS(projectList);

        assertEquals(projectList.size(), responseResultList.size());
        assertEquals(projectList.get(0).getId(), responseResultList.get(0).getId());
        assertEquals(projectList.get(1).getImages().get(0).getPath(), responseResultList.get(1).getImagePaths().get(0));
    }

    @Test
    void toDtoProject() {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("name");
        projectRequest.setDescription("description");
        projectRequest.setSubtopic("subtopic");

        Project projectResult = projectMapper.toDtoProject(new Project(), projectRequest);

        assertEquals(project.getName(), projectResult.getName());
        assertEquals(project.getSubtopic(), projectResult.getSubtopic());
        assertEquals(project.getDescription(), projectResult.getDescription());
    }
}