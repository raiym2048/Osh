package kg.it_lab.backend.Osh.dto.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimerResponse {
    private Integer months;
    private Integer days;
    private Integer hours;
    private Integer minutes;
    private Integer seconds;
}
