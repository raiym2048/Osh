package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.event.EventDetailResponse;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.event.EventResponse;
import kg.it_lab.backend.Osh.dto.event.TimerResponse;
import kg.it_lab.backend.Osh.entities.Event;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.mapper.EventMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class EventMapperImpl implements EventMapper {


    @Override
    public EventResponse toDto(Event event) {
        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(event.getId());
        if(event.getImage() != null)
            eventResponse.setImagePath(event.getImage().getPath());
        eventResponse.setName(event.getName());
        eventResponse.setDateTime(event.getDateTime());
        eventResponse.setTimer(timerCount(event.getDateTime()));
        return eventResponse;
    }

    @Override
    public List<EventResponse> toDtos(List<Event> eventList) {
        List<EventResponse> eventResponses = new ArrayList<>();
        for (Event event: eventList){
            eventResponses.add(toDto(event));
        }
        return eventResponses;
    }

    @Override
    public Event toDtoEvent(Event event, EventRequest eventRequest , Image image) {

        event.setName(eventRequest.getName());
        event.setDescription(eventRequest.getDescription());
        event.setDateTime(LocalDateTime.of(eventRequest.getYear() , eventRequest.getMonth() , eventRequest.getDay() , eventRequest.getHour(),   eventRequest.getMinute() , eventRequest.getSeconds()));
        event.setImage(image);
        return event;
    }

    @Override
    public EventDetailResponse toDetailDto(Event event) {
        EventDetailResponse response = new EventDetailResponse();
        response.setId(event.getId());
        if(event.getImage() != null)
            response.setImagePath(event.getImage().getPath());
        response.setName(event.getName());
        response.setDateTime(event.getDateTime());
        response.setDescription(event.getDescription());
        response.setTimer(timerCount(event.getDateTime()));
        return response;
    }

    private TimerResponse timerCount(LocalDateTime dateTime) {
        TimerResponse response = new TimerResponse();
        LocalDateTime dateTimeNow = LocalDateTime.now();
        Duration duration = Duration.between(dateTimeNow, dateTime);

        Period period = Period.between(dateTimeNow.toLocalDate(), dateTime.toLocalDate());
        response.setMonths(period.getMonths());
        response.setDays(period.getDays());

        response.setHours((int) (duration.toHours() % 24));
        response.setMinutes((int) (duration.toMinutes() % 60));
        response.setSeconds((int) (duration.getSeconds() % 60));
        return response;
    }
}
