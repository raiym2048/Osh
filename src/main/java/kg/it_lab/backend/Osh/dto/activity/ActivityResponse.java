package kg.it_lab.backend.Osh.dto.activity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityResponse {
    private Long id;
    private Integer year;
    private String imagePath;
    private String name;
    private String description;
}
