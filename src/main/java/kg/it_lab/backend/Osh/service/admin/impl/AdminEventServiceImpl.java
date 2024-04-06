package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.entities.Event;
import kg.it_lab.backend.Osh.entities.Image;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.ImageService;
import kg.it_lab.backend.Osh.service.admin.AdminEventService;
import kg.it_lab.backend.Osh.service.admin.AdminService;
import kg.it_lab.backend.Osh.service.emailSender.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final AdminService adminService;

    @Override
    public void addEvent(EventRequest eventRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if(adminService.imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        if (eventRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the event can't be empty");
        }
        if (eventRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the event can't be empty");
        }
        if (categoryRepository.findById(eventRequest.getCategoryId()).isEmpty()) {
            throw new BadRequestException("Category of event with this id wasn't found");
        }
        if (eventRequest.getSlogan().isEmpty()) {
            throw new BadRequestException("Slogan of event can't be empty ");
        }
        if (eventRequest.getYear() < 0) {
            throw new BadRequestException("Date of year can't be negative ");
        }
        if (eventRequest.getMonth() < 1 || eventRequest.getMonth() > 12) {
            throw new BadRequestException("Incorrect input of months");
        }
        if (eventRequest.getDay() > 31 || eventRequest.getDay() < 1) {
            throw new BadRequestException("Incorrect date of the day");
        }
        if (eventRequest.getMonth() == 2) {
            if (isLeapYear(eventRequest.getYear())) {
                if (eventRequest.getDay() > 29) {
                    throw new BadRequestException("February in leap year should have maximum 29 days");
                }
            } else {
                if (eventRequest.getDay() > 28) {
                    throw new BadRequestException("February should have maximum 28 days");
                }
            }

        } else if (eventRequest.getMonth() == 4 || eventRequest.getMonth() == 6 ||
                eventRequest.getMonth() == 9 || eventRequest.getMonth() == 11) {
            if (eventRequest.getDay() > 30) {
                throw new BadRequestException("This month should have maximum 30 days");
            }
        }


        if (eventRequest.getHour() > 23 || eventRequest.getHour() < 0) {
            throw new BadRequestException("Incorrect date of hour");
        }
        if (eventRequest.getMinute() < 0 || eventRequest.getMinute() > 59) {
            throw new BadRequestException("Incorrect date of minutes");
        }
        if (eventRequest.getSeconds() < 0 || eventRequest.getSeconds() > 59) {
            throw new BadRequestException("Incorrect date of seconds");
        }
        LocalDateTime dateTime = LocalDateTime.of(eventRequest.getYear() , eventRequest.getMonth() , eventRequest.getDay() , eventRequest.getHour(),   eventRequest.getMinute() , eventRequest.getSeconds());
        if(dateTime.isBefore(LocalDateTime.now()))
            throw new BadRequestException("DateTime can not be earlier than now!!!");
        Event event = new Event();
        eventRepository.save(eventMapper.toDtoEvent(event, eventRequest, image.get()));
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    @Override
    public void updateEvent(Long id, EventRequest eventRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if((adminService.imageChecker(image.get()) == 1 && !eventRepository.existsByImage(image.get())) || adminService.imageChecker(image.get()) > 1)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        Optional<Event> event = eventRepository.findById(id);
        if (eventRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the event can't be empty");
        }
        if (eventRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the event can't be empty");
        }
        if (event.isEmpty()) {
            throw new NotFoundException("Title of event with this id wasn't found", HttpStatus.NOT_FOUND);
        }
        if (categoryRepository.findById(eventRequest.getCategoryId()).isEmpty()) {
            throw new BadRequestException("Category of event can't be empty ");
        }
        if (eventRequest.getSlogan().isEmpty()) {
            throw new BadRequestException("Slogan of event can't be empty ");
        }
        if (eventRequest.getYear() < 0) {
            throw new BadRequestException("Date of year can't be negative ");
        }
        if (eventRequest.getMonth() < 1 || eventRequest.getMonth() > 12) {
            throw new BadRequestException("Incorrect input of months");
        }
        if (eventRequest.getDay() > 31 || eventRequest.getDay() < 1) {
            throw new BadRequestException("Incorrect date of the day");
        }
        if (eventRequest.getMonth() == 2) {
            if (isLeapYear(eventRequest.getYear())) {
                if (eventRequest.getDay() > 29) {
                    throw new BadRequestException("February in leap year should have maximum 29 days");
                }
            } else {
                if (eventRequest.getDay() > 28) {
                    throw new BadRequestException("February should have maximum 28 days");
                }
            }

        } else if (eventRequest.getMonth() == 4 || eventRequest.getMonth() == 6 ||
                eventRequest.getMonth() == 9 || eventRequest.getMonth() == 11) {
            if (eventRequest.getDay() > 30) {
                throw new BadRequestException("This month should have maximum 30 days");
            }
        }
        eventRepository.save(eventMapper.toDtoEvent(event.get(), eventRequest, image.get()));
    }

    @Override
    public void deleteEvent(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new NotFoundException("Event with id: " + id + " - not found", HttpStatus.NOT_FOUND);
        }
        int cnt = 0;
        if(event.get().getImage() != null){
            cnt = adminService.imageChecker(event.get().getImage());
        }

        eventRepository.deleteById(id);
        if(cnt == 1)imageService.deleteFile(event.get().getImage().getId());
//        imageRepository.deleteByName(event.get().getImage().getName());
    }
}
