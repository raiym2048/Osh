package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.entities.Volunteer;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.repository.VolunteerRepository;
import kg.it_lab.backend.Osh.service.admin.AdminVolunteerService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminVolunteerServiceImpl implements AdminVolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final MessageSource messageSource;
    @Override
    public void acceptVolunteer(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("volunteer.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND));
        volunteer.setConfirmed(true);
        // todo here volunteer should enrolled to team or to sth
        volunteerRepository.save(volunteer);
    }

    @Override
    public void rejectVolunteer(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("volunteer.notfound", null, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND));
        volunteerRepository.delete(volunteer);
    }
}
