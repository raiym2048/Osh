package kg.it_lab.backend.Osh.dto.event;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventRequest {
    private String name;
    private Long categoryId ;
    private String slogan;
    private String description;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private Integer seconds;
}
