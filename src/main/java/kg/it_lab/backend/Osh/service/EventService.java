package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.event.EventDetailResponse;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.event.EventResponse;

import java.util.List;

public interface EventService {


    List<EventResponse> all();

    EventDetailResponse detail(Long id);

    void attachImageToEvent(String eventName, String imageName);
}
