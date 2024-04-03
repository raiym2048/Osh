package kg.it_lab.backend.Osh.dto.project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponse {
    private Long id;
    private String imagePath;
    private String description;
    private String name;
    private String subtopic;
}
