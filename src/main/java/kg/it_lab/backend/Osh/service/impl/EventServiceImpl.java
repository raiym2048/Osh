package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.event.EventDetailResponse;
import kg.it_lab.backend.Osh.dto.event.EventResponse;
import kg.it_lab.backend.Osh.entities.Event;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.EventMapper;
import kg.it_lab.backend.Osh.repository.EventRepository;
import kg.it_lab.backend.Osh.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    @Override
    public List<EventResponse> all() {
        return eventMapper.toDtos(eventRepository.findAll());
    }

    @Override
    public EventDetailResponse detail(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isEmpty())
            throw new NotFoundException("Event with id: " + id + " - doesn't exist!", HttpStatus.NOT_FOUND);
        return eventMapper.toDetailDto(event.get());
    }

}
