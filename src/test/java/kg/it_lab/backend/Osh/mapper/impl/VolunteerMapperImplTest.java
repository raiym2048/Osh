package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.volunteer.VolunteerRequest;
import kg.it_lab.backend.Osh.dto.volunteer.VolunteerResponse;
import kg.it_lab.backend.Osh.entities.Volunteer;
import kg.it_lab.backend.Osh.enums.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VolunteerMapperImplTest {

    @InjectMocks
    private VolunteerMapperImpl volunteerMapper;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldReturnVolunteerResponse() {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setName("example");
        volunteer.setGender(Gender.MALE);
        volunteer.setDateOfBirth(LocalDate.of(2004, 1, 1));
        volunteer.setTown("Osh");
        volunteer.setComment("It's comment for volunteer");
        volunteer.setContacts("4285704");

        VolunteerResponse expectedResponse = new VolunteerResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("example");
        expectedResponse.setGender(String.valueOf(Gender.MALE));
        expectedResponse.setAge(volunteer.getAge());
        expectedResponse.setTown("Osh");
        expectedResponse.setComment("It's comment for volunteer");
        expectedResponse.setContacts("4285704");

        VolunteerResponse response = volunteerMapper.toDto(volunteer);

        assertEquals(volunteer.getName(), response.getName());
        assertEquals(expectedResponse, response);
    }

    @Test
    void itShouldReturnListOfResponse() {
        List<Volunteer> volunteers = new ArrayList<>();

        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setName("example");
        volunteer.setGender(Gender.MALE);
        volunteer.setDateOfBirth(LocalDate.of(2004, 1, 1));
        volunteer.setTown("Osh");
        volunteer.setComment("It's comment for volunteer");
        volunteer.setContacts("4285704");

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setId(2L);
        volunteer1.setName("example");
        volunteer1.setGender(Gender.FEMALE);
        volunteer1.setDateOfBirth(LocalDate.of(2004, 1, 1));
        volunteer1.setTown("Osh");
        volunteer1.setComment("It's comment for volunteer");
        volunteer1.setContacts("4285704");

        volunteers.add(volunteer);
        volunteers.add(volunteer1);

        List<VolunteerResponse> expectedResponseList = new ArrayList<>();

        VolunteerResponse response = new VolunteerResponse();
        response.setId(1L);
        response.setName("example");
        response.setGender(String.valueOf(Gender.MALE));
        response.setAge(volunteer.getAge());
        response.setTown("Osh");
        response.setComment("It's comment for volunteer");
        response.setContacts("4285704");

        VolunteerResponse response1 = new VolunteerResponse();
        response1.setId(2L);
        response1.setName("example");
        response1.setGender(String.valueOf(Gender.FEMALE));
        response1.setAge(volunteer1.getAge());
        response1.setTown("Osh");
        response1.setComment("It's comment for volunteer");
        response1.setContacts("4285704");

        expectedResponseList.add(response);
        expectedResponseList.add(response1);

        List<VolunteerResponse> exactResponseList = volunteerMapper.toDtoS(volunteers);

        assertEquals(expectedResponseList, exactResponseList);
    }

    @Test
    void toDtoVolunteer() {
        VolunteerRequest volunteerRequest = new VolunteerRequest();
        volunteerRequest.setName("example");
        volunteerRequest.setGender("MALE");
        volunteerRequest.setDateOfBirth(LocalDate.of(20004, 1, 1));
        volunteerRequest.setTown("Osh");
        volunteerRequest.setComment("It's comment for volunteer request");
        volunteerRequest.setContacts("43544");

        Volunteer expectedVolunteer = new Volunteer();
        expectedVolunteer.setId(null);
        expectedVolunteer.setName("example");
        expectedVolunteer.setGender(Gender.MALE);
        expectedVolunteer.setDateOfBirth(LocalDate.of(20004, 1, 1));
        expectedVolunteer.setTown("Osh");
        expectedVolunteer.setComment("It's comment for volunteer request");
        expectedVolunteer.setContacts("43544");

        Volunteer exactVolunteer = new Volunteer();
        exactVolunteer = volunteerMapper.toDtoVolunteer(exactVolunteer, volunteerRequest);

        assertEquals(expectedVolunteer, exactVolunteer);
    }
}