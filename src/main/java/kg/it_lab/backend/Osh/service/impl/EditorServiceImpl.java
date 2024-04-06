package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.config.JwtService;
import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.entities.*;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.admin.AdminService;
import kg.it_lab.backend.Osh.service.AuthLoginService;
import kg.it_lab.backend.Osh.service.EditorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final ServicesRepository servicesRepository;
    private final ServicesMapper servicesMapper;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder encoder;
    private final AuthLoginService authLoginService;
    private final SponsorshipRepository sponsorshipRepository;
    private final SponsorshipMapper sponsorshipMapper;
    private final AdminService adminService;

    @Override
    public void updateById(Long id, NewsRequest newsRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if((adminService.imageChecker(image.get()) == 1 && !newsRepository.existsByImage(image.get())) || adminService.imageChecker(image.get()) > 1)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        Optional<News> news = newsRepository.findById(id);
        if (newsRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the news can't be empty");
        }
        if (newsRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the news can't be empty");
        }
        if (news.isEmpty()) {
            throw new NotFoundException("News with ID  wasn't found", HttpStatus.NOT_FOUND);
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
            throw new NotFoundException("Project with this ID wasn't found", HttpStatus.NOT_FOUND);
        }
        if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Project with name " + projectRequest.getName() + " already exist!");
        }
        projectRepository.save(projectMapper.toDtoProject(project.get(), projectRequest));
    }

    @Override
    public void updateService(Long id, ServicesRequest servicesRequest) {
        Optional<Services> service = servicesRepository.findById(id);
        if (servicesRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the service can't be empty");
        }
        if (servicesRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the service can't be empty");
        }
        if (service.isEmpty()) {
            throw new NotFoundException("Service with this ID wasn't found", HttpStatus.NOT_FOUND);
        }
        if (servicesRepository.findByName(servicesRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Service with name " + servicesRequest.getName() + " already exist!");
        }
        servicesRepository.save(servicesMapper.toDtoService(service.get(), servicesRequest));
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
    public void changePassword(String token  ,EditorPasswordRequest editorPasswordRequest, String editorEmail) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String editorEmailFromToken = jwtService.getUserEmail(token);
        System.out.println("It's email from token: " + editorEmailFromToken);
        if(!editorEmailFromToken.equals(editorEmail)) {
            throw new BadRequestException("You can't change password!");
        }

        User editor = userRepository.findByEmail(editorEmail).orElseThrow();

        String pass1 = editorPasswordRequest.getPassword1();
        String pass2 = editorPasswordRequest.getPassword2();
        if(!Objects.equals(pass1, pass2)){
            throw new BadCredentialsException("Passwords don't match!");
        }

        editor.setPassword(encoder.encode(pass1));
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
    public void updateSponsorship(Long id, SponsorshipRequest sponsorshipRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if((adminService.imageChecker(image.get()) == 1 && !sponsorshipRepository.existsByImage(image.get())) || adminService.imageChecker(image.get()) > 1)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        Optional<Sponsorship> sponsorship = sponsorshipRepository.findById(id);
        if(sponsorship.isEmpty()){
            throw new NotFoundException("Sponsorship with id" + id + "not found",HttpStatus.NOT_FOUND);
        }
        if(sponsorshipRequest.getInn().isEmpty()){
            throw new BadRequestException("INN can't be empty ");
        }
        if(sponsorshipRequest.getBank().isEmpty()){
            throw new BadRequestException("Bank can't be empty");
        }
        if(sponsorshipRequest.getAddress().isEmpty()){
            throw new BadRequestException("Address can't be empty ");
        }
        if(sponsorshipRequest.getPaymentAccount().isEmpty()){
            throw new BadRequestException("Payment account can't be empty");
        }
        if(sponsorshipRequest.getCompany().isEmpty()) {
            throw new BadRequestException("Company name can't be empty");
        }
        if(sponsorshipRequest.getDirector().isEmpty()){
            throw new BadRequestException("Director can't be empty");
        }
        sponsorshipRepository.save(sponsorshipMapper.toDtoSponsorship(sponsorship.get() , sponsorshipRequest, image.get()));
    }

    @Override
    public void updateEvent(Long eventId, EventRequest eventRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if((adminService.imageChecker(image.get()) == 1 && !newsRepository.existsByImage(image.get())) || adminService.imageChecker(image.get()) > 1)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        Optional<Event> event = eventRepository.findById(eventId);
        if (eventRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the event can't be empty");
        }
        if (eventRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the event can't be empty");
        }
        if (event.isEmpty()) {
            throw new NotFoundException("Event with this ID wasn't found", HttpStatus.NOT_FOUND);
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



    private void checker(Optional<News> news, Long id) {
        if(news.isEmpty()) {
            throw new NotFoundException("Product with id  " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }


}
