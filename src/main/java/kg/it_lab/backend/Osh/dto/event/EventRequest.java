package kg.it_lab.backend.Osh.dto.event;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventRequest {
    LocalDateTime dateTime = LocalDateTime.of(2024, 4, 1, 12, 30, 0);
    private String name;
    private String category;
    private String slogan;
    private String description;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private Integer seconds;
}
