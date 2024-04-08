package kg.it_lab.backend.Osh.mapper.impl;

import kg.it_lab.backend.Osh.dto.volunteer.VolunteerRequest;
import kg.it_lab.backend.Osh.dto.volunteer.VolunteerResponse;
import kg.it_lab.backend.Osh.entities.Volunteer;
import kg.it_lab.backend.Osh.enums.Gender;
import kg.it_lab.backend.Osh.mapper.VolunteerMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VolunteerMapperImpl implements VolunteerMapper {
    @Override
    public VolunteerResponse toDto(Volunteer volunteer) {
        VolunteerResponse response = new VolunteerResponse();
        response.setId(volunteer.getId());
        response.setName(volunteer.getName());
        response.setGender(String.valueOf(volunteer.getGender()));
        response.setAge(volunteer.getAge());
        response.setTown(volunteer.getTown());
        response.setComment(volunteer.getComment());
        response.setContacts(volunteer.getContacts());
        return response;
    }

    @Override
    public List<VolunteerResponse> toDtoS(List<Volunteer> volunteerList) {
        List<VolunteerResponse> volunteerResponses = new ArrayList<>();
        for(Volunteer volunteer : volunteerList) {
            volunteerResponses.add(toDto(volunteer));
        }
        return volunteerResponses;
    }

    @Override
    public Volunteer toDtoVolunteer(Volunteer volunteer, VolunteerRequest volunteerRequest) {
        volunteer.setName(volunteerRequest.getName());
        volunteer.setGender(Gender.valueOf(volunteerRequest.getGender()));
        volunteer.setDateOfBirth(volunteerRequest.getDateOfBirth());
        volunteer.setTown(volunteerRequest.getTown());
        volunteer.setComment(volunteerRequest.getComment());
        volunteer.setContacts(volunteerRequest.getContacts());
        volunteer.setConfirmed(false);
        return volunteer;
    }
}
