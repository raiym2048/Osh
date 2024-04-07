package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.event.EventResponse;
import kg.it_lab.backend.Osh.entities.Category;
import kg.it_lab.backend.Osh.entities.Event;
import kg.it_lab.backend.Osh.entities.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventMapperImplTest {

    @InjectMocks
    private EventMapperImpl eventMapper;

    @Mock
    private CategoryRepository categoryRepository;

    private final Event event = new Event();

    private final Category category = new Category();
    private final Image image = new Image();

    @BeforeEach
    void setUp() {
        category.setId(1L);
        category.setName("category");

        image.setId(1L);
        image.setName("image");
        image.setPath("path");

        event.setId(1L);
        event.setName("name");
        event.setSlogan("slogan");
        event.setDescription("description");
        event.setDateTime(LocalDateTime.now());
        event.setCategory(category);
        event.setImage(image);
    }

    @Test
    void toDto() {
        EventResponse responseResult = eventMapper.toDto(event);

        assertEquals(event.getId(), responseResult.getId());
        assertEquals(event.getImage().getPath(), responseResult.getImagePath());
        assertEquals(event.getName(), responseResult.getName());
        assertEquals(event.getCategory().getName(), responseResult.getCategory());
        assertEquals(event.getSlogan(), responseResult.getSlogan());
        assertEquals(event.getDateTime(), responseResult.getDateTime());
        // todo here should be a timer, or the timer shouldn't be
    }

    @Test
    void toDtos() {
        Category category1 = new Category();
        category1.setId(2L);
        category1.setName("category1");

        Image image1 = new Image();
        image1.setId(1L);
        image1.setName("image1");
        image1.setPath("path1");

        Event event1 = new Event();
        event1.setId(2L);
        event1.setName("name1");
        event1.setSlogan("slogan1");
        event1.setDescription("description1");
        event1.setDateTime(LocalDateTime.now());
        event1.setCategory(category1);
        event1.setImage(image1);

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        eventList.add(event1);

        List<EventResponse> responseListResult = eventMapper.toDtos(eventList);

        assertEquals(eventList.size(), responseListResult.size());
        assertEquals(eventList.get(0).getId(), responseListResult.get(0).getId());
        assertEquals(eventList.get(1).getImage().getPath(), responseListResult.get(1).getImagePath());
        assertEquals(eventList.get(0).getCategory().getName(), responseListResult.get(0).getCategory());
    }

    @Test
    void toDtoEvent() {
        EventRequest eventRequest = new EventRequest();
        eventRequest.setName("name");
        eventRequest.setCategoryId(1L);
        eventRequest.setSlogan("slogan");
        eventRequest.setDescription("description");
        eventRequest.setYear(2024);
        eventRequest.setMonth(5);
        eventRequest.setDay(22);
        eventRequest.setHour(12);
        eventRequest.setMinute(0);
        eventRequest.setSeconds(0);

        when(categoryRepository.findById(eventRequest.getCategoryId())).thenReturn(Optional.of(category));

        Event eventResult = eventMapper.toDtoEvent(event, eventRequest, image);

        assertEquals(event.getName(), eventResult.getName());
        assertEquals(event.getSlogan(), eventResult.getSlogan());
        assertEquals(event.getDescription(), eventResult.getDescription());
        assertEquals(event.getDateTime(), eventResult.getDateTime());
        assertEquals(event.getCategory(), eventResult.getCategory());
        assertEquals(event.getImage(), eventResult.getImage());
    }

    @Test
    void toDtoWhenCategoryNotFound() {

    }

    @Test
    void toDetailDto() {
        EventRequest eventRequest = new EventRequest();
        eventRequest.setName("name");
        eventRequest.setCategoryId(1L);
        eventRequest.setSlogan("slogan");
        eventRequest.setDescription("description");
        eventRequest.setYear(2024);
        eventRequest.setMonth(5);
        eventRequest.setDay(22);
        eventRequest.setHour(12);
        eventRequest.setMinute(0);
        eventRequest.setSeconds(0);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> eventMapper.toDtoEvent(event, eventRequest, image));
    }
}