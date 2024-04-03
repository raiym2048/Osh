package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.project.ProjectResponse;
import kg.it_lab.backend.Osh.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/all")
    public List<ProjectResponse> all(){
        return projectService.all();
    }
}
