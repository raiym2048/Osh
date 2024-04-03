package kg.it_lab.backend.Osh.repository;

import kg.it_lab.backend.Osh.entities.Volunteer;
import kg.it_lab.backend.Osh.enums.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VolunteerRepositoryTest {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @AfterEach
    void tearDown() {
        volunteerRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfVolunteerExistWithId() {
        // given
        Volunteer volunteer = new Volunteer();
        volunteer.setName("Nurmukhanbet");
        volunteer.setGender(Gender.MALE);
        volunteer.setDateOfBirth(LocalDate.of(2004, 1, 1));
        volunteer.setTown("Osh");
        volunteer.setComment("It should work");
        volunteer.setContacts("0551-04-04-42");
        volunteerRepository.save(volunteer);

        // when
        Volunteer foundVolunteer = volunteerRepository.findById(volunteer.getId()).orElse(null);

        // then
        assertNotNull(foundVolunteer);
        assertEquals(volunteer.getName(), foundVolunteer.getName());
    }
}
