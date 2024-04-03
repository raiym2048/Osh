package kg.it_lab.backend.Osh.service;


import kg.it_lab.backend.Osh.dto.activity.ActivityRequest;
import kg.it_lab.backend.Osh.dto.admin.EditorRegisterRequest;
import kg.it_lab.backend.Osh.dto.admin.category.CategoryRequest;
import kg.it_lab.backend.Osh.dto.event.EventRequest;
import kg.it_lab.backend.Osh.dto.news.NewsRequest;
import kg.it_lab.backend.Osh.dto.project.ProjectRequest;
import kg.it_lab.backend.Osh.dto.role.RoleRequest;
import kg.it_lab.backend.Osh.dto.service.ServiceRequest;

public interface AdminService {
    void add(NewsRequest newsRequest , Long  imageId);

    void updateById(Long id, NewsRequest newsRequest , Long imageId);

    void deleteById(Long id);
    void addEvent(EventRequest eventRequest , Long imageId );
    void updateEvent(Long eventId , EventRequest eventRequest ,   Long imageId );
    void deleteEvent(Long id);
    void addCategory(CategoryRequest categoryRequest);
    void deleteCategory(Long id);
    void registerEditor(EditorRegisterRequest editorRegisterRequest);
    void addRole(RoleRequest roleRequest);
    void deleteRole(Long id );
    void addProject(ProjectRequest projectRequest );
    void updateProject(Long id, ProjectRequest projectRequest );
    void attachImageToProject(Long projectId, Long imageId);
    void deleteProject(Long id);
    void addService(ServiceRequest serviceRequest );
    void updateService(Long id, ServiceRequest serviceRequest );
    void attachImageToService(Long serviceId, Long imageId);
    void deleteService(Long id);
    void addActivity(ActivityRequest activityRequest , Long imageId );
    void updateActivity(Long id , ActivityRequest activityRequest ,   Long imageId );
    void deleteActivity(Long id);


}
