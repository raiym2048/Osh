package kg.it_lab.backend.Osh.dto.project;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectResponse {
    private Long id;
    private List<String> imagePaths;
    private String description;
    private String name;
    private String subtopic;
}
