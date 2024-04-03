package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.config.JwtService;
import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.service.ServiceRequest;
import kg.it_lab.backend.Osh.entities.*;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.EditorService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EditorServiceImpl implements EditorService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ImageRepository imageRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public void updateById(Long id, NewsRequest newsRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        Optional<News> news = newsRepository.findById(id);
        if (newsRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the news can't be empty");
        }
        if (newsRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the news can't be empty");
        }
        if (news.isEmpty()) {
            throw new NotFoundException("Title of news with this name wasn't found", HttpStatus.NOT_FOUND);
        }

        checker(news, id);
        newsRepository.save(newsMapper.toDtoNews(news.get(), newsRequest, image.get()));
    }

    @Override
    public void updateProject(Long id, ProjectRequest projectRequest) {
        Optional<Project> project = projectRepository.findById(id);
        if (projectRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the project can't be empty");
        }
        if (projectRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the project can't be empty");
        }
        if (project.isEmpty()) {
            throw new NotFoundException("Title of project with this name wasn't found", HttpStatus.NOT_FOUND);
        }
        if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Project with name " + projectRequest.getName() + " already exist!");
        }
        projectRepository.save(projectMapper.toDtoProject(project.get(), projectRequest));
    }

    @Override
    public void updateService(Long id, ServiceRequest serviceRequest) {
        Optional<Services> service = serviceRepository.findById(id);
        if (serviceRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the service can't be empty");
        }
        if (serviceRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the service can't be empty");
        }
        if (service.isEmpty()) {
            throw new NotFoundException("Title of service with this name wasn't found", HttpStatus.NOT_FOUND);
        }
        if (serviceRepository.findByName(serviceRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Service with name " + serviceRequest.getName() + " already exist!");
        }
        serviceRepository.save(serviceMapper.toDtoService(service.get(), serviceRequest));
    }

    @Override
    public AuthLoginResponse loginEditor(AdminLoginRequest adminLoginRequest) {
        Optional<User>user =userRepository.findByEmail(adminLoginRequest.getEmail());
        if(adminLoginRequest.getPassword().isEmpty() || adminLoginRequest.getEmail().isEmpty()){
            throw new BadRequestException("Your password or email can't be empty");
        }
        if(user.isEmpty()){
            throw new NotFoundException("User with this username was not found" , HttpStatus.NOT_FOUND);
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            adminLoginRequest.getEmail(),
                            adminLoginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.get());
        AuthLoginResponse authLoginResponse = new AuthLoginResponse();
        authLoginResponse.setToken(token);
        return  authLoginResponse;

    }
    @Override
    public void changePassword(String token  ,EditorPasswordRequest editorPasswordRequest) {
        User editor  = getUserFromToken(token); //todo change here
        String pass1 = editorPasswordRequest.getPassword1();
        String pass2 = editorPasswordRequest.getPassword2();
        if(!Objects.equals(pass1, pass2)){
            throw new BadRequestException("Passwords don't match!");
        }
        editor.setPassword(pass1);
        userRepository.save(editor);

    }

    @Override
    public void updateActivity(Long id, ActivityRequest activityRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this name not found", HttpStatus.NOT_FOUND);
        }
        Optional<Activity> activity = activityRepository.findById(id);
        if (activityRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the activity can't be empty");
        }
        if (activityRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the activity can't be empty");
        }
        if (activity.isEmpty()) {
            throw new NotFoundException("Title of activity with this name wasn't found", HttpStatus.NOT_FOUND);
        }
        if (activityRepository.findByName(activityRequest.getName()).isPresent()) {
            throw new BadRequestException("Title of activity with this name already exist");
        }
        activityRepository.save(activityMapper.toDtoActivity(activity.get(),activityRequest, image.get()));
    }

    @Override
    public void updateEvent(Long eventId, EventRequest eventRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (eventRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the event can't be empty");
        }
        if (eventRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the event can't be empty");
        }
        if (event.isEmpty()) {
            throw new NotFoundException("Title of event with this name wasn't found", HttpStatus.NOT_FOUND);
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
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    @Override
    public User getUserFromToken(String token) {
        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        if (chunks.length != 3)
            throw new org.springframework.security.authentication.BadCredentialsException("Wrong token!");
        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            byte[] decodedBytes = decoder.decode(chunks[1]);
            object = (JSONObject) jsonParser.parse(decodedBytes);
        } catch (ParseException e) {
            throw new org.springframework.security.authentication.BadCredentialsException("Wrong token!!");
        }
        return userRepository.findByUsername(String.valueOf(object.get("sub"))).orElseThrow(() -> new org.springframework.security.authentication.BadCredentialsException("Wrong token!!!"));
    }

    private void checker(Optional<News> news, Long id) {
        if(news.isEmpty()) {
            throw new NotFoundException("Product with id  " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }


}
