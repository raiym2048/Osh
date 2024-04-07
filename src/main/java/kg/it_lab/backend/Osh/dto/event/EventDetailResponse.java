package kg.it_lab.backend.Osh.dto.event;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventDetailResponse {
    private Long id;
    private String imagePath;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private TimerResponse timer;
}
