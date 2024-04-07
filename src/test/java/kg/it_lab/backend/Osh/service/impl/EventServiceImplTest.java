package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.event.EventDetailResponse;
import kg.it_lab.backend.Osh.dto.event.EventResponse;
import kg.it_lab.backend.Osh.entities.Event;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.EventMapper;
import kg.it_lab.backend.Osh.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventMapper eventMapper;

    private final Event event = new Event();
    private final EventResponse eventResponse = new EventResponse();
    private final EventDetailResponse detailResponse = new EventDetailResponse();
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void all() {
        when(eventRepository.findAll()).thenReturn(List.of(event));
        when(eventMapper.toDtos(anyList())).thenReturn(List.of(eventResponse));

        List<EventResponse> responseListResult = eventService.all();


        assertNotNull(responseListResult);
        assertEquals(eventResponse, responseListResult.get(0));
        assertFalse(responseListResult.isEmpty());

        verify(eventRepository).findAll();
        verify(eventMapper).toDtos(anyList());
    }

    @Test
    void detail() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(eventMapper.toDetailDto(any(Event.class))).thenReturn(detailResponse);

        EventDetailResponse responseResult = eventService.detail(anyLong());

        assertNotNull(responseResult);
        assertEquals(detailResponse, responseResult);

        verify(eventRepository).findById(anyLong());
        verify(eventMapper).toDetailDto(any(Event.class));
    }

    @Test
    void detailThrowException() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> eventService.detail(anyLong()));

        verify(eventRepository).findById(anyLong());
        verify(eventMapper, never()).toDetailDto(any(Event.class));
    }
}