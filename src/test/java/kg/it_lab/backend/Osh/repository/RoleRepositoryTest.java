package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private final Role role = new Role();

    @BeforeEach
    void setUp() {
        role.setName("ADMIN");
        roleRepository.save(role);
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void itShouldFindRoleByName() {
        Role findRoleByName = roleRepository.findByName(role.getName()).orElse(null);

        assertNotNull(findRoleByName);
        assertEquals(role.getName(), findRoleByName.getName());
    }
}