package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Services;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ServiceRepositoryTest {

    @Autowired
    private ServiceRepository serviceRepository;
    private final Services services = new Services();
    @BeforeEach
    void setUp() {
        services.setName("example");
        services.setSubtopic("example");
        services.setDescription("It's description");
        services.setImages(null);
        serviceRepository.save(services);
    }

    @AfterEach
    void tearDown() {
        serviceRepository.deleteAll();
    }

    @Test
    void itShouldFindServiceByName() {
        Services findServiceByName = serviceRepository.findByName(services.getName()).orElse(null);

        assertNotNull(findServiceByName);
        assertEquals(services.getName(), findServiceByName.getName());
    }
}