package kg.it_lab.backend.Osh.controller;

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
import kg.it_lab.backend.Osh.service.admin.*;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



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

    @PostMapping("/news/add/{imageId}")
    public MyData add(@RequestBody NewsRequest newsRequest ,@PathVariable Long imageId ){
        adminNewsService.add(newsRequest , imageId);
        MyData data = new MyData();
        data.setMessage("News added successfully");
        return data;
    }
    @PutMapping("/news/updateById/{newsId}/{imageId}")
    public MyData updateById(@PathVariable Long newsId, @RequestBody NewsRequest newsRequest , @PathVariable Long imageId) {
        adminNewsService.updateById(newsId, newsRequest , imageId);
        MyData data = new MyData();
        data.setMessage("News updated successfully");
        return data;
    }
    @DeleteMapping("/news/deleteById/{newsId}")
    public MyData deleteByName(@PathVariable Long newsId) {
        adminNewsService.deleteById(newsId);
        MyData data = new MyData();
        data.setMessage("News deleted successfully");
        return data;
    }
    @PostMapping("/event/add/{imageId}")
    public MyData add( @RequestBody EventRequest eventRequest ,@PathVariable Long imageId){
        adminEventService.addEvent(eventRequest ,imageId);
        MyData data = new MyData();
        data.setMessage("Event added successfully");
        return data;
    }
    @PutMapping("/event/updateById/{eventId}/{imageId}")
    public MyData update(@PathVariable Long eventId, @RequestBody EventRequest eventRequest ,@PathVariable Long imageId) {
        adminEventService.updateEvent(eventId, eventRequest , imageId);
        MyData data = new MyData();
        data.setMessage("Event updated successfully");
        return data;
    }
    @DeleteMapping("/event/deleteById/{eventId}")
    public MyData delete(@PathVariable Long eventId){
        adminEventService.deleteEvent(eventId);
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
        adminProjectService.addProject(projectRequest );
        MyData data = new MyData();
        data.setMessage("Project added successfully");
        return data;
    }
    @PutMapping("/project/updateById/{projectId}")
    public MyData updateProject(@PathVariable Long projectId, @RequestBody ProjectRequest projectRequest ){
        adminProjectService.updateProject(projectId, projectRequest );
        MyData data = new MyData();
        data.setMessage("Project updated successfully");
        return data;
    }
    @DeleteMapping("/project/deleteById/{id}")
    public MyData deleteProject(@PathVariable Long id) {
        adminProjectService.deleteProject(id);
        MyData data = new MyData();
        data.setMessage("Project deleted successfully");
        return data;
    }
    @PostMapping("/project/attachImageToProject/{projectId}/image/{imageId}")
    public MyData attachImageToProject(@PathVariable Long projectId, @PathVariable Long imageId) {
        adminProjectService.attachImageToProject(projectId, imageId);
        MyData data = new MyData();
        data.setMessage("Image attached to project successfully");
        return data;
    }

    @PostMapping("/services/add")
    public MyData addService(@RequestBody ServicesRequest servicesRequest){
        adminServicesService.addService(servicesRequest);
        MyData data = new MyData();
        data.setMessage("Services added successfully");
        return data;
    }
    @PutMapping("/services/updateById/{id}")
    public MyData updateService(@PathVariable Long id, @RequestBody ServicesRequest servicesRequest){
        adminServicesService.updateService(id, servicesRequest);
        MyData data = new MyData();
        data.setMessage("Services updated successfully");
        return data;
    }
    @DeleteMapping("/services/deleteById/{id}")
    public MyData deleteService(@PathVariable Long id) {
        adminServicesService.deleteService(id);
        MyData data = new MyData();
        data.setMessage("Services deleted successfully");
        return data;
    }
    @PostMapping("/services/attachImageToService/{serviceId}/image/{imageId}")
    public MyData attachImageToService(@PathVariable Long serviceId, @PathVariable Long imageId) {
        adminServicesService.attachImageToService(serviceId, imageId);
        MyData data = new MyData();
        data.setMessage("Image attached to services successfully");
        return data;
    }
    @PostMapping("/activity/add/{imageId}")
    public MyData addActivity(@RequestBody ActivityRequest activityRequest , @PathVariable Long imageId ){
        adminActivityService.addActivity(activityRequest , imageId);
        MyData data = new MyData();
        data.setMessage("Activity added successfully");
        return data;
    }
    @PutMapping("/activity/updateById/{activityId}/{imageId}")
    public MyData updateActivity(@PathVariable Long activityId, @RequestBody ActivityRequest activityRequest , @PathVariable Long imageId) {
        adminActivityService.updateActivity(activityId, activityRequest , imageId);
        MyData data = new MyData();
        data.setMessage("Activity updated successfully");
        return data;
    }
    @DeleteMapping("/activity/deleteById/{id}")
    public MyData deleteActivity(@PathVariable Long id) {
        adminActivityService.deleteActivity(id);
        MyData data = new MyData();
        data.setMessage("Activity deleted successfully");
        return data;
    }
    @PostMapping("/sponsorship/add/{imageId}")
        public MyData addSponsorship(@RequestBody SponsorshipRequest sponsorshipRequest, @PathVariable Long imageId){
        adminSponsorshipService.addSponsorship(sponsorshipRequest, imageId);
        MyData data = new MyData();
        data.setMessage("Sponsorship added successfully");
        return data;
    }
    @PutMapping("/sponsorship/updateById/{id}/{imageId}/")
    public MyData updateSponsorship(@PathVariable Long id , @RequestBody SponsorshipRequest sponsorshipRequest, @PathVariable Long imageId){
        adminSponsorshipService.updateSponsorship( id , sponsorshipRequest, imageId);
        MyData data = new MyData();
        data.setMessage("Sponsorship updated successfully");
        return data;
    }
    @DeleteMapping("/sponsorship/deleteById/{id}")
    public MyData deleteSponsorship(@PathVariable Long id ){
        adminSponsorshipService.deleteSponsorship(id);
        MyData data = new MyData();
        data.setMessage("Sponsorship deleted successfully");
        return data;

    }
    @PostMapping("/numbers/add")
    public MyData addNumbers(@RequestBody NumbersRequest numbersRequest){
        adminNumbersService.addNumbers(numbersRequest);
        MyData data = new MyData();
        data.setMessage("Numbers added successfully");
        return data;
    }
    @PutMapping("/numbers/updateById/{id}")
    public MyData updateNumbers(@PathVariable Long id ,@RequestBody NumbersRequest numbersRequest){
        adminNumbersService.updateNumbers(id , numbersRequest);
        MyData data = new MyData();
        data.setMessage("Numbers updated successfully");
        return data;
    }
    @DeleteMapping("/numbers/deleteById/{id}")
    public MyData deleteNumbersById(@PathVariable Long id ){
        adminNumbersService.deleteNumbersById(id);
        MyData data = new MyData();
        data.setMessage("Numbers deleted successfully");
        return data;
    }
}
