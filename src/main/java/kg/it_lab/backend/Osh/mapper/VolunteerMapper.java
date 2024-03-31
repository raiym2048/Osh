package kg.it_lab.backend.Osh.mapper;

import kg.it_lab.backend.Osh.dto.volunteer.VolunteerRequest;
import kg.it_lab.backend.Osh.dto.volunteer.VolunteerResponse;
import kg.it_lab.backend.Osh.entities.Volunteer;

import java.util.List;

public interface VolunteerMapper {
    VolunteerResponse toDto(Volunteer volunteer);
    List<VolunteerResponse> toDtoS(List<Volunteer> volunteerList);
    Volunteer toDtoVolunteer(Volunteer volunteer, VolunteerRequest volunteerRequest);
}
