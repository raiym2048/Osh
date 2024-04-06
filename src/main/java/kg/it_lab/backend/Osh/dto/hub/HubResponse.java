package kg.it_lab.backend.Osh.dto.hub;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HubResponse {
    private Long id;
    private List<String> imagePaths;
    private String name;
    private String description;
    private String subtopic;
}
