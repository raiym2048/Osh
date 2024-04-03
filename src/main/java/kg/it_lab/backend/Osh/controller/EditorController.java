package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.auth.AuthLoginResponse;
import kg.it_lab.backend.Osh.dto.auth.EditorPasswordRequest;
import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.admin.AdminLoginRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.service.EditorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/editor")
public class EditorController {
    private final EditorService editorService;
    @PutMapping("/service/updateById/{id}")
    public MyData updateService(@PathVariable Long id, @RequestBody ServicesRequest servicesRequest){
        editorService.updateService(id, servicesRequest);
        MyData data = new MyData();
        data.setMessage("Service updated successfully");
        return data;
    }
    @PutMapping("/project/updateById/{projectId}")
    public MyData updateProject(@PathVariable Long projectId, @RequestBody ProjectRequest projectRequest ){
        editorService.updateProject(projectId, projectRequest );
        MyData data = new MyData();
        data.setMessage("Project updated successfully");
        return data;
    }
    @PutMapping("/news/updateById/{newsId}/{imageId}")
    public MyData updateById(@PathVariable Long newsId, @RequestBody NewsRequest newsRequest , @PathVariable Long imageId) {
        editorService.updateById(newsId, newsRequest , imageId);
        MyData data = new MyData();
        data.setMessage("News updated successfully");
        return data;
    }
    @PutMapping("/event/updateById/{eventId}/{imageId}")
    public MyData update(@PathVariable Long eventId, @RequestBody EventRequest eventRequest , @PathVariable Long imageId) {
        editorService.updateEvent(eventId, eventRequest , imageId);
        MyData data = new MyData();
        data.setMessage("Event updated successfully");
        return data;
    }
    @PutMapping("/activity/updateById/{activityId}/{imageId}")
    public MyData updateActivity(@PathVariable Long activityId, @RequestBody ActivityRequest activityRequest , @PathVariable Long imageId) {
        editorService.updateActivity(activityId, activityRequest , imageId);
        MyData data = new MyData();
        data.setMessage("Activity updated successfully");
        return data;
    }
    @PostMapping("/login")
    public AuthLoginResponse loginEditor(@RequestBody AdminLoginRequest adminLoginRequest){
        return editorService.loginEditor(adminLoginRequest);
    }
    @PostMapping("/changePassword")
    public MyData changePassword(@RequestHeader("Authorization")String token ,  @RequestBody EditorPasswordRequest editorPasswordRequest){

        editorService.changePassword( token , editorPasswordRequest);

        MyData data = new MyData();
        data.setMessage("Your password changed successfully");
        return data;
    }



}
