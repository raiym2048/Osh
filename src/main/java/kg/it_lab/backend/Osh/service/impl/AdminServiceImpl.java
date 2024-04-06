package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.admin.EditorRegisterRequest;
import kg.it_lab.backend.Osh.dto.admin.category.CategoryRequest;

import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;

import kg.it_lab.backend.Osh.dto.numbers.NumbersRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.role.RoleRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.entities.*;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.AdminService;

import kg.it_lab.backend.Osh.service.ImageService;
import kg.it_lab.backend.Osh.service.emailSender.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private final ServicesMapper servicesMapper;
    private final ServicesRepository servicesRepository;
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final SponsorshipRepository sponsorshipRepository;
    private final SponsorshipMapper sponsorshipMapper;
    private final NumbersRepository numbersRepository;
    private final NumbersMapper numbersMapper;
    private final ImageService imageService;

    @Override
    public void add(NewsRequest newsRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if(imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        if (newsRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the news can't be empty");
        }
        if(categoryRepository.findById(newsRequest.getCategoryId()).isEmpty()){
            throw new NotFoundException("Category with this id "+ newsRequest.getCategoryId()+" not found" , HttpStatus.NOT_FOUND);
        }
        if (newsRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the news can't be empty");
        }
        News news = new News();
        newsRepository.save(newsMapper.toDtoNews(news, newsRequest, image.get()));
    }

    @Override
    public void updateById(Long id, NewsRequest newsRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if((imageChecker(image.get()) == 1 && !newsRepository.existsByImage(image.get())) || imageChecker(image.get()) > 1)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        Optional<News> news = newsRepository.findById(id);
        if (newsRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the news can't be empty");
        }
        if (newsRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the news can't be empty");
        }
        if (newsRequest.getCategoryId() == null)
            throw new BadRequestException("Category of the news can't be empty");

        if (categoryRepository.findById(newsRequest.getCategoryId()).isEmpty())
            throw new NotFoundException("Category with id: " + newsRequest.getCategoryId() + " - wasn't found!!", HttpStatus.NOT_FOUND);

        checker(news, id);
        news.get().setImage(null);
        newsRepository.save(newsMapper.toDtoNews(news.get(), newsRequest, image.get()));
    }

    @Override
    public void deleteById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        checker(news, id);
        int cnt = 0;
        if(news.get().getImage() != null){
            cnt = imageChecker(news.get().getImage());
            System.out.println(cnt);
        }

        newsRepository.deleteById(id);
        if(cnt == 1)imageService.deleteFile(news.get().getImage().getId());

//        imageRepository.deleteByName(news.get().getImage().getName());
//        eventRepository.update

    }

    @Override
    public void addEvent(EventRequest eventRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if(imageChecker(image.get()) > 0)
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
        if((imageChecker(image.get()) == 1 && !eventRepository.existsByImage(image.get())) || imageChecker(image.get()) > 1)
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
            cnt = imageChecker(event.get().getImage());
        }

        eventRepository.deleteById(id);
        if(cnt == 1)imageService.deleteFile(event.get().getImage().getId());
//        imageRepository.deleteByName(event.get().getImage().getName());
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
    public void deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new BadRequestException("Category wasn't found");
        }
        categoryRepository.deleteById(id);
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
    public void deleteRole(Long id) {
        Optional<Role>role = roleRepository.findById(id);
        if(role.isEmpty()){
            throw new NotFoundException("Role with this id: " + id + " - not found", HttpStatus.NOT_FOUND );
        }
        roleRepository.deleteById(id);
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
            throw new BadCredentialsException("Project with name: " + projectRequest.getName() + " - already exist!");
        }
        Project project = new Project();
        projectRepository.save(projectMapper.toDtoProject(project, projectRequest));
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
            throw new NotFoundException("Title of project with this id wasn't found", HttpStatus.NOT_FOUND);
        }
        if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Project with name: " + projectRequest.getName() + " - already exist!");
        }
        projectRepository.save(projectMapper.toDtoProject(project.get(), projectRequest));
    }

    @Override
    public void deleteProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new NotFoundException("Project with this id wasn't found", HttpStatus.NOT_FOUND);
        }
        ArrayList<Long> ans = new ArrayList<>();
        if(project.get().getImages() != null)
            for(Image image: project.get().getImages())
                if(imageChecker(image) == 1)
                    ans.add(image.getId());

        projectRepository.deleteById(id);
        for (Long a : ans)
            imageService.deleteFile(a);
//            imageRepository.deleteByName(s);
    }

    @Override
    public void addService(ServicesRequest servicesRequest) {
        if (servicesRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the service can't be empty");
        }
        if (servicesRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the service can't be empty");
        }
        if (servicesRepository.findByName(servicesRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Service with name " + servicesRequest.getName() + " already exist!");
        }

        var service = new Services();
        servicesRepository.save(servicesMapper.toDtoService(service, servicesRequest));

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
            throw new NotFoundException( "Service with this id wasn't found", HttpStatus.NOT_FOUND);
        }
        if (servicesRepository.findByName(servicesRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Service with name " + servicesRequest.getName() + " already exist!");
        }
        servicesRepository.save(servicesMapper.toDtoService(service.get(), servicesRequest));
    }

    @Override
    public void attachImageToService(Long serviceId, Long imageId) {
        Optional<Services> service = servicesRepository.findById(serviceId);
        if(service.isEmpty()){
            throw new NotFoundException("Services with this id" + serviceId + "not found" ,HttpStatus.NOT_FOUND );
        }
        Optional<Image>image = imageRepository.findById(imageId);
        if(image.isEmpty()){
            throw new NotFoundException("Image with id " + imageId + " not found" , HttpStatus.NOT_FOUND);
        }
        if(imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        List<Image> images = new ArrayList<>();
        if(service.get().getImages() != null)
            images=service.get().getImages();
        images.add(image.get());
        service.get().setImages(images);
        servicesRepository.save(service.get());

    }

    @Override
    public void attachImageToProject(Long projectId, Long imageId) {
        Optional<Project>project =projectRepository.findById(projectId);
        if(project.isEmpty()){
            throw new NotFoundException("project with this id not found ", HttpStatus.NOT_FOUND);
        }
        Optional<Image>image =imageRepository.findById(imageId);
        if(image.isEmpty()){
            throw new NotFoundException("Image with id " + imageId + " not found" , HttpStatus.NOT_FOUND);
        }
        if(imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        List<Image> images = new ArrayList<>();
        if(project.get().getImages() != null) images = project.get().getImages();
        images.add(image.get());
        project.get().setImages(images);
        projectRepository.save(project.get());
    }

    @Override
    public void deleteService(Long id) {
        Optional<Services> service = servicesRepository.findById(id);
        if (service.isEmpty()) {
            throw new NotFoundException("Service with this id wasn't found", HttpStatus.NOT_FOUND);
        }
        ArrayList<Long> ans = new ArrayList<>();
        if(service.get().getImages() != null)
            for(Image image: service.get().getImages())
                if(imageChecker(image) == 1)
                    ans.add(image.getId());

        servicesRepository.deleteById(id);
        for (Long a : ans)
            imageService.deleteFile(a);
//            imageRepository.deleteByName(s);
//        servicesRepository.deleteById(id);
    }

    @Override
    public void addActivity(ActivityRequest activityRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if(imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
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
    public void updateActivity(Long id, ActivityRequest activityRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this name not found", HttpStatus.NOT_FOUND);
        }
        if((imageChecker(image.get()) == 1 && !activityRepository.existsByImage(image.get())) || imageChecker(image.get()) > 1)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
        Optional<Activity> activity = activityRepository.findById(id);
        if (activityRequest.getName().isEmpty()) {
            throw new BadRequestException("Title of the activity can't be empty");
        }
        if (activityRequest.getDescription().isEmpty()) {
            throw new BadRequestException("Content of the activity can't be empty");
        }
        if (activity.isEmpty()) {
            throw new NotFoundException("Activity with this ID wasn't found", HttpStatus.NOT_FOUND);
        }
        if (activityRepository.findByName(activityRequest.getName()).isPresent()) {
            throw new BadRequestException("Title of activity with this name already exist");
        }
        activityRepository.save(activityMapper.toDtoActivity(activity.get(),activityRequest, image.get()));
    }

    @Override
    public void deleteActivity(Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new NotFoundException("Activity with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        int cnt = 0;
        if(activity.get().getImage() != null){
            cnt = imageChecker(activity.get().getImage());
        }

        activityRepository.deleteById(id);
        if(cnt == 1)imageService.deleteFile(activity.get().getImage().getId());
//        imageRepository.deleteByName(activity.get().getImage().getName());


    }

    @Override
    public void addSponsorship(SponsorshipRequest sponsorshipRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if(imageChecker(image.get()) > 0)
            throw new BadRequestException("Image with id: " + imageId + " - is already in use!!");
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
        Sponsorship sponsorship = new Sponsorship();
        sponsorshipRepository.save(sponsorshipMapper.toDtoSponsorship(sponsorship ,sponsorshipRequest, image.get()));
    }

    @Override
    public void updateSponsorship(Long id, SponsorshipRequest sponsorshipRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new NotFoundException("Image with this id not found", HttpStatus.NOT_FOUND);
        }
        if((imageChecker(image.get()) == 1 && !sponsorshipRepository.existsByImage(image.get())) || imageChecker(image.get()) > 1)
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
    public void deleteSponsorship(Long id) {
        Optional<Sponsorship> sponsorship = sponsorshipRepository.findById(id);
        if(sponsorship.isEmpty()){
            throw new NotFoundException("Sponsorship with id" + id + "not found",HttpStatus.NOT_FOUND);
        }
        sponsorshipRepository.deleteById(id);
    }

    @Override
    public void addNumbers(NumbersRequest numbersRequest) {
        Numbers numbers = new Numbers();
        numbersRepository.save(numbersMapper.toDtoNumbers(numbers , numbersRequest ));
    }

    @Override
    public void updateNumbers(Long id , NumbersRequest numbersRequest) {
        Optional<Numbers> numbers = numbersRepository.findById(id);
        if(numbersRepository.findById(id).isEmpty()){
            throw new NotFoundException("Numbers with id "+ id+ " not found!" , HttpStatus.NOT_FOUND);
        }
        numbersRepository.save(numbersMapper.toDtoNumbers(numbers.get() , numbersRequest ));
    }

    @Override
    public void deleteNumbersById(Long id) {
        Optional<Numbers> numbers = numbersRepository.findById(id);
        if(numbersRepository.findById(id).isEmpty()){
            throw new NotFoundException("Numbers with id "+ id+ " not found!" , HttpStatus.NOT_FOUND);
        }
        numbersRepository.deleteById(id);


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


    private void checker(Optional<News> news, Long id ) {
        if (news.isEmpty()) {
            throw new NotFoundException("News with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public int imageChecker(Image image){
        int cnt = 0;
        if (eventRepository.existsByImage(image)) {
            cnt++;
            System.out.println("EVENT");
        }
        if (activityRepository.existsByImage(image)) {
            cnt++;
            System.out.println("ACTIVITY");
        }
        if (newsRepository.existsByImage(image)) {
            cnt++;
            System.out.println("NEWS");
        }
        if (projectRepository.existsByImagesContaining(image)) {
            cnt++;
            System.out.println("PROJECT");
        }
        if (servicesRepository.existsByImagesContaining(image)) {
            cnt++;
            System.out.println("SERVICES");
        }
        if(sponsorshipRepository.existsByImage(image)){
            cnt++;
            System.out.println("SPONSORSHIP");
        }
        return cnt;

    }
}
