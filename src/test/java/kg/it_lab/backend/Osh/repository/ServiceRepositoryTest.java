package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Service;
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
    private final Service service = new Service();
    @BeforeEach
    void setUp() {
        service.setName("example");
        service.setSubtopic("example");
        service.setDescription("It's description");
        service.setImages(null);
        serviceRepository.save(service);
    }

    @AfterEach
    void tearDown() {
        serviceRepository.deleteAll();
    }

    @Test
    void itShouldFindServiceByName() {
        Service findServiceByName = serviceRepository.findByName(service.getName()).orElse(null);

        assertNotNull(findServiceByName);
        assertEquals(service.getName(), findServiceByName.getName());
    }

    @Test
    void itShouldDeleteServiceByName() {
        serviceRepository.deleteByName(service.getName());

        Service emptyService = serviceRepository.findByName(service.getName()).orElse(null);

        assertNull(emptyService);
    }
}