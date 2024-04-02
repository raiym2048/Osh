package kg.it_lab.backend.Osh.controller;

import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.admin.EditorRegisterRequest;
import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.admin.category.CategoryRequest;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;

import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.role.RoleRequest;
import kg.it_lab.backend.Osh.dto.service.ServiceRequest;
import kg.it_lab.backend.Osh.service.AdminService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    @PostMapping("/news/add/{imageName}")
    public MyData add(@RequestBody NewsRequest newsRequest ,@PathVariable String imageName ){
        adminService.add(newsRequest , imageName);
        MyData data = new MyData();
        data.setMessage("News added successfully");
        return data;
    }
    @PutMapping("/news/updateByName/{newsName}/{imageName}")
    public MyData updateByName(@PathVariable String newsName, @RequestBody NewsRequest newsRequest , @PathVariable String imageName) {
        adminService.updateByName(newsName, newsRequest , imageName);
        MyData data = new MyData();
        data.setMessage("News updated successfully");
        return data;
    }
    @DeleteMapping("/news/deleteByName/{name}")
    public MyData deleteByName(@PathVariable String name) {
        adminService.deleteByName(name);
        MyData data = new MyData();
        data.setMessage("News deleted successfully");
        return data;
    }
    @PostMapping("/event/add/{imageName}")
    public MyData add( @RequestBody EventRequest eventRequest ,@PathVariable String imageName){
        adminService.addEvent(eventRequest , imageName);
        MyData data = new MyData();
        data.setMessage("Event added successfully");
        return data;
    }
    @PutMapping("/event/updateByName/{eventName}/{imageName}")
    public MyData update(@PathVariable String eventName, @RequestBody EventRequest eventRequest ,@PathVariable String imageName) {
        adminService.updateEvent(eventName, eventRequest , imageName);
        MyData data = new MyData();
        data.setMessage("Event updated successfully");
        return data;
    }
    @DeleteMapping("/event/deleteByName/{name}")
    public MyData delete(@PathVariable String name){
        adminService.deleteEvent(name);
        MyData data = new MyData();
        data.setMessage("Event deleted successfully");
        return data;
    }
    @PostMapping("/category/add")
    public MyData addCategory(@RequestBody CategoryRequest categoryRequest){
        adminService.addCategory(categoryRequest);
        MyData data = new MyData();
        data.setMessage("Category added successfully");
        return data;
    }
    @DeleteMapping("/category/deleteByName/{name}")
    public MyData deleteCategory(@PathVariable String name){
        adminService.deleteCategory(name);
        MyData data = new MyData();
        data.setMessage("Category was successfully deleted");
        return data;
    }
    @PostMapping("/register")
    public MyData registerEditor(@RequestBody EditorRegisterRequest editorRegisterRequest){
        adminService.registerEditor(editorRegisterRequest);
        MyData data = new MyData();
        data.setMessage("Your new login and password was sent to "+ editorRegisterRequest.getEmail()+" !");
        return data;
    }
    @PostMapping("/role/add")
    public MyData addRole(@RequestBody RoleRequest roleRequest){

        adminService.addRole(roleRequest);
        MyData data = new MyData();
        data.setMessage("Role added successfully");
        return data;
    }
    @PostMapping("/project/add")
    public MyData addProject(@RequestBody ProjectRequest projectRequest ){
        adminService.addProject(projectRequest );
        MyData data = new MyData();
        data.setMessage("Project added successfully");
        return data;
    }
    @PutMapping("/project/updateByName/{projectName}")
    public MyData updateProject(@PathVariable String projectName, @RequestBody ProjectRequest projectRequest ){
        adminService.updateProject(projectName, projectRequest );
        MyData data = new MyData();
        data.setMessage("Project updated successfully");
        return data;
    }
    @DeleteMapping("/project/deleteByName/{name}")
    public MyData deleteProject(@PathVariable String name) {
        adminService.deleteProject(name);
        MyData data = new MyData();
        data.setMessage("Project deleted successfully");
        return data;
    }
    @PostMapping("/service/add")
    public MyData addService(@RequestBody ServiceRequest serviceRequest ){
        adminService.addService(serviceRequest);
        MyData data = new MyData();
        data.setMessage("Service added successfully");
        return data;
    }
    @PutMapping("/service/updateByName/{name}")
    public MyData updateService(@PathVariable String serviceName, @RequestBody ServiceRequest serviceRequest ){
        adminService.updateService(serviceName, serviceRequest );
        MyData data = new MyData();
        data.setMessage("Service updated successfully");
        return data;
    }
    @DeleteMapping("/service/deleteByName/{name}")
    public MyData deleteService(@PathVariable String name) {
        adminService.deleteService(name);
        MyData data = new MyData();
        data.setMessage("Service deleted successfully");
        return data;
    }
    @PostMapping("/activity/add/{imageName}")
    public MyData addActivity(@RequestBody ActivityRequest activityRequest , @PathVariable String imageName ){
        adminService.addActivity(activityRequest , imageName);
        MyData data = new MyData();
        data.setMessage("Activity added successfully");
        return data;
    }
    @PutMapping("/activity/updateByName/{activityName}/{imageName}")
    public MyData updateActivity(@PathVariable String activityName, @RequestBody ActivityRequest activityRequest , @PathVariable String imageName) {
        adminService.updateActivity(activityName, activityRequest , imageName);
        MyData data = new MyData();
        data.setMessage("Activity updated successfully");
        return data;
    }
    @DeleteMapping("/activity/deleteByName/{name}")
    public MyData deleteActivity(@PathVariable String name) {
        adminService.deleteActivity(name);
        MyData data = new MyData();
        data.setMessage("Activity deleted successfully");
        return data;
    }

}
