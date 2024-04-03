package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    private final Event event = new Event();
    @BeforeEach
    void setUp() {
//        event.setName();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByName() {
    }

    @Test
    void deleteByName() {
    }
}