package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.User;
import kg.it_lab.backend.Osh.entities.Volunteer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
    private final User user = new User();
    @BeforeEach
    void setUp() {
        user.setEmail("example01@example.com");
        user.setUsername("example");
        user.setPassword("password");
        user.setRole(null); // todo fix here
        userRepository.save(user);
    }
    @Test
    void itShouldFindUserByEmail() {
        // given


        // when
        User getUserByEmail = userRepository.findByEmail(user.getEmail()).orElse(null);

        // then
        assertNotNull(getUserByEmail);
        assertEquals(user.getEmail(), getUserByEmail.getEmail());
    }

//    @Test
//    void findByUsername() {
//        // given
//
//        // when
//        User getUserByName = userRepository.findByUsername(user.getUsername()).orElse(null);
//
//        // then
//        assertNotNull(getUserByName);
//        assertEquals(user.getUsername(), getUserByName.getUsername());
//    }
}