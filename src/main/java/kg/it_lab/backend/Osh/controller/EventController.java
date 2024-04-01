package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.event.EventDetailResponse;
import kg.it_lab.backend.Osh.dto.event.EventResponse;
import kg.it_lab.backend.Osh.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("/all")
    public List<EventResponse> all(){
        return eventService.all();
    }

    @GetMapping("/{id}")
    public EventDetailResponse detail(@PathVariable Long id){
        return eventService.detail(id);
    }

}
