package kg.it_lab.backend.Osh.mapper;

import kg.it_lab.backend.Osh.dto.event.EventDetailResponse;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.event.EventResponse;

import kg.it_lab.backend.Osh.entities.Event;
import kg.it_lab.backend.Osh.entities.Image;


import java.util.List;

public interface EventMapper {
    EventResponse toDto(Event event);
    List<EventResponse> toDtos(List<Event> event);
    Event toDtoEvent(Event event , EventRequest eventRequest , Image image  );

    EventDetailResponse toDetailDto(Event event);
}
