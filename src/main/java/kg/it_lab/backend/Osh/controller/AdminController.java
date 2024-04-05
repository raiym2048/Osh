package kg.it_lab.backend.Osh.controller;

import com.amazonaws.services.dynamodbv2.xspec.M;
import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.admin.EditorRegisterRequest;
import kg.it_lab.backend.Osh.dto.auth.MyData;
import kg.it_lab.backend.Osh.dto.admin.category.CategoryRequest;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;

import kg.it_lab.backend.Osh.dto.numbers.NumbersRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.role.RoleRequest;
import kg.it_lab.backend.Osh.dto.service.ServicesRequest;
import kg.it_lab.backend.Osh.dto.sponsorship.SponsorshipRequest;
import kg.it_lab.backend.Osh.service.AdminService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    @PostMapping("/news/add/{imageId}")
    public MyData add(@RequestBody NewsRequest newsRequest ,@PathVariable Long imageId ){
        adminService.add(newsRequest , imageId);
        MyData data = new MyData();
        data.setMessage("News added successfully");
        return data;
    }
    @PutMapping("/news/updateById/{newsId}/{imageId}")
    public MyData updateById(@PathVariable Long newsId, @RequestBody NewsRequest newsRequest , @PathVariable Long imageId) {
        adminService.updateById(newsId, newsRequest , imageId);
        MyData data = new MyData();
        data.setMessage("News updated successfully");
        return data;
    }
    @DeleteMapping("/news/deleteById/{newsId}")
    public MyData deleteByName(@PathVariable Long newsId) {
        adminService.deleteById(newsId);
        MyData data = new MyData();
        data.setMessage("News deleted successfully");
        return data;
    }
    @PostMapping("/event/add/{imageId}")
    public MyData add( @RequestBody EventRequest eventRequest ,@PathVariable Long imageId){
        adminService.addEvent(eventRequest ,imageId);
        MyData data = new MyData();
        data.setMessage("Event added successfully");
        return data;
    }
    @PutMapping("/event/updateById/{eventId}/{imageId}")
    public MyData update(@PathVariable Long eventId, @RequestBody EventRequest eventRequest ,@PathVariable Long imageId) {
        adminService.updateEvent(eventId, eventRequest , imageId);
        MyData data = new MyData();
        data.setMessage("Event updated successfully");
        return data;
    }
    @DeleteMapping("/event/deleteById/{eventId}")
    public MyData delete(@PathVariable Long eventId){
        adminService.deleteEvent(eventId);
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
    @DeleteMapping("/category/deleteById/{id}")
    public MyData deleteCategory(@PathVariable Long id){
        adminService.deleteCategory(id);
        MyData data = new MyData();
        data.setMessage("Category was successfully deleted");
        return data;
    }
    @PostMapping("/registerEditor")
    public MyData registerEditor(@RequestBody EditorRegisterRequest editorRegisterRequest){
        adminService.registerEditor(editorRegisterRequest);
        MyData data = new MyData();
        data.setMessage("Your password was sent to "+ editorRegisterRequest.getEmail()+" !");
        return data;
    }
    @PostMapping("/role/add")
    public MyData addRole(@RequestBody RoleRequest roleRequest){

        adminService.addRole(roleRequest);
        MyData data = new MyData();
        data.setMessage("Role added successfully");
        return data;
    }
    @DeleteMapping("/role/delete/{id}")
    public MyData deleteRole(@PathVariable Long id ){
        adminService.deleteRole(id);
        MyData data = new MyData();
        data.setMessage("Role deleted successfully");
        return data;

    }

    @PostMapping("/project/add")
    public MyData addProject(@RequestBody ProjectRequest projectRequest ){
        adminService.addProject(projectRequest );
        MyData data = new MyData();
        data.setMessage("Project added successfully");
        return data;
    }
    @PutMapping("/project/updateById/{projectId}")
    public MyData updateProject(@PathVariable Long projectId, @RequestBody ProjectRequest projectRequest ){
        adminService.updateProject(projectId, projectRequest );
        MyData data = new MyData();
        data.setMessage("Project updated successfully");
        return data;
    }
    @DeleteMapping("/project/deleteById/{id}")
    public MyData deleteProject(@PathVariable Long id) {
        adminService.deleteProject(id);
        MyData data = new MyData();
        data.setMessage("Project deleted successfully");
        return data;
    }
    @PostMapping("/project/attachImageToProject/{projectId}/image/{imageId}")
    public MyData attachImageToProject(@PathVariable Long projectId, @PathVariable Long imageId) {
        adminService.attachImageToProject(projectId, imageId);
        MyData data = new MyData();
        data.setMessage("Image attached to project successfully");
        return data;
    }

    @PostMapping("/services/add")
    public MyData addService(@RequestBody ServicesRequest servicesRequest){
        adminService.addService(servicesRequest);
        MyData data = new MyData();
        data.setMessage("Services added successfully");
        return data;
    }
    @PutMapping("/services/updateById/{id}")
    public MyData updateService(@PathVariable Long id, @RequestBody ServicesRequest servicesRequest){
        adminService.updateService(id, servicesRequest);
        MyData data = new MyData();
        data.setMessage("Services updated successfully");
        return data;
    }
    @DeleteMapping("/services/deleteById/{id}")
    public MyData deleteService(@PathVariable Long id) {
        adminService.deleteService(id);
        MyData data = new MyData();
        data.setMessage("Services deleted successfully");
        return data;
    }
    @PostMapping("/services/attachImageToService/{serviceId}/image/{imageId}")
    public MyData attachImageToService(@PathVariable Long serviceId, @PathVariable Long imageId) {
        adminService.attachImageToService(serviceId, imageId);
        MyData data = new MyData();
        data.setMessage("Image attached to services successfully");
        return data;
    }
    @PostMapping("/activity/add/{imageId}")
    public MyData addActivity(@RequestBody ActivityRequest activityRequest , @PathVariable Long imageId ){
        adminService.addActivity(activityRequest , imageId);
        MyData data = new MyData();
        data.setMessage("Activity added successfully");
        return data;
    }
    @PutMapping("/activity/updateById/{activityId}/{imageId}")
    public MyData updateActivity(@PathVariable Long activityId, @RequestBody ActivityRequest activityRequest , @PathVariable Long imageId) {
        adminService.updateActivity(activityId, activityRequest , imageId);
        MyData data = new MyData();
        data.setMessage("Activity updated successfully");
        return data;
    }
    @DeleteMapping("/activity/deleteById/{id}")
    public MyData deleteActivity(@PathVariable Long id) {
        adminService.deleteActivity(id);
        MyData data = new MyData();
        data.setMessage("Activity deleted successfully");
        return data;
    }
    @PostMapping("/sponsorship/add")
        public MyData addSponsorship(@RequestBody SponsorshipRequest sponsorshipRequest){
        adminService.addSponsorship(sponsorshipRequest);
        MyData data = new MyData();
        data.setMessage("Sponsorship added successfully");
        return data;
    }
    @PutMapping("/sponsorship/updateById/{id}")
    public MyData updateSponsorship(@PathVariable Long id , @RequestBody SponsorshipRequest sponsorshipRequest){
        adminService.updateSponsorship( id , sponsorshipRequest);
        MyData data = new MyData();
        data.setMessage("Sponsorship updated successfully");
        return data;
    }
    @DeleteMapping("/sponsorship/deleteById/{id}")
    public MyData deleteSponsorship(@PathVariable Long id ){
        adminService.deleteSponsorship(id);
        MyData data = new MyData();
        data.setMessage("Sponsorship deleted successfully");
        return data;

    }
    @PostMapping("/numbers/add")
    public MyData addNumbers(@RequestBody NumbersRequest numbersRequest){
        adminService.addNumbers(numbersRequest);
        MyData data = new MyData();
        data.setMessage("Numbers added successfully");
        return data;
    }
    @PutMapping("/numbers/updateById/{id}")
    public MyData updateNumbers(@PathVariable Long id ,@RequestBody NumbersRequest numbersRequest){
        adminService.updateNumbers(id , numbersRequest);
        MyData data = new MyData();
        data.setMessage("Numbers updated successfully");
        return data;
    }
    @DeleteMapping("/numbers/deleteById/{id}")
    public MyData deleteNumbersById(@PathVariable Long id ){
        adminService.deleteNumbersById(id);
        MyData data = new MyData();
        data.setMessage("Numbers deleted successfully");
        return data;
    }


}
