package kg.it_lab.backend.Osh.service.impl;

import kg.it_lab.backend.Osh.config.JwtService;
import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.entities.*;
import kg.it_lab.backend.Osh.exception.BadCredentialsException;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.mapper.*;
import kg.it_lab.backend.Osh.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditorServiceImplTest {

    @InjectMocks
    private EditorServiceImpl editorService;

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsMapper newsMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ServicesRepository servicesRepository;
    @Mock
    private ServicesMapper servicesMapper;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private ActivityMapper activityMapper;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventMapper eventMapper;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private SponsorshipRepository sponsorshipRepository;
    @Mock
    private SponsorshipMapper sponsorshipMapper;

    private final NewsRequest newsRequest = new NewsRequest();
    private final ProjectRequest projectRequest = new ProjectRequest();
    private final ServicesRequest servicesRequest = new ServicesRequest();
    private final EditorPasswordRequest editorPasswordRequest = new EditorPasswordRequest();
    private final ActivityRequest activityRequest = new ActivityRequest();
    private final SponsorshipRequest sponsorshipRequest = new SponsorshipRequest();
    private final EventRequest eventRequest = new EventRequest();


    private final Image image = new Image();
    private final News news = new News();
    private final Project project = new Project();
    private final Services services = new Services();
    private final Activity activity = new Activity();
    private final Sponsorship sponsorship = new Sponsorship();
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void updateNewsById() {
        newsRequest.setName("name");
        newsRequest.setDescription("description");
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(news));
        when(newsMapper.toDtoNews(any(News.class), any(NewsRequest.class), any(Image.class))).thenReturn(news);

        editorService.updateById(1L, newsRequest, 1L);

        verify(imageRepository).findById(anyLong());
        verify(newsRepository).findById(anyLong());
        verify(newsRepository).save(new News());
    }

    @Test
    void updateNewsByIdThrowExceptionWhenImageIsEmpty() {
        when(imageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> editorService.updateById(anyLong(), newsRequest, 1L));

        verify(imageRepository).findById(anyLong());
        verify(newsRepository, never()).findById(anyLong());
        verify(newsMapper, never()).toDtoNews(any(News.class), any(NewsRequest.class), any(Image.class));
    }

    @Test
    void updateNewsByIdThrowExceptionWhenNewsIsEmpty() {
        newsRequest.setName("name");
        newsRequest.setDescription("description");
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        when(newsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> editorService.updateById(1L, newsRequest, 1L));

        verify(imageRepository).findById(anyLong());
        verify(newsRepository).findById(anyLong());
        verify(newsMapper, never()).toDtoNews(any(News.class), any(NewsRequest.class), any(Image.class));
    }

    @Test
    void updateNewsByIdThrowExceptionWhenNewsRequestNameIsEmpty() {
        newsRequest.setDescription("description");
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(news));

        assertThrows(NullPointerException.class, () -> editorService.updateById(1L, newsRequest, 1L));

        verify(imageRepository).findById(anyLong());
        verify(newsRepository).findById(anyLong());
        verify(newsMapper, never()).toDtoNews(any(News.class), any(NewsRequest.class), any(Image.class));
    }

    @Test
    void updateNewsByIdThrowExceptionWhenNewsDescIsEmpty() {
        newsRequest.setName("name");
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        when(newsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> editorService.updateById(1L, newsRequest, 1L));

        verify(imageRepository).findById(anyLong());
        verify(newsRepository).findById(anyLong());
        verify(newsMapper, never()).toDtoNews(any(News.class), any(NewsRequest.class), any(Image.class));
    }
    @Test
    void updateProject() {
        projectRequest.setDescription("des");
        projectRequest.setName("name1");
        projectRequest.setSubtopic("hello");

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(projectRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(projectMapper.toDtoProject(any(Project.class), any(ProjectRequest.class))).thenReturn(new Project());

        editorService.updateProject(1L, projectRequest);

        verify(projectRepository).findById(1L);
        verify(projectRepository).findByName(projectRequest.getName());
        verify(projectMapper).toDtoProject(project, projectRequest);
        verify(projectRepository).save(new Project());
    }

    @Test
    void updateProjectThrowExceptionWhenProjectIsEmpty() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> editorService.updateProject(1L, projectRequest));

        verify(projectRepository).findById(anyLong());
        verify(projectRepository, never()).findByName(anyString());
        verify(projectMapper, never()).toDtoProject(any(Project.class), any(ProjectRequest.class));
        verify(projectRepository, never()).save(new Project());
    }

    @Test
    void updateProjectThrowExceptionWhenProjectNameIsExist() {
        project.setName("name");
        projectRequest.setName("name");
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(projectRepository.findByName(anyString())).thenReturn(Optional.of(project));

        assertThrows(BadCredentialsException.class, () -> editorService.updateProject(1L, projectRequest));

        verify(projectRepository).findById(anyLong());
        verify(projectRepository).findByName(anyString());
        verify(projectMapper, never()).toDtoProject(any(Project.class), any(ProjectRequest.class));
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void updateProjectThrowExceptionWhenProjectRequestNameIsEmpty() {
        projectRequest.setName("");
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(projectRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> editorService.updateProject(1L, projectRequest));

        verify(projectRepository).findById(anyLong());
        verify(projectRepository).findByName(anyString());
        verify(projectMapper, never()).toDtoProject(any(Project.class), any(ProjectRequest.class));
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void updateProjectThrowExceptionWhenProjectRequestDescIsEmpty() {
        projectRequest.setName("name");
        projectRequest.setDescription("");
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(projectRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> editorService.updateProject(anyLong(), projectRequest));

        verify(projectRepository).findById(anyLong());
        verify(projectRepository).findByName(anyString());
        verify(projectMapper, never()).toDtoProject(any(Project.class), any(ProjectRequest.class));
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void updateService() {
        servicesRequest.setName("name");
        servicesRequest.setDescription("des");

        when(servicesRepository.findById(anyLong())).thenReturn(Optional.of(services));
        when(servicesRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(servicesMapper.toDtoService(any(Services.class), any(ServicesRequest.class))).thenReturn(services);

        editorService.updateService(1L, servicesRequest);

        verify(servicesRepository).findById(anyLong());
        verify(servicesRepository).findByName(anyString());
        verify(servicesMapper).toDtoService(any(Services.class), any(ServicesRequest.class));
        verify(servicesRepository).save(new Services());
    }

    @Test
    void updateServiceThrowExceptionWhenServicesIsEmpty() {
        when(servicesRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> editorService.updateService(1L, servicesRequest));

        verify(servicesRepository).findById(anyLong());
        verify(servicesRepository, never()).findByName(anyString());
        verify(servicesMapper, never()).toDtoService(any(Services.class), any(ServicesRequest.class));
        verify(servicesRepository, never()).save(new Services());
    }

    @Test
    void updateServiceThrowExceptionWhenServiceNameIsExist() {
        services.setName("name");
        servicesRequest.setName("name");
        when(servicesRepository.findById(anyLong())).thenReturn(Optional.of(services));
        when(servicesRepository.findByName(anyString())).thenReturn(Optional.of(services));

        assertThrows(BadCredentialsException.class, () -> editorService.updateService(1L, servicesRequest));

        verify(servicesRepository).findById(anyLong());
        verify(servicesRepository).findByName(anyString());
        verify(servicesMapper, never()).toDtoService(any(Services.class), any(ServicesRequest.class));
        verify(servicesRepository, never()).save(new Services());
    }

    @Test
    void updateProjectThrowExceptionWhenServicesRequestNameIsEmpty() {
        servicesRequest.setName("");
        when(servicesRepository.findById(anyLong())).thenReturn(Optional.of(services));
        when(servicesRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> editorService.updateService(1L, servicesRequest));

        verify(servicesRepository).findById(anyLong());
        verify(servicesRepository).findByName(anyString());
        verify(servicesMapper, never()).toDtoService(any(Services.class), any(ServicesRequest.class));
        verify(servicesRepository, never()).save(new Services());
    }

    @Test
    void updateProjectThrowExceptionWhenServicesRequestDescIsEmpty() {
        servicesRequest.setName("name");
        servicesRequest.setDescription("");
        when(servicesRepository.findById(anyLong())).thenReturn(Optional.of(services));
        when(servicesRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> editorService.updateService(1L, servicesRequest));

        verify(servicesRepository).findById(anyLong());
        verify(servicesRepository).findByName(anyString());
        verify(servicesMapper, never()).toDtoService(any(Services.class), any(ServicesRequest.class));
        verify(servicesRepository, never()).save(new Services());
    }


    @Test
    void loginEditor() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void updateActivity() {
        activityRequest.setName("name");
        activityRequest.setDescription("des");
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(activity));
        when(activityRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(activityMapper.toDtoActivity(any(Activity.class), any(ActivityRequest.class), any(Image.class))).thenReturn(activity);

        editorService.updateActivity(1L, activityRequest, 1L);

        verify(imageRepository).findById(anyLong());
        verify(activityRepository).findById(anyLong());
        verify(activityRepository).findByName(anyString());
        verify(activityMapper).toDtoActivity(any(Activity.class), any(ActivityRequest.class), any(Image.class));
        verify(activityRepository).save(new Activity());
    }

    @Test
    void updateActivityThrowExceptionWhenImageIsEmpty() {
        when(imageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> editorService.updateActivity(1L, activityRequest, 1L));

        verify(imageRepository).findById(anyLong());
        verify(activityRepository, never()).findById(anyLong());
        verify(activityRepository, never()).findByName(anyString());
        verify(activityMapper, never()).toDtoActivity(any(Activity.class), any(ActivityRequest.class), any(Image.class));
        verify(activityRepository, never()).save(new Activity());
    }

    @Test
    void updateActivityThrowExceptionWhenActivityIsEmpty() {
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> editorService.updateActivity(1L, activityRequest, 1L));

        verify(imageRepository).findById(anyLong());
        verify(activityRepository).findById(anyLong());
        verify(activityRepository, never()).findByName(anyString());
        verify(activityMapper, never()).toDtoActivity(any(Activity.class), any(ActivityRequest.class), any(Image.class));
        verify(activityRepository, never()).save(new Activity());
    }

    @Test
    void updateActivityThrowExceptionWhenActivityNameIsExist() {
        activityRequest.setName("name");
        activity.setName("name");
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(activity));
        when(activityRepository.findByName(anyString())).thenReturn(Optional.of(activity));

        assertThrows(BadRequestException.class, () -> editorService.updateActivity(1L, activityRequest, 1L));

        verify(imageRepository).findById(anyLong());
        verify(activityRepository).findById(anyLong());
        verify(activityRepository).findByName(anyString());
        verify(activityMapper, never()).toDtoActivity(any(Activity.class), any(ActivityRequest.class), any(Image.class));
        verify(activityRepository, never()).save(new Activity());
    }

    @Test
    void updateActivityThrowExceptionWhenActivityRequestNameIsEmpty() {
        activityRequest.setName("");
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(activity));
        when(activityRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> editorService.updateActivity(1L, activityRequest, 1L));

        verify(imageRepository).findById(anyLong());
        verify(activityRepository).findById(anyLong());
        verify(activityRepository).findByName(anyString());
        verify(activityMapper, never()).toDtoActivity(any(Activity.class), any(ActivityRequest.class), any(Image.class));
        verify(activityRepository, never()).save(new Activity());
    }

    @Test
    void updateActivityThrowExceptionWhenActivityRequestDescIsEmpty() {
        activityRequest.setName("name");
        activityRequest.setDescription("");
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(activity));
        when(activityRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> editorService.updateActivity(1L, activityRequest, 1L));

        verify(imageRepository).findById(anyLong());
        verify(activityRepository).findById(anyLong());
        verify(activityRepository).findByName(anyString());
        verify(activityMapper, never()).toDtoActivity(any(Activity.class), any(ActivityRequest.class), any(Image.class));
        verify(activityRepository, never()).save(new Activity());
    }


    @Test
    void updateSponsorship() {
        sponsorshipRequest.setInn("inn");
        sponsorshipRequest.setBank("bank");
        sponsorshipRequest.setAddress("address");
        sponsorshipRequest.setPaymentAccount("acc");
        sponsorshipRequest.setCompany("company");
        sponsorshipRequest.setDirector("director");

        when(sponsorshipRepository.findById(anyLong())).thenReturn(Optional.of(sponsorship));
        when(sponsorshipMapper.toDtoSponsorship(any(Sponsorship.class), any(SponsorshipRequest.class))).thenReturn(sponsorship);

        editorService.updateSponsorship(1L, sponsorshipRequest);

        verify(sponsorshipRepository).findById(anyLong());
        verify(sponsorshipMapper).toDtoSponsorship(any(Sponsorship.class), any(SponsorshipRequest.class));
        verify(sponsorshipRepository).save(new Sponsorship());
    }

    @Test
    void updateSponsorshipThrowExceptionWhenSponsorshipIsEmpty() {
        when(sponsorshipRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> editorService.updateSponsorship(1L, sponsorshipRequest));

        verify(sponsorshipRepository).findById(anyLong());
        verify(sponsorshipMapper, never()).toDtoSponsorship(any(Sponsorship.class), any(SponsorshipRequest.class));
        verify(sponsorshipRepository, never()).save(new Sponsorship());
    }
    @Test
    void updateSponsorshipThrowsExceptions() {
        for(int i = 0; i < 7; i++) {
            when(sponsorshipRepository.findById(anyLong())).thenReturn(Optional.of(sponsorship));
            if(i == 0) {
                sponsorshipRequest.setInn("");
            } else if (i == 1) {
                sponsorshipRequest.setInn("inn");
                sponsorshipRequest.setBank("");
            } else if (i == 2) {
                sponsorshipRequest.setBank("bank");
                sponsorshipRequest.setAddress("");
            } else if (i == 3) {
                sponsorshipRequest.setAddress("address");
                sponsorshipRequest.setPaymentAccount("");
            } else if (i == 4) {
                sponsorshipRequest.setPaymentAccount("acc");
                sponsorshipRequest.setCompany("");
            } else {
                sponsorshipRequest.setCompany("company");
                sponsorshipRequest.setDirector("");
            }

            assertThrows(BadRequestException.class, () -> editorService.updateSponsorship(1L, sponsorshipRequest));

        }
        verify(sponsorshipRepository, times(7)).findById(anyLong());
        verify(sponsorshipMapper, never()).toDtoSponsorship(any(Sponsorship.class), any(SponsorshipRequest.class));
        verify(sponsorshipRepository, never()).save(new Sponsorship());
    }

    @Test
    void updateEvent() {
        
    }
}