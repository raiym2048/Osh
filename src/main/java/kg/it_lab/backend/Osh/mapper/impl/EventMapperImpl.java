package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.event.EventResponse;
import kg.it_lab.backend.Osh.entities.Event;
import kg.it_lab.backend.Osh.mapper.EventMapper;
import kg.it_lab.backend.Osh.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class EventMapperImpl implements EventMapper {
    private final CategoryRepository categoryRepository;
    @Override
    public EventResponse toDto(Event event) {
        EventResponse eventResponse = new EventResponse();
        eventResponse.setName(event.getName());
        eventResponse.setCategory(event.getCategory().getName());
        eventResponse.setDescription(event.getDescription());
        eventResponse.setSlogan(event.getSlogan());
        eventResponse.setDateTime(event.getDateTime());
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
    public Event toDtoEvent(Event event, EventRequest eventRequest) {

        event.setName(eventRequest.getName());
        event.setDescription(eventRequest.getDescription());
        event.setCategory(categoryRepository.findById(eventRequest.getCategoryId()).get());
        event.setSlogan(eventRequest.getSlogan());
        event.setDateTime(LocalDateTime.of(eventRequest.getYear() , eventRequest.getMonth() , eventRequest.getDay() , eventRequest.getHour(),   eventRequest.getMinute() , eventRequest.getSeconds()));
        return event;
    }
}
