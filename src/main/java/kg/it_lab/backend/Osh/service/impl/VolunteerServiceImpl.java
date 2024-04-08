package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.volunteer.VolunteerRequest;
import kg.it_lab.backend.Osh.dto.volunteer.VolunteerResponse;
import kg.it_lab.backend.Osh.entities.Volunteer;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.VolunteerMapper;
import kg.it_lab.backend.Osh.repository.VolunteerRepository;
import kg.it_lab.backend.Osh.service.VolunteerService;
import kg.it_lab.backend.Osh.service.emailSender.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository repository;
    private final VolunteerMapper mapper;
    private final EmailSenderService emailSenderService;
    @Override
    public List<VolunteerResponse> getAll() {
        return mapper.toDtoS(repository.findAll());
    }

    @Override
    public VolunteerResponse finById(Long id) {
        Optional<Volunteer> volunteer = repository.findById(id);
        if(volunteer.isEmpty()) {
            throw new NotFoundException("Volunteer with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return mapper.toDto(volunteer.get());
    }

    @Override
    public void addVolunteer(VolunteerRequest volunteerRequest) {
        Volunteer volunteer = new Volunteer();
        repository.save(mapper.toDtoVolunteer(volunteer, volunteerRequest));
        emailSenderService.sendMessageToAdmin(volunteer);
    }
}
