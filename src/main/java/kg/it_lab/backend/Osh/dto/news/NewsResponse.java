package kg.it_lab.backend.Osh.dto.news;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class NewsResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private String slogan;
    private String category;
}
