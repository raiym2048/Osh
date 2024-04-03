package kg.it_lab.backend.Osh.dto.service;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServicesResponse {
    private Long id;
    private List<String> imagePaths;
    private String name;
    private String subtopic;
    private String description; //todo text there
}
