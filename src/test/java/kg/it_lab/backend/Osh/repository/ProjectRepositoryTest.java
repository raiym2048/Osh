package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    private final Project project = new Project();

    @BeforeEach
    void setUp() {
        project.setName("Project");
        project.setSubtopic("It's subtopic");
        project.setDescription("It's description for project");
        project.setImages(null);
        projectRepository.save(project);
    }

    @AfterEach
    void tearDown() {
        projectRepository.deleteAll();
    }

    @Test
    void itShouldFindProjectByName() {
        Project findProjectByName = projectRepository.findByName(project.getName()).orElse(null);

        assertNotNull(projectRepository.findByName(project.getName()));
        assertEquals(project.getName(), findProjectByName.getName());
    }

    @Test
    void itShouldDeleteProjectByName() {
        projectRepository.deleteByName(project.getName());

        Project emptyProject = projectRepository.findByName(project.getName()).orElse(null);

        assertNull(emptyProject);
    }
}