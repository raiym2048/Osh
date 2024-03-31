package kg.it_lab.backend.Osh.service;

import kg.it_lab.backend.Osh.dto.volunteer.VolunteerRequest;
import kg.it_lab.backend.Osh.dto.volunteer.VolunteerResponse;

import java.util.List;

public interface VolunteerService {
    List<VolunteerResponse> getAll();
    VolunteerResponse finById(Long id);
    void addVolunteer(VolunteerRequest volunteerRequest);
}
