package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.admin.RegisterRequest;
import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;

import kg.it_lab.backend.Osh.dto.numbers.NumbersRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.role.RoleRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.service.admin.*;

import kg.it_lab.backend.Osh.service.VolunteerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;


@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final AdminNewsService adminNewsService;
    private final AdminEventService adminEventService;
    private final AdminProjectService adminProjectService;
    private final AdminServicesService adminServicesService;
    private final AdminSponsorshipService adminSponsorshipService;
    private final AdminActivityService adminActivityService;
    private final AdminNumbersService adminNumbersService;
    private final MessageSource messageSource;
    private final AdminPartnersService adminPartnersService;


    private final VolunteerService volunteerService;
    @PostMapping("/news/add/{imageId}")
    public MyData add(@RequestBody NewsRequest newsRequest,
                      @PathVariable Long imageId,
                      @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminNewsService.add(newsRequest , imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("news.add", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/news/updateById/{newsId}/{imageId}")
    public MyData updateById(@PathVariable Long newsId,
                             @RequestBody NewsRequest newsRequest,
                             @PathVariable Long imageId,
                             @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        adminNewsService.updateById(newsId, newsRequest , imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("news.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @DeleteMapping("/news/deleteById/{newsId}")
    public MyData deleteByName(@PathVariable Long newsId,
                               @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        adminNewsService.deleteById(newsId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("news.delete", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PostMapping("/event/add/{imageId}")
    public MyData add( @RequestBody EventRequest eventRequest,
                       @PathVariable Long imageId,
                       @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminEventService.addEvent(eventRequest ,imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("event.add", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/event/updateById/{eventId}/{imageId}")
    public MyData update(@PathVariable Long eventId,
                         @RequestBody EventRequest eventRequest ,
                         @PathVariable Long imageId,
                         @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        adminEventService.updateEvent(eventId, eventRequest , imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("event.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @DeleteMapping("/event/deleteById/{eventId}")
    public MyData delete(@PathVariable Long eventId,
                         @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminEventService.deleteEvent(eventId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("event.delete", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PostMapping("/registerUser")
    public MyData registerEditor(@RequestBody RegisterRequest editorRegisterRequest,
                                 @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminService.registerEditor(editorRegisterRequest);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("password.sent", null, LocaleContextHolder.getLocale()) + " " + editorRegisterRequest.getEmail()+" !");
        return data;
    }
    @PostMapping("/role/add")
    public MyData addRole(@RequestBody RoleRequest roleRequest,
                          @RequestHeader(name = "Accept-Language", required = false) Locale locale){

        adminService.addRole(roleRequest);
        MyData data = new MyData();
            data.setMessage(messageSource.getMessage("role.add", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @DeleteMapping("/role/delete/{id}")
    public MyData deleteRole(@PathVariable Long id,
                             @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminService.deleteRole(id);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("role.delete", null, LocaleContextHolder.getLocale()));
        return data;

    }

    @PostMapping("/project/add")
    public MyData addProject(@RequestBody ProjectRequest projectRequest,
                             @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminProjectService.addProject(projectRequest );
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("project.add" , null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/project/updateById/{projectId}")
    public MyData updateProject(@PathVariable Long projectId,
                                @RequestBody ProjectRequest projectRequest,
                                @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminProjectService.updateProject(projectId, projectRequest );
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("project.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @DeleteMapping("/project/deleteById/{id}")
    public MyData deleteProject(@PathVariable Long id,
                                @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        adminProjectService.deleteProject(id);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("project.delete", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PostMapping("/project/attachImageToProject/{projectId}/image/{imageId}")
    public MyData attachImageToProject(@PathVariable Long projectId,
                                       @PathVariable Long imageId,
                                       @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        adminProjectService.attachImageToProject(projectId, imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("image.attach.project", null, LocaleContextHolder.getLocale()));
        return data;
    }

    @PostMapping("/services/add")
    public MyData addService(@RequestBody ServicesRequest servicesRequest,
                             @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminServicesService.addService(servicesRequest);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("service.add", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/services/updateById/{id}")
    public MyData updateService(@PathVariable Long id,
                                @RequestBody ServicesRequest servicesRequest,
                                @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminServicesService.updateService(id, servicesRequest);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("service.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @DeleteMapping("/services/deleteById/{id}")
    public MyData deleteService(@PathVariable Long id,
                                @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        adminServicesService.deleteService(id);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("service.delete", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PostMapping("/services/attachImageToService/{serviceId}/image/{imageId}")
    public MyData attachImageToService(@PathVariable Long serviceId,
                                       @PathVariable Long imageId,
                                       @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        adminServicesService.attachImageToService(serviceId, imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("image.attach.service", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PostMapping("/activity/add/{imageId}")
    public MyData addActivity(@RequestBody ActivityRequest activityRequest ,
                              @PathVariable Long imageId,
                              @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminActivityService.addActivity(activityRequest , imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("activity.add", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/activity/updateById/{activityId}/{imageId}")
    public MyData updateActivity(@PathVariable Long activityId,
                                 @RequestBody ActivityRequest activityRequest ,
                                 @PathVariable Long imageId,
                                 @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        adminActivityService.updateActivity(activityId, activityRequest , imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("activity.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @DeleteMapping("/activity/deleteById/{id}")
    public MyData deleteActivity(@PathVariable Long id,
                                 @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        adminActivityService.deleteActivity(id);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("activity.delete", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PostMapping("/sponsorship/add")
        public MyData addSponsorship(@RequestBody SponsorshipRequest sponsorshipRequest,
                                     @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminSponsorshipService.addSponsorship(sponsorshipRequest);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("sponsorship.add", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/sponsorship/updateById/{id}")
    public MyData updateSponsorship(@PathVariable Long id ,
                                    @RequestBody SponsorshipRequest sponsorshipRequest,
                                    @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminSponsorshipService.updateSponsorship( id , sponsorshipRequest);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("sponsorship.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @DeleteMapping("/sponsorship/deleteById/{id}")
    public MyData deleteSponsorship(@PathVariable Long id ,
                                    @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminSponsorshipService.deleteSponsorship(id);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("sponsorship.delete", null, LocaleContextHolder.getLocale()));
        return data;

    }
    @PostMapping("/numbers/add")
    public MyData addNumbers(@RequestBody NumbersRequest numbersRequest,
                             @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminNumbersService.addNumbers(numbersRequest);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("numbers.add", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/numbers/updateById/{id}")
    public MyData updateNumbers(@PathVariable Long id ,
                                @RequestBody NumbersRequest numbersRequest,
                                @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminNumbersService.updateNumbers(id , numbersRequest);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("numbers.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @DeleteMapping("/numbers/deleteById/{id}")
    public MyData deleteNumbersById(@PathVariable Long id,
                                    @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminNumbersService.deleteNumbersById(id);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("numbers.delete", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PostMapping("/partners/add/{imageId}")
    public MyData addPartners(@PathVariable Long imageId){
        adminPartnersService.addPartners(imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("partners.add", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @DeleteMapping("/partners/deleteById/{id}")
    public MyData deletePartners(@PathVariable Long id , @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        adminPartnersService.deletePartners(id);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("partners.delete", null, LocaleContextHolder.getLocale()));
        return data;
    }

    @PostMapping("/volunteer/accept/{volunteerId}")
    public ResponseEntity<?> acceptVolunteer(@PathVariable Long volunteerId) {
        adminService.acceptVolunteer(volunteerId);
        return new ResponseEntity<>("Volunteer is successfully accept", HttpStatus.OK);
    }

    @DeleteMapping("/volunteer/reject/{volunteerId}")
    public ResponseEntity<?> rejectVolunteer(@PathVariable Long volunteerId) {
        adminService.rejectVolunteer(volunteerId);
        return new ResponseEntity<>("Volunteer is rejected", HttpStatus.OK);
    }
}
