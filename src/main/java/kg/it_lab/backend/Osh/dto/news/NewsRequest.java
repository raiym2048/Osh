package kg.it_lab.backend.Osh.dto.news;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewsRequest {
    private String name;
    private String description ;
    private Long categoryId;
    private String slogan;
}
