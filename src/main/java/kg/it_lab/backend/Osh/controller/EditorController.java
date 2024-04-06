package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.hub.HubRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.service.EditorService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@AllArgsConstructor
@RequestMapping("/editor")
public class EditorController {
    private final EditorService editorService;
    private final MessageSource messageSource;
    @PutMapping("/service/updateById/{id}")
    public MyData updateService(@PathVariable Long id,
                                @RequestBody ServicesRequest servicesRequest,
                                @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        editorService.updateService(id, servicesRequest);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("service.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/project/updateById/{projectId}")
    public MyData updateProject(@PathVariable Long projectId,
                                @RequestBody ProjectRequest projectRequest ,
                                @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        editorService.updateProject(projectId, projectRequest );
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("project.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/news/updateById/{newsId}/{imageId}")
    public MyData updateById(@PathVariable Long newsId,
                             @RequestBody NewsRequest newsRequest ,
                             @PathVariable Long imageId,
                             @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        editorService.updateById(newsId, newsRequest , imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("news.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/event/updateById/{eventId}/{imageId}")
    public MyData update(@PathVariable Long eventId,
                         @RequestBody EventRequest eventRequest ,
                         @PathVariable Long imageId,
                         @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        editorService.updateEvent(eventId, eventRequest , imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("event.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/activity/updateById/{activityId}/{imageId}")
    public MyData updateActivity(@PathVariable Long activityId,
                                 @RequestBody ActivityRequest activityRequest ,
                                 @PathVariable Long imageId,
                                 @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        editorService.updateActivity(activityId, activityRequest , imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("activity.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/sponsorship/updateById/{id}/{imageId}")
    public MyData updateSponsorship(@PathVariable Long id ,
                                    @RequestBody SponsorshipRequest sponsorshipRequest,
                                    @PathVariable Long imageId,
                                    @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        editorService.updateSponsorship(id , sponsorshipRequest, imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("sponsorship.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/hub/updateById/{id}")
    public MyData updateHub(@PathVariable Long id , @RequestBody HubRequest hubRequest , @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        editorService.updateHub(id , hubRequest);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("hub.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PutMapping("/partners/updateById/{id}/{imageId}")
    public MyData updatePartners(@PathVariable Long id , @PathVariable Long imageId , @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        editorService.updatePartners(id , imageId);
        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("partners.update", null, LocaleContextHolder.getLocale()));
        return data;
    }
    @PostMapping("/login")
    public AuthLoginResponse loginEditor(@RequestBody AdminLoginRequest adminLoginRequest,
                                         @RequestHeader(name = "Accept-Language", required = false) Locale locale){
        return editorService.loginEditor(adminLoginRequest);
    }
    @PutMapping("/changePassword/{editorEmail}")
    public MyData changePassword(@RequestHeader("Authorization")String token ,
                                 @RequestBody EditorPasswordRequest editorPasswordRequest,
                                 @PathVariable String editorEmail,
                                 @RequestHeader(name = "Accept-Language", required = false) Locale locale){

        editorService.changePassword(token , editorPasswordRequest, editorEmail);

        MyData data = new MyData();
        data.setMessage(messageSource.getMessage("password.change", null, LocaleContextHolder.getLocale()));
        return data;
    }



}
