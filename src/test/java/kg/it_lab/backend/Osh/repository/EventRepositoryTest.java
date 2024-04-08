package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    private final Event event = new Event();
    @BeforeEach
    void setUp() {
        event.setName("example");
        event.setDescription("It's description for event");
        event.setDateTime(LocalDateTime.now());
        event.setImage(null);
        eventRepository.save(event);
    }

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    void itShouldFindEventByName() {
        Event findEventByName = eventRepository.findByName(event.getName()).orElse(null);

        assertNotNull(findEventByName);
        assertEquals(event.getName(), findEventByName.getName());
    }

    // todo will the method remove?
    @Test
    void deleteByName() {
    }
}