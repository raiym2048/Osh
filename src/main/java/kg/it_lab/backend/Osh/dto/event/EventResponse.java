package kg.it_lab.backend.Osh.dto.event;

import kg.it_lab.backend.Osh.dto.image.ImageResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventResponse {
    private Long id;
    private String imagePath;
    private String name;
    private String category;
    private String slogan;
    private LocalDateTime dateTime;
}
