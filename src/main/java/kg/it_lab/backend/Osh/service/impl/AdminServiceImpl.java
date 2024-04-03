package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.admin.EditorRegisterRequest;
import kg.it_lab.backend.Osh.dto.admin.category.CategoryRequest;

import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;

import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.role.RoleRequest;
import kg.it_lab.backend.Osh.dto.service.ServiceRequest;
import kg.it_lab.backend.Osh.entities.*;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;

import kg.it_lab.backend.Osh.service.AdminService;

import kg.it_lab.backend.Osh.service.emailSender.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final RoleRepository roleRepository;
    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final ServiceMapper serviceMapper;
    private final ServiceRepository serviceRepository;
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Override
    public void add(NewsRequest newsRequest, String imageName) {
        Optional<Image> image = imageRepository.findByName(imageName);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this name not found", HttpStatus.NOT_FOUND);
        }
        if (newsRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the news can't be empty");
        }
        if (newsRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the news can't be empty");
        }
        if (newsRepository.findByName(newsRequest.getName()).isPresent()) {
            throw new BadCredentialsException("News with name " + newsRequest.getName() + " already exist!");
        }
        News news = new News();
        newsRepository.save(newsMapper.toDtoNews(news, newsRequest, image.get()));
    }

    @Override
    public void updateByName(String name, NewsRequest newsRequest, String imageName) {
        Optional<Image> image = imageRepository.findByName(imageName);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this name not found", HttpStatus.NOT_FOUND);
        }
        Optional<News> news = newsRepository.findByName(name);
        if (newsRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the news can't be empty");
        }
        if (newsRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the news can't be empty");
        }
        if (news.isEmpty()) {
            throw new NotFoundException("Title of news with this name wasn't found", HttpStatus.NOT_FOUND);
        }
        if (newsRepository.findByName(newsRequest.getName()).isPresent()) {
            throw new BadRequestException("Title of news with this name already exist");
        }
        checker(news, name);
        newsRepository.save(newsMapper.toDtoNews(news.get(), newsRequest, image.get()));
    }

    @Override
    public void deleteByName(String name) {
        Optional<News> news = newsRepository.findByName(name);
        checker(news, name);
        newsRepository.deleteByName(name);
        imageRepository.deleteByName(news.get().getImage().getName());

    }

    @Override
    public void addEvent(EventRequest eventRequest, String imageName) {
        Optional<Image> image = imageRepository.findByName(imageName);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this name not found", HttpStatus.NOT_FOUND);
        }
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
        if (eventRepository.findByName(eventRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Event with name " + eventRequest.getName() + " already exist!");
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
        Event event = new Event();
        eventRepository.save(eventMapper.toDtoEvent(event, eventRequest, image.get()));
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    @Override
    public void updateEvent(String name, EventRequest eventRequest, String imageName) {
        Optional<Image> image = imageRepository.findByName(imageName);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this name not found", HttpStatus.NOT_FOUND);
        }
        Optional<Event> event = eventRepository.findByName(name);
        if (eventRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the event can't be empty");
        }
        if (eventRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the event can't be empty");
        }
        if (event.isEmpty()) {
            throw new NotFoundException("Title of event with this name wasn't found", HttpStatus.NOT_FOUND);
        }
        if (eventRepository.findByName(eventRequest.getName()).isPresent()) {
            throw new BadRequestException("Title of event with this name already exist");
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
    public void deleteEvent(String name) {
        Optional<Event> event = eventRepository.findByName(name);
        if (event.isEmpty()) {
            throw new NotFoundException("Event with name " + name + " not found", HttpStatus.NOT_FOUND);
        }
        eventRepository.deleteByName(name);
        imageRepository.deleteByName(event.get().getImage().getName());

    }

    @Override
    public void addCategory(CategoryRequest categoryRequest) {
        if (categoryRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the category can't be empty");
        }
        if (categoryRepository.findByName(categoryRequest.getName()).isPresent()) {
            throw new BadRequestException("Category with this title already exist!");
        }

        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        if (category.isEmpty()) {
            throw new BadRequestException("Category wasn't found");
        }
        categoryRepository.deleteByName(name);
    }

    @Override
    public void addRole(RoleRequest roleRequest) {
        if (roleRequest.getRoleName().isEmpty()) {
            throw new BadRequestException("Role name can't be empty");
        }
        Optional<Role> role = roleRepository.findByName(roleRequest.getRoleName());
        if (role.isPresent()) {
            throw new BadRequestException("This role already exist");
        }
        Role role1 = new Role();
        role1.setName(roleRequest.getRoleName());
        roleRepository.save(role1);
    }

    @Override
    public void addProject(ProjectRequest projectRequest) {
        if (projectRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the project can't be empty");
        }
        if (projectRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the project can't be empty");
        }
        if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Project with name " + projectRequest.getName() + " already exist!");
        }
        Project project = new Project();
        projectRepository.save(projectMapper.toDtoProject(project, projectRequest));
    }

    @Override
    public void updateProject(String name, ProjectRequest projectRequest) {
        Optional<Project> project = projectRepository.findByName(name);
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
    public void deleteProject(String name) {
        Optional<Project> project = projectRepository.findByName(name);
        if (project.isEmpty()) {
            throw new NotFoundException("Title of project with this name wasn't found", HttpStatus.NOT_FOUND);
        }
        projectRepository.deleteByName(name);
    }

    @Override
    public void addService(ServiceRequest serviceRequest) {
        if (serviceRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the service can't be empty");
        }
        if (serviceRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the service can't be empty");
        }
        if (serviceRepository.findByName(serviceRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Service with name " + serviceRequest.getName() + " already exist!");
        }
        var service = new kg.it_lab.backend.Osh.entities.Service();
        serviceRepository.save(serviceMapper.toDtoService(service, serviceRequest));

    }

    @Override
    public void updateService(String name, ServiceRequest serviceRequest) {
        Optional<kg.it_lab.backend.Osh.entities.Service> service = serviceRepository.findByName(name);
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
    public void deleteService(String name) {
        Optional<kg.it_lab.backend.Osh.entities.Service> service = serviceRepository.findByName(name);
        if (service.isEmpty()) {
            throw new NotFoundException("Title of service with this name wasn't found", HttpStatus.NOT_FOUND);
        }
        serviceRepository.deleteByName(name);
    }

    @Override
    public void addActivity(ActivityRequest activityRequest, String imageName) {
        Optional<Image> image = imageRepository.findByName(imageName);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this name not found", HttpStatus.NOT_FOUND);
        }
        if (activityRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the activity can't be empty");
        }
        if (activityRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the activity can't be empty");
        }
        if (activityRepository.findByName(activityRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Activity with name " + activityRequest.getName() + " already exist!");
        }
        Activity activity = new Activity();
        activityRepository.save(activityMapper.toDtoActivity(activity, activityRequest,  image.get()));
    }

    @Override
    public void updateActivity(String name, ActivityRequest activityRequest, String imageName) {
        Optional<Image> image = imageRepository.findByName(imageName);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this name not found", HttpStatus.NOT_FOUND);
        }
        Optional<Activity> activity = activityRepository.findByName(name);
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
    public void deleteActivity(String name) {
        Optional<Activity> activity = activityRepository.findByName(name);
        if (activity.isEmpty()) {
            throw new NotFoundException("Activity with name " + name + " not found", HttpStatus.NOT_FOUND);
        }

        activityRepository.deleteByName(name);
        imageRepository.deleteByName(activity.get().getImage().getName());

    }

    @Override
    public void registerEditor(EditorRegisterRequest editorRegisterRequest) {
        if (userRepository.findByEmail(editorRegisterRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Editor with this email already exist");
        }
        if (editorRegisterRequest.getEmail().isEmpty()) {
            throw new BadRequestException("Your email can't be empty");
        }
        if (!editorRegisterRequest.getEmail().contains("@")) {
            throw new BadRequestException("Invalid email!");
        }

        User editor = new User();
        Optional<Role> role = roleRepository.findByName(editorRegisterRequest.getRole());
        if (role.isEmpty()) {
            throw new NotFoundException("Role with this name not found", HttpStatus.NOT_FOUND);
        }
        editor.setEmail(editorRegisterRequest.getEmail());
        editor.setRole(role.get());
        userRepository.save(editor);
        emailSenderService.sendPassword(editorRegisterRequest.getEmail());
    }


    private void checker(Optional<News> news, String name) {
        if (news.isEmpty()) {
            throw new NotFoundException("News with name " + name + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
