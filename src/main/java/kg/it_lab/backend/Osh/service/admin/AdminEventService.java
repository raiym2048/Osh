package kg.it_lab.backend.Osh.service.admin;

import kg.it_lab.backend.Osh.dto.event.EventRequest;

public interface AdminEventService {
    void addEvent(EventRequest eventRequest, Long imageId);

    void updateEvent(Long id, EventRequest eventRequest, Long imageId);

    void deleteEvent(Long id);
}
