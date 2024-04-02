package kg.it_lab.backend.Osh.dto.activity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityRequest {
    private Integer year;
    private String name;
    private String description;
}
