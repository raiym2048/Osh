package kg.it_lab.backend.Osh.dto.project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {
    private String name;
    private String description;
    private String subtopic;
}
