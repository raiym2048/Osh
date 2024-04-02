package kg.it_lab.backend.Osh.dto.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse {
    private Long id;
    private String name;
    private String subtopic;
    private String description; //todo text there
}
